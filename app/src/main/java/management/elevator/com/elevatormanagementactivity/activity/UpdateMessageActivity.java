package management.elevator.com.elevatormanagementactivity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * 修改个人资料
 * Created by Administrator on 2017/1/15 0015.
 */

public class UpdateMessageActivity extends BaseActivity implements View.OnClickListener {
    @BindById(R.id.edit_update_name)
    EditText editUpdateName;
    @BindById(R.id.edit_update_address)
    EditText editUpdateAddress;
    @BindById(R.id.edit_idcard)
    EditText editIdcard;
    @BindById(R.id.edit_email)
    EditText editEmail;
    @BindById(R.id.btn_update)
    Button btnUpdate;
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.img_other)
    TextView imgOther;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_messge);
        PreIOC.binder(this);
        texTitle.setText("修改资料");
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.img_back:
        finish();
        break;
    default:
        break;
}
    }
}
