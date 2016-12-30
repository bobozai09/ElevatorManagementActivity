package management.elevator.com.elevatormanagementactivity.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.CellListBean;
import management.elevator.com.elevatormanagementactivity.bean.RetParkBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/30 0030.
 * 小区电梯信息详情
 */

public class CellMessageActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //与父菜单 电梯维保共用一个布局
        setContentView(R.layout.activity_elevator_maintenance);
        PreIOC.binder(this);
        recEleMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        initData(0);
    }

    private void initData(final int number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                intent = getIntent();
                String id = intent.getStringExtra("ID");
                token = Constant.TOKEN;
                String domain = Constant.URL_TRANSCTION;
                String params = Constant.OPER + "=" +
                        Constant.PARK_DEV_LIST + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.SPE + "=" + number + "&" + Constant.ID + "=" + id;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    ArrayList<CellListBean.Data> mlist = new ArrayList<CellListBean.Data>();
                    data = new CellListBean.Data();
                    try {
                        bean = new CellListBean();
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            data.setID(jsonObject.getInt("ID"));
                            data.setDESC(jsonObject.getString("DESC"));
                            data.setLOCAL(jsonObject.getString("LOCAL"));
                            mlist.add(data);
                        }
                        bean.setDatas(mlist);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();


    }
}