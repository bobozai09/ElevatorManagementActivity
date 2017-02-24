package management.elevator.com.elevatormanagementactivity.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class MaintenanceItemActivity extends BaseActivity implements View.OnClickListener {
    Intent intent;
    String token;
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.img_other)
    TextView imgOther;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_item);
        PreIOC.binder(this);
        imgOther.setVisibility(View.VISIBLE);
        intent = getIntent();
        initView();
        initData();
    }

    private void initView() {
        imgOther.setOnClickListener(this);
        imgBack.setOnClickListener(this);

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                token = Constant.TOKEN;
                String ID = intent.getStringExtra("ID") + "";
                String domain = Constant.URL_TRANSCTION;
                String params = Constant.OPER + "=" +
                        Constant.DEV_MC_RESULT + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.ID + "=" + ID;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {


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
