package management.elevator.com.elevatormanagementactivity.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.adapter.CellItemAdapter;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.bean.CellItemBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class CellItemActivity extends BaseActivity implements View.OnClickListener {
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.rec_ele_main)
    RecyclerView recEleMain;
    Intent intent;
    String token;
    CellItemBean bean;
    CellItemBean.Data data;
    CellItemAdapter adapter;
    private static final int INITDATA = 011;
    @BindById(R.id.img_other)
    TextView imgOther;
    PopChooseDateWin pop;
    String id;
    String OLDID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_maintenance);
        PreIOC.binder(this);
        intent = getIntent();
        imgBack.setOnClickListener(this);
        OLDID = intent.getStringExtra("OID_ID");
//        Log.d("oldid", "onCreate: " + OLDID);

        imgOther.setVisibility(View.VISIBLE);
        texTitle.setText(intent.getStringExtra("NAME"));
        recEleMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        initData(0);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INITDATA:
                    final CellItemBean obj = (CellItemBean) msg.obj;
                    adapter = new CellItemAdapter(getApplicationContext(), obj) {
                        @Override
                        public void onBindViewHolder(CellItemViewholder holder, final int position) {
                            super.onBindViewHolder(holder, position);
                            adapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, String data) {
                                    int status = obj.getDatas().get(position).getI_STATUS();
                                    String temp = obj.getDatas().get(position).getID() + "";
                                    switch (status) {
                                        case 0:
                                            break;
                                        case 1:
                                        case 2:
//                                            Intent intent = new Intent(getApplication(), MaintenanceItemActivity.class);
//                                            intent.putExtra("ID", temp);
//                                            startActivity(intent);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            });
                        }
                    };
                    recEleMain.setAdapter(adapter);
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
                id = intent.getStringExtra("ID");
                String domain = Constant.URL_TRANSCTION;
                String params = Constant.OPER + "=" +
                        Constant.DEV_MC_LIST + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.SPE + "=" + number + "&" + Constant.ID + "=" + id;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    ArrayList<CellItemBean.Data> mlist = new ArrayList<CellItemBean.Data>();
                    try {
                        data = new CellItemBean.Data();
                        bean = new CellItemBean();
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            data.setDTM_END(jsonObject.getString("DTM_END"));
                            data.setI_STATUS(jsonObject.getInt("I_STATUS"));
                            data.setID(jsonObject.getInt("ID"));
                            data.setTMPL(jsonObject.getString("TMPL"));
                            mlist.add(data);
                        }
                        bean.setDatas(mlist);
                        Message message = new Message();
                        message.what = INITDATA;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

        }
    }

    public void showpopForBottom(View view) {
        pop = new PopChooseDateWin(this, onClickListener);
        pop.showAtLocation(findViewById(R.id.main_view_2), Gravity.CENTER, 0, 0);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                /**
                 * 电梯:电梯半月维保（TMPL=21），电梯季度维保（TMPL=22），
                 * 电梯半年维保（TMPL=23），电梯全年维保（TMPL=24）
                 */
                case R.id.btn_main_choose_a_half_month:
                    toIntent(21);
                    break;
                case R.id.btn_main_choose_three_month:
                    toIntent(22);
                    break;
                case R.id.btn_main_choose_half_year:
                    toIntent(23);
                    break;
                case R.id.btn_main_choose_all_year:
                    toIntent(24);
                    break;
                default:
                    break;
            }
        }
    };

    private void toIntent(int tmpl) {

        Intent intent = new Intent();
        intent.putExtra("TMPL", tmpl);
        intent.putExtra("OLD", OLDID);
        intent.setClass(getApplicationContext(), MaintenanceRecordActivity.class);
        startActivity(intent);
        pop.dismiss();
    }
}
