package management.elevator.com.elevatormanagementactivity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.maintenance.ScanLliftdDetailActivity;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.adapter.RetScanLiftItemAdapter;
import management.elevator.com.elevatormanagementactivity.bean.RetScanLiftItemBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/10 0010.
 * y用于显示扫描电梯二维码或模糊输入查询电梯 结果
 */

public class RetScanLiftItActivity extends BaseActivity implements View.OnClickListener {
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.img_other)
    TextView imgOther;
    @BindById(R.id.rec_trans)
    RecyclerView recTrans;
    String token;
    RetScanLiftItemAdapter adapter;
    RetScanLiftItemBean retScanLiftItemBean;
    RetScanLiftItemBean.Data retscanData;
    private static final int HAVERESULT = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transction);
        PreIOC.binder(this);
        token = Constant.TOKEN;
        initView();
        initData();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HAVERESULT:
                    final RetScanLiftItemBean obj = (RetScanLiftItemBean) msg.obj;
                    adapter = new RetScanLiftItemAdapter(getApplicationContext(), obj) {
                        @Override
                        public void onBindViewHolder(Viewholder holder, final int position) {
                            adapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, String data) {
                                    int id = obj.getDatas().get(position).getID();
                                    toIntent(id);
                                }
                            });
                            super.onBindViewHolder(holder, position);
                        }
                    };

                    recTrans.setAdapter(adapter);
                    break;
            }
        }
    };

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.HOME_URL;
                String params = Constant.OPER + "=" +
                        Constant.SCAN_LIFT + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.WAY + "=" + Constant.WAYS + "&" + Constant.SN + "=" + Constant.SNS;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    ArrayList<RetScanLiftItemBean.Data> mretscanleft_list = new ArrayList<RetScanLiftItemBean.Data>();
                    try {
                        retScanLiftItemBean = new RetScanLiftItemBean();
                        JSONArray jsonarray = new JSONArray(json);

                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            retscanData = new RetScanLiftItemBean.Data();
                            retscanData.setL_AREA(jsonObject.getString("L_AREA"));
                            retscanData.setID(jsonObject.getInt("ID"));
                            retscanData.setL_PARK(jsonObject.getString("L_PARK"));
                            retscanData.setNAME(jsonObject.getString("NAME"));
                            retscanData.setSN(jsonObject.getString("SN"));
                            retscanData.setTICK_NUM(jsonObject.getInt("TICK_NUM"));
                            mretscanleft_list.add(retscanData);
                        }
                        retScanLiftItemBean.setDatas(mretscanleft_list);
                        Message message = new Message();
                        message.what = HAVERESULT;
                        message.obj = retScanLiftItemBean;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initView() {
        recTrans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        imgBack.setOnClickListener(this);
//        adapter=new RetScanLiftItemAdapter(getApplicationContext(),)
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;


        }
    }
    private void toIntent(int id) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ScanLliftdDetailActivity.class);
        intent.putExtra("TID", id);
        startActivity(intent);
    }
}
