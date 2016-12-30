package management.elevator.com.elevatormanagementactivity.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.adapter.EleMaintenanceAdapter;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.bean.RetParkBean;
import management.elevator.com.elevatormanagementactivity.bean.TickFlowBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class ElevatorMaintenanceActivity extends AppCompatActivity {
//    @BindById(R.id.img_back)
//    ImageView imgBack;
//    @BindById(R.id.tex_title)
//    TextView texTitle;
    @BindById(R.id.rec_ele_main)
    RecyclerView recEleMain;
    String token;
    RetParkBean bean;
    private static final int LOADTICKHIST = 101;
    EleMaintenanceAdapter adapter;
//String []str={"海王星大厦","海王星大厦"};
//    String []str2={"江北区","江北区"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_maintenance);
        PreIOC.binder(this);
        init();
        initData(0);
    }
    private void init() {
        recEleMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADTICKHIST:
                    final RetParkBean obj = (RetParkBean) msg.obj;
                    adapter=new EleMaintenanceAdapter(getApplicationContext(),obj){
                        @Override
                        public void onBindViewHolder(final EleMaintenanceTextViewHolder holder, final int position) {
                            super.onBindViewHolder(holder, position);
                            adapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, String data) {
                                    Log.i("onclickListener","onclicklisten");
                                    String id=obj.getDatas().get(position).getID()+"";
                                    Intent intent=new Intent();
                                    intent.putExtra("ID",id);
                                    intent.setClass(getApplicationContext(),CellMessageActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    };
                    recEleMain.setAdapter(adapter);
                    adapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {

                        }
                    });
//
                    break;
                default:
                    break;
            }
        }
    };

    private void initData(final int number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                token = Constant.TOKEN;
                String domain = Constant.URL_TRANSCTION;
                String params = Constant.OPER + "=" +
                        Constant.PARK_LIST + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.SPE + "=" + number;
                String json = GetSession.post(domain, params);

                if (!json.equals("+ER+")) {
                    ArrayList<RetParkBean.Data> mlist = new ArrayList<RetParkBean.Data>();
                    try {
                        RetParkBean.Data data;
                        bean = new RetParkBean();
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            data = new RetParkBean.Data();
                            data.setID(jsonObject.getInt("ID"));
                            data.setAREA(jsonObject.getString("AREA"));
                            data.setDEV_NUM(jsonObject.getString("DEV_NUM"));
                            data.setNAME(jsonObject.getString("NAME"));
                            mlist.add(data);

                        }
                        bean.setDatas(mlist);
                        Message message = new Message();
                        message.obj = bean;
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
