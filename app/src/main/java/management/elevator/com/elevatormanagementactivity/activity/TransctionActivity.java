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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.MyLocationAndMapActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.maintenance.ElevatorMaintenanceActivity;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.adapter.TransactionAdapter;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import management.elevator.com.elevatormanagementactivity.widget.RecycleViewDivider;
import management.elevator.com.elevatormanagementactivity.widget.SpaceItemDecoration;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * 我的事务 列表页
 * <p>
 * Created by Administrator on 2016/12/28 0028.
 */

public class TransctionActivity extends BaseActivity implements View.OnClickListener {
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
    private final static int LOADNUM = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transction);
        PreIOC.binder(this);
        texTitle.setText("我的事务");
        initData();
        initView();
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADNUM:
                    adapter = new TransactionAdapter(getApplicationContext());
                    String getstr = (String) msg.obj;
                    break;
            }
        }
    };

    private void initView() {
        imgBack.setOnClickListener(this);
        recTrans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        int spaceInPixes = getResources().getDimensionPixelSize(R.dimen.activity_15dp);
        recTrans.addItemDecoration(new SpaceItemDecoration(spaceInPixes));
        Resources res = getResources();
        String[] title = res.getStringArray(R.array.tranction_title);
        String[] indrucation = res.getStringArray(R.array.tranction_introdu);
        adapter = new TransactionAdapter(getApplicationContext(), title, img, indrucation);
        recTrans.setAdapter(adapter);
        adapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                int position = recTrans.getChildAdapterPosition(view);
                switch (position) {
                    case 2:
                    case 0:
                    case 3:
                        toIntent(position);
                        break;
                    case 1:
                    case 4:
                    case 5:
                        toIntent(200 + position);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void toIntent(int number) {
        Intent intent;
        switch (number) {
            case 2:
            case 3:
                intent = new Intent();
                intent.setClass(getApplicationContext(), ElevatorMaintenanceActivity.class);
                intent.putExtra("NUMBER", number);
                startActivity(intent);
                break;
            case 0:
                intent = new Intent();
//            Bundle bundle1 = new Bundle();
//            bundle1.putInt("ID", 0);
//            intent.putExtras(bundle1);
                intent.setClass(getApplicationContext(), MyLocationAndMapActivity.class);
                startActivity(intent);
                break;

            case 201:
            case 204:
            case 205:
                intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("ID", number);
                intent.putExtras(bundle);
                intent.setClass(getApplicationContext(), WebViewActivity.class);
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
                        Constant.LIFT_MC_NUM = lift_mc_num;
                        Constant.ICCM_MC_NUM = ICCM_MC_NUM;

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
}
