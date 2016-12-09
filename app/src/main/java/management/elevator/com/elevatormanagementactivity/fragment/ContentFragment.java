package management.elevator.com.elevatormanagementactivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.Order_SpecificMessageActivity;
import management.elevator.com.elevatormanagementactivity.adapter.OrderUndoneAdapter;
import management.elevator.com.elevatormanagementactivity.bean.TickSelfBean;
import management.elevator.com.elevatormanagementactivity.utils.GetMD5;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import management.elevator.com.elevatormanagementactivity.widget.SpaceItemDecoration;

/**
 * Created by janiszhang on 2016/6/6.
 */

public class ContentFragment extends Fragment {

    private OrderUndoneAdapter adapter;
    //    String[] number = {"工单编号：0122334455555", "工单编号：0122334455555"};
//    String[] status = {"紧急", "重要"};
    private View viewContent;
    private int mType = 0;
    private String mTitle;
    RecyclerView orderReceiver;
    String token;

    SharedPreferences sp;
    TickSelfBean bean;
    TickSelfBean.Data Data;
    private static final int LOADDATA = 1;
    private static final int TICKHOLDFAIL=100;
    private static final  int TICKHOLDSUCC=101;
    private  static  final  int TICKHOLDOTHERS=102;
    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //布局文件中只有一个居中的TextView
        viewContent = inflater.inflate(R.layout.fragment_context, container, false);
        ButterKnife.bind(getActivity());
        Data = new TickSelfBean.Data();
        sp = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        token = sp.getString(Constant.LOGIN_TOKEN, "");
        init(viewContent);
        inithold();
        initData();

        return viewContent;
    }
private void inithold(){
    new Thread(new Runnable() {
        @Override
        public void run() {
            String domain = Constant.BASE_URL + Constant.TICKER;
            String params = Constant.OPER + "=" +
                    Constant.TICK_HOLD + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TID + "=" + "2016120217541";
            String json = GetSession.post(domain, params);
            Log.i("---inithold---", json);
            if (!json.equals("+ER+")){
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String result = jsonObject.getString("result");
                    Message message = handler.obtainMessage();
                    message.obj = result;
                    if (result.equals("fail")){
                        message.what=TICKHOLDFAIL;
                    }else if(result.equals("succ")){
                        message.what=TICKHOLDSUCC;
                    }else {
                        message.what=TICKHOLDOTHERS;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }).start();

}
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.TICKER;
                String params = Constant.OPER + "=" +
                        Constant.TICK_SELF + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + "p=1";
                String json = GetSession.post(domain, params);
                Log.i("---contentfragment---", json);
                if (!json.equals("+ER+")) {
                    try {
                        ArrayList<TickSelfBean.Data> mlist = new ArrayList<TickSelfBean.Data>();
                        TickSelfBean.Data data;
                        bean = new TickSelfBean();
                        JSONArray jsonArray = new JSONArray(json);
                        int iszie = jsonArray.length();
                        for (int i = 0; i < iszie; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            System.out.println("[" + i + "]gwcxxid=" + jsonObject.get("TID"));
                            long tid = jsonObject.getLong("TID");
                            String type = jsonObject.getString("TYPE");
                            String name = jsonObject.getString("NAME");
                            String tick = jsonObject.getString("TICK");
                            int i_status = jsonObject.getInt("I_STATUS");
                            String s_status=jsonObject.getString("S_STATUS");
                            String dis_dtm = jsonObject.getString("DIS_DTM");
                            String die_man = jsonObject.getString("DIS_MAN");
                            int i_rank = jsonObject.getInt("I_RANK");
                            String s_rank = jsonObject.getString("S_RANK");
                            boolean is_urged = jsonObject.getBoolean("IS_URGED");
                            boolean is_ovt = jsonObject.getBoolean("IS_OVT");
                            String lmt_time = jsonObject.getString("LMT_TIME");
                            String del_time = jsonObject.getString("DEL_TIME");
                            data = new TickSelfBean.Data();
                            data.setTID(tid);
                            data.setTYPE(type);
                            data.setNAME(name);
                            data.setTICK(tick);
                            data.setI_STATUS(i_status);
                            data.setS_STATUS(s_status);
                            data.setDIS_DTM(dis_dtm);
                            data.setI_RANK(i_rank);
                            data.setS_RANK(s_rank);
                            data.setIS_URGED(is_urged);
                            data.setIS_OVT(is_ovt);
                            data.setLMT_TIME(lmt_time);
                            data.setDEL_TIME(del_time);
                            data.setDIS_MAN(die_man);
                            mlist.add(data);
                        }
                        bean.setDatas(mlist);
                        Message message = new Message();
                        message.obj = bean;
                        message.what=LOADDATA;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();


    }


    private void init(View view) {
        int spaceInPixes = getResources().getDimensionPixelSize(R.dimen.activity_8dp);
        orderReceiver = (RecyclerView) view.findViewById(R.id.rec_order);
        orderReceiver.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderReceiver.addItemDecoration(new SpaceItemDecoration(spaceInPixes));

        orderReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    }
});

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOADDATA:
                    TickSelfBean obj = (TickSelfBean) msg.obj;
                    adapter = new OrderUndoneAdapter(getContext(), obj);
                    orderReceiver.setAdapter(adapter);
break;
                case TICKHOLDFAIL:
                    Toast.makeText(getActivity(), "很遗憾 接单失败", Toast.LENGTH_LONG).show();
                    break;
                case TICKHOLDSUCC:
                    Toast.makeText(getActivity(),"恭喜您登录成功",Toast.LENGTH_LONG).show();
                    break;
                case  TICKHOLDOTHERS:
                    Toast.makeText(getActivity(),"很遗憾 出错了",Toast.LENGTH_LONG).show();
                    break;
            }
            super.handleMessage(msg);


        }
    };

}
