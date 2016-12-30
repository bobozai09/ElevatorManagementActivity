package management.elevator.com.elevatormanagementactivity.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.maintenance.ElevatorMaintenanceActivity;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.adapter.TransactionAdapter;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import management.elevator.com.elevatormanagementactivity.widget.SpaceItemDecoration;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * 我的事务 列表页
 *
 * Created by Administrator on 2016/12/28 0028.
 */

public class TransctionActivity extends AppCompatActivity {
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.rec_trans)
    RecyclerView recTrans;
    TransactionAdapter adapter;
    int[] img = {R.mipmap.icon_check_in,
            R.mipmap.icon_wodegongzuo,
            R.mipmap.icon_weibao, R.mipmap.icon_zhongduanweihu,
            R.mipmap.icon_xiaoquxuncha,
            R.mipmap.icon_weibao_queren};
    private final  static int LOADNUM = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transction);
        PreIOC.binder(this);
        texTitle.setText("我的事务");
        initData();
        initView();
    }
Handler handler=new Handler(){

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case LOADNUM:
                adapter=new TransactionAdapter(getApplicationContext());
                String getstr= (String) msg.obj;
                break;
        }
    }
};
    private void initView() {
        recTrans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        int spaceInPixes = getResources().getDimensionPixelSize(R.dimen.activity_8dp);
        recTrans.addItemDecoration(new SpaceItemDecoration(spaceInPixes));
        Resources res = getResources();
        String[] title = res.getStringArray(R.array.tranction_title);
        String[] indrucation = res.getStringArray(R.array.tranction_introdu);
        adapter = new TransactionAdapter(getApplicationContext(),title, img, indrucation);
        recTrans.setAdapter(adapter);
        adapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                int position = recTrans.getChildAdapterPosition(view);
                switch (position){
                    case 2:
                        toIntent(2);
                        break;
                }

            }
        });

    }
private void toIntent(int number){
    Intent intent;
    switch (number){
        case 2:
            intent=new Intent();
            intent.setClass(getApplicationContext(), ElevatorMaintenanceActivity.class);
            intent.putExtra("NUMBER",number);
            startActivity(intent);
            break;
    }
}
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = Constant.TOKEN;
                String domain = Constant.URL_TRANSCTION;
                String params = Constant.OPER + "=" +
                        Constant.MC_BADGE_NUM + "&" + Constant.LOGIN_TOKEN + "=" + token;
                String json = GetSession.post(domain, params);
                Message message;
                if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int lift_mc_num = jsonObject.getInt("LIFT_MC_NUM");
                        int ICCM_MC_NUM = jsonObject.getInt("ICCM_MC_NUM");
                        Constant.LIFT_MC_NUM=lift_mc_num;
                        Constant.ICCM_MC_NUM=ICCM_MC_NUM;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }).start();


    }
}
