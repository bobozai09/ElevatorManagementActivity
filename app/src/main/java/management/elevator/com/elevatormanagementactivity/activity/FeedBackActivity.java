package management.elevator.com.elevatormanagementactivity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/15 0015.
 */

public class FeedBackActivity extends BaseActivity {
    @BindById(R.id.text_feedback_text)
    EditText textFeedbackText;
    @BindById(R.id.btn_add)
    Button btnAdd;
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.img_other)
    TextView imgOther;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        PreIOC.binder(this);
        texTitle.setText("意见反馈");
    }
}
