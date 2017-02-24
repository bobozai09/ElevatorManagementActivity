package management.elevator.com.elevatormanagementactivity.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CallSuper;
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
import management.elevator.com.elevatormanagementactivity.adapter.CellListAdapter;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.bean.CellListBean;
import management.elevator.com.elevatormanagementactivity.bean.RetParkBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/30 0030.
 * 小区电梯信息详情 列表页
 */

public class CellMessageActivity extends BaseActivity implements View.OnClickListener {
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.rec_ele_main)
    RecyclerView recEleMain;
    String token;
    CellListBean bean;
    CellListBean.Data data;
    Intent intent;
    CellListAdapter adapter;
    private static final int LOADDATA = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //与父菜单 电梯维保共用一个布局
        setContentView(R.layout.activity_elevator_maintenance);
        PreIOC.binder(this);
        recEleMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        intent = getIntent();
        texTitle.setText(intent.getStringExtra("NAME"));
        imgBack.setOnClickListener(this);
        initData(0);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADDATA:
                    final CellListBean obj = (CellListBean) msg.obj;
                    adapter = new CellListAdapter(getApplicationContext(), obj) {
                        @Override
                        public void onBindViewHolder(CellListViewholder holder, final int position) {
                            super.onBindViewHolder(holder, position);
                            adapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, String data) {
                                    String id = obj.getDatas().get(position).getDESC() + "";
                                    Intent intent = new Intent();
                                    intent.putExtra("ID", id);
                                    intent.putExtra("OID_ID", obj.getDatas().get(position).getID()+"");
                                    intent.putExtra("NAME",obj.getDatas().get(position).getDESC());
                                    intent.setClass(getApplicationContext(), CellItemActivity.class);
                                    startActivity(intent);
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

                String id = intent.getStringExtra("ID");
                token = Constant.TOKEN;
                String domain = Constant.URL_TRANSCTION;
                String params = Constant.OPER + "=" +
                        Constant.PARK_DEV_LIST + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.SPE + "=" + number + "&" + Constant.ID + "=" + id;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    ArrayList<CellListBean.Data> mlist = new ArrayList<CellListBean.Data>();
                    try {

                        bean = new CellListBean();
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            data = new CellListBean.Data();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            data.setID(jsonObject.getInt("ID"));
                            data.setDESC(jsonObject.getString("DESC"));
                            data.setLOCAL(jsonObject.getString("LOCAL"));
                            mlist.add(data);
                        }
                        bean.setDatas(mlist);
                        Message message = new Message();
                        message.obj = bean;
                        message.what = LOADDATA;
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
switch (v.getId()){
    case R.id.img_back:
        finish();
        break;


}
    }
}
