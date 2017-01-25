package management.elevator.com.elevatormanagementactivity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.TickFlowBean;
import management.elevator.com.elevatormanagementactivity.bean.TickHistViewBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import management.elevator.com.elevatormanagementactivity.widget.UnderLineLinearLayout;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class TickHistViewActivity extends BaseActivity {
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.order_number)
    TextView orderNumber;
    @BindById(R.id.order_status)
    TextView orderStatus;
    @BindById(R.id.order_is_receiver)
    TextView orderIsReceiver;
    @BindById(R.id.ordertype)
    TextView ordertype;
    @BindById(R.id.ordermessage)
    TextView ordermessage;
    @BindById(R.id.line_order_message)
    LinearLayout lineOrderMessage;
    @BindById(R.id.order_sendtime)
    TextView orderSendtime;
    @BindById(R.id.order_sendpersomeone)
    TextView orderSendpersomeone;
    @BindById(R.id.order_sendaddress)
    TextView orderSendaddress;
    @BindById(R.id.line_sendsomeoneaddress)
    LinearLayout lineSendsomeoneaddress;
    @BindById(R.id.order_btn_accept)
    Button orderBtnAccept;
    @BindById(R.id.order_btn_refrush)
    Button orderBtnRefrush;
    @BindById(R.id.line_order_status)
    LinearLayout line_order_status;
    Intent intent;
    private static final int INITDATA = 101;
    private static final  int LOADTICKHIST=102;
    TickHistViewBean bean;
    TickHistViewBean.Data data;
    @BindById(R.id.txt_malfunction_one)
    TextView txtMalfunction;
    @BindById(R.id.txt_source_one)
    TextView txtSource;
    @BindById(R.id.txt_lmt_tiem_one)
    TextView txtLmtTiem;
    @BindById(R.id.underline_order_message_one)
    UnderLineLinearLayout underlineOrderMessageOne;
    TickFlowBean bean_of_flow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tick_hist_view);
        PreIOC.binder(this);
        intent = getIntent();
        line_order_status.setVisibility(View.GONE);
        initData();
        initTickFlow();
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INITDATA:
                    final TickHistViewBean obj = (TickHistViewBean) msg.obj;
                    orderNumber.setText(String.valueOf(obj.getDatas().get(0).getTID()));
                    orderStatus.setText(obj.getDatas().get(0).getS_RANK());
                    orderIsReceiver.setText(obj.getDatas().get(0).getS_STATUS());
                    ordertype.setText("故障内容：" + obj.getDatas().get(0).getTICK());
                    orderSendtime.setText("派单时间：" + obj.getDatas().get(0).getDIS_DTM());
                    orderSendpersomeone.setText("派单人：" + obj.getDatas().get(0).getDIS_MAN());
                    orderSendaddress.setText("地   址:" + obj.getDatas().get(0).getDEV_LOC());


                    txtSource.setText(obj.getDatas().get(0).getALM_CATE());
                    txtMalfunction.setText(obj.getDatas().get(0).getDEV_OSN());
                    txtLmtTiem.setText(obj.getDatas().get(0).getLMT_TIME());
                    break;

                case LOADTICKHIST:
                    final TickFlowBean obj_hist = (TickFlowBean) msg.obj;
                    Log.i("data",obj_hist.toString());
                    for (int i = 0; i < obj_hist.getDatas().size(); i++) {
                        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_vertical, underlineOrderMessageOne, false);
                        ((TextView) v.findViewById(R.id.tx_action)).setText("" + obj_hist.getDatas().get(i).getINF());
                        ((TextView) v.findViewById(R.id.tx_action_time)).setText("" + obj_hist.getDatas().get(i).getDTM());
                        ((TextView) v.findViewById(R.id.tx_action_status)).setText("");
                        underlineOrderMessageOne.addView(v);
                        i++;
                    }
                    break;
            }
        }
    };

    private void initData() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.URL;
                final String token = Constant.TOKEN;
                final String tid = intent.getStringExtra("ID") + "";
                String params = Constant.OPER + "=" +
                        Constant.TICK_HIST_VIEW + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TID + "=" + tid;
                String jsondata = GetSession.post(domain, params);
                if (!jsondata.equals("+ER+")) {
                    data = new TickHistViewBean.Data();
                    ArrayList<TickHistViewBean.Data> mlist_flow = new ArrayList<TickHistViewBean.Data>();
                    try {
                        JSONObject jsonObject = new JSONObject(jsondata);
                        data.setTID(jsonObject.getLong("TID"));
                        data.setI_TYPE(jsonObject.getInt("I_TYPE"));
                        data.setS_TYPE(jsonObject.getString("S_TYPE"));
                        data.setTMPL(jsonObject.getInt("TMPL"));
                        data.setNAME(jsonObject.getString(""));
                        data.setTICK(jsonObject.getString("TICK"));
                        data.setS_STATUS(jsonObject.getString("S_STATUS"));
                        data.setDIS_MAN(jsonObject.getString("DIS_MAN"));
                        data.setDIS_DTM(jsonObject.getString("DIS_DTM"));
                        data.setS_RANK(jsonObject.getString("S_RANK"));
                        data.setLMT_TIME(jsonObject.getString("LMT_TIME"));
                        data.setDEV_OSN(jsonObject.getString("DEV_OSN"));
                        data.setDEV_LOC(jsonObject.getString("DEV_LOC"));
                        data.setALM_CATE(jsonObject.getString(" ALM_CATE"));
                        data.setALM_STA(jsonObject.getString("ALM_STA"));
                        data.setALM_CONTENT(jsonObject.getString("ALM_CONTENT"));
                        data.setALM_ADVISE(jsonObject.getString("ALM_ADVISE"));
                        data.setDEL_DTM(jsonObject.getString("DEL_DTM"));
                        data.setI_RANK(jsonObject.getInt("I_RANK"));
                        data.setDEV_SPE(jsonObject.getInt("DEV_SPE"));
                        data.setDEV_OID(jsonObject.getInt("DEV_OID"));
                        data.setIS_URGED(jsonObject.getBoolean("IS_URGED"));
                        data.setIS_OVT(jsonObject.getBoolean("IS_OVT"));
                        mlist_flow.add(data);
                        bean.setDatas(mlist_flow);
                        Message message = new Message();
                        message.what = INITDATA;
                        message.obj = bean;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                String json = GetSession.post(domain, params);
            }
        }).start();


    }
    private void initTickFlow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.TICKER;
                final  String tid = intent.getStringExtra("TID");
                final String token = Constant.TOKEN;
                String params = Constant.OPER + "=" +
                        Constant.TICK_FLOW + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TID + "=" + tid;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    TickFlowBean.Data data;
                    bean_of_flow = new TickFlowBean();
                    ArrayList<TickFlowBean.Data> mlist_flow = new ArrayList<TickFlowBean.Data>();
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String dtm = jsonObject.getString("DTM");
                            String man = jsonObject.getString("MAN");
                            String inf = jsonObject.getString("INF");
                            String photo = jsonObject.getString("PHOTO");
                            data = new TickFlowBean.Data();
                            data.setDTM(dtm);
                            data.setMAN(man);
                            data.setINF(inf);
                            data.setPHOTO(photo);
                            mlist_flow.add(data);
                        }
                        bean_of_flow.setDatas(mlist_flow);

                        Message message = new Message();
                        message.obj = bean_of_flow;
                        message.what = LOADTICKHIST;
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
