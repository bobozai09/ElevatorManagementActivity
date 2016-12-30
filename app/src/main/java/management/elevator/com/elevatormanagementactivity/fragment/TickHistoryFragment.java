package management.elevator.com.elevatormanagementactivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.TickHistViewActivity;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.adapter.TickHistoryAdapter;
import management.elevator.com.elevatormanagementactivity.bean.TickHistoryBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class TickHistoryFragment extends Fragment {
    private static final int NODATASHOW = 110;
    private static final int TICKHISTDATA = 100;
    RecyclerView recyclerView;
    SharedPreferences sp;
    String token;
    TickHistoryBean bean;
    TickHistoryBean.Data Data;
    TickHistoryAdapter adapter;
    @BindById(R.id.viewstub_one)
    ViewStub viewstubOne;
    @BindById(R.id.rec_order)
    RecyclerView recOrder;
    private int mType = 1;
    private String mTitle;
    private View viewContent;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TICKHISTDATA:
                    final TickHistoryBean obj = (TickHistoryBean) msg.obj;
                    adapter = new TickHistoryAdapter(getContext(), obj) {
                        @Override
                        public void onBindViewHolder(final TickHistoryTextViewHolder holder, int position) {

                            super.onBindViewHolder(holder, position);
                            adapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, String data) {
                                    String tid = holder.order_numer.getText().toString();
                                    Intent intent = new Intent();
                                    intent.putExtra("ID", tid);
                                    intent.setClass(getActivity(), TickHistViewActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    recyclerView.setAdapter(adapter);
                    break;
                case NODATASHOW:
                    try {
                        viewstubOne.inflate();
                    } catch (Exception e) {
                        viewstubOne.setVisibility(View.VISIBLE);
                    }

                    break;
                default:
                    break;

            }
            super.handleMessage(msg);
        }
    };

    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContent = inflater.inflate(R.layout.fragment_context, container, false);
        ButterKnife.bind(getActivity());
        Data = new TickHistoryBean.Data();
        sp = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        token = sp.getString(Constant.LOGIN_TOKEN, "");
//        init();
        initView(viewContent);
        initData();
        PreIOC.binder(this, viewContent);
        return viewContent;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rec_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.DEVLIFTURL;
//                String params = Constant.OPER + "=" +
//                        "lift-num" + "&" + Constant.LOGIN_TOKEN + "=" + token ;
                String params = Constant.OPER + "=" +
                        "lift-list" + "&" + Constant.LOGIN_TOKEN + "=" + token + "&p=1";
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {


                }
            }
        }).start();


    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.URL;
                String params = Constant.OPER + "=" +
                        Constant.TICK_HIST + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + "p=1" + "&m=12";
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    try {
                        if (json.equals("[]")) {
                            Message message = new Message();
                            message.what = NODATASHOW;
                            handler.sendMessage(message);
                        }
                        ArrayList<TickHistoryBean.Data> mlist = new ArrayList<TickHistoryBean.Data>();
                        TickHistoryBean.Data data;
                        bean = new TickHistoryBean();
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            data = new TickHistoryBean.Data();
                            long tid = jsonObject.getLong("TID");
                            String type = jsonObject.getString("TYPE");
                            String name = jsonObject.getString("NAME");
                            String tick = jsonObject.getString("TICK");
                            int i_status = jsonObject.getInt("I_STATUS");
                            String s_status = jsonObject.getString("S_STATUS");
                            String dis_man = jsonObject.getString("DIS_MAN");
                            String dis_dtm = jsonObject.getString("DIS_DTM");
                            int i_rank = jsonObject.getInt("I_RANK");
                            String s_rank = jsonObject.getString("S_RANK");
                            boolean is_urged = jsonObject.getBoolean("IS_URGED");
                            boolean is_ovt = jsonObject.getBoolean("IS_OVT");
                            String lmt_time = jsonObject.getString("LMT_TIME");
                            String del_time = jsonObject.getString("DEL_TIME");
                            data.setTID(tid);
                            data.setTYPE(type);
                            data.setNAME(name);
                            data.setTICK(tick);
                            data.setI_STATUS(i_status);
                            data.setS_STATUS(s_status);
                            data.setDIS_MAN(dis_man);
                            data.setDIS_DTM(dis_dtm);
                            data.setI_RANK(i_rank);
                            data.setS_RANK(s_rank);
                            data.setIS_URGED(is_urged);
                            data.setIS_OVT(is_ovt);
                            data.setLMT_TIME(lmt_time);
                            data.setDEL_TIME(del_time);
                            mlist.add(data);
                        }
                        bean.setDatas(mlist);
                        Message message = new Message();
                        message.what = TICKHISTDATA;
                        message.obj = bean;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreIOC.unBinder(this);
    }
}
