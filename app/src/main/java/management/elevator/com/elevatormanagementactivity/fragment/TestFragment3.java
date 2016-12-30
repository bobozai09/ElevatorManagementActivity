package management.elevator.com.elevatormanagementactivity.fragment;


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
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.adapter.LiftListAdapter;
import management.elevator.com.elevatormanagementactivity.bean.LiftListBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * 设备主页
 * Created by janiszhang on 2016/6/10.
 */

public class TestFragment3 extends Fragment {
    LiftListAdapter adapter_lift_list;
    String token;
    private static final int lift = 101;//电梯
    private static final int iccm = 102;//一体机
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.rec_lift_and_iccm)
    RecyclerView recLiftAndIccm;
    @BindById(R.id.text_cell_number)
    TextView textCellNumber;
    @BindById(R.id.txt_elevator_number)
    TextView txtElevatorNumber;
    private static final int DEVILIFT = 101;
    private static final int LIFTLISTMESSAGE = 102;
    private static final int NODATASHOW = 110;
    LiftListBean liftlistbean;
    @BindById(R.id.viewstub_one)
    ViewStub viewstubOne;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_3, null);
        PreIOC.binder(this, view);
        token = Constant.TOKEN;
        initView();
        recLiftAndIccm.setLayoutManager(new LinearLayoutManager(getActivity()));
        initDeviliftData();
        initdeviliftDataList();
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DEVILIFT:
                    String getStr = (String) msg.obj;
                    String[] arr = getStr.split(",");
                    int devnumb = Integer.parseInt(arr[0]);
                    int packnum = Integer.parseInt(arr[1]);
                    textCellNumber.setText("电梯" + packnum);
                    txtElevatorNumber.setText("共" + devnumb + "小区");
                    break;
                case LIFTLISTMESSAGE:
                    final LiftListBean obj = (LiftListBean) msg.obj;
                    adapter_lift_list = new LiftListAdapter(getContext(), obj);
                    Log.i("data", "" + obj);
                    recLiftAndIccm.setAdapter(adapter_lift_list);
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
        }
    };

    private void initView() {
        imgBack.setVisibility(View.GONE);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recLiftAndIccm.setLayoutManager(llm);

    }

    private void initDeviliftData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String domain = Constant.DEVLIFTURL;
                String params = Constant.OPER + "=" +
                        Constant.LIFT_NUM + "&" + Constant.LOGIN_TOKEN + "=" + token;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int devnum = jsonObject.getInt("devNum");
                        int parknum = jsonObject.getInt("parkNum");
                        Message message = new Message();
                        message.what = DEVILIFT;
                        String str = devnum + "," + parknum;
                        message.obj = str;
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    private void initdeviliftDataList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.DEVLIFTURL;
                String params1 = Constant.OPER + "=" +
                        Constant.LIFT_LIST + "&" + Constant.LOGIN_TOKEN + "=" + token + "&p=1";
                String json1 = GetSession.post(domain, params1);
                if (!json1.equals("+ER+")) {
                    if (json1.equals("[]")) {
                        Message message = new Message();
                        message.what = NODATASHOW;
                        handler.sendMessage(message);
                    }
                    try {
                        ArrayList<LiftListBean.Data> mlist = new ArrayList<LiftListBean.Data>();
                        LiftListBean.Data data;
                        liftlistbean = new LiftListBean();
                        JSONArray jsonArray = new JSONArray(json1);
                        data = new LiftListBean.Data();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            data.setID(jsonObject.getInt("ID"));
                            data.setSN(jsonObject.getString("SN"));
                            data.setL_AREA(jsonObject.getString("L_AREA"));
                            data.setL_PARK(jsonObject.getString("L_PARK"));
                            data.setL_POINT(jsonObject.getString("L_POINT"));
                            data.setDEV_NAME(jsonObject.getString("DEV_NAME"));
                            data.setDEV_TYPE(jsonObject.getString("DEV_TYPE"));
                            data.setDEV_VND(jsonObject.getString("DEV_VND"));
                            data.setSTATUS(jsonObject.getString("STATUS"));
                            data.setMC_INFO(jsonObject.getString("MC_INFO"));
                            mlist.add(data);
                        }
                        Message message = new Message();
                        liftlistbean.setDatas(mlist);
                        message.obj = liftlistbean;
                        message.what = LIFTLISTMESSAGE;
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
