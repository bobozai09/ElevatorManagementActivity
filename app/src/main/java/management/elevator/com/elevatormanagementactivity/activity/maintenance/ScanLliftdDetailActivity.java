package management.elevator.com.elevatormanagementactivity.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.util.EncodingUtils;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class ScanLliftdDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.img_other)
    TextView imgOther;
    @BindById(R.id.web_show)
    WebView webShow;
    String url;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        texTitle.setText("查询详情");
        PreIOC.binder(this);
        initView();
    }
    private void initView() {
        imgBack.setOnClickListener(this);
        intent = getIntent();
        final int tid = intent.getIntExtra("TID", 0);
        url = Constant.BASE_URL + Constant.SCANLIFTDETAIL + tid;
        webShow.loadUrl(url);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webShow.canGoBack()) {
            webShow.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
