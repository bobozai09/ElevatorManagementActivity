package management.elevator.com.elevatormanagementactivity.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.decoding.Intents;

import org.apache.http.util.EncodingUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.img_other)
    TextView imgOther;
    @BindById(R.id.web_show)
    WebView webShow;
    Intent intent;
    Bundle bundler;
    String url;
    String cname;
    String dataurl;
    int id;
    int uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        PreIOC.binder(this);
        initView();
        url = Constant.BASE_URL + url;
        if (id == 6 || id == 201 || id == 204 || id == 205||id==102) {
            webShow.postUrl(url, EncodingUtils.getBytes(uid + "", "utf-8"));
        } else {
            webShow.postUrl(url, EncodingUtils.getBytes(cname, "utf-8"));
        }

        initData();
    }

    private void initData() {
        webShow.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initView() {
//intent=getIntent();
        webShow.getSettings().setJavaScriptEnabled(true);
        webShow.getSettings().setLoadsImagesAutomatically(true);
        bundler = new Bundle();
        bundler = this.getIntent().getExtras();
        cname = Constant.C;
        uid = Constant.LOGIN_USERID;
        imgBack.setOnClickListener(this);
        id = bundler.getInt("ID");
        switch (id) {
            case 5:
                url = Constant.KNOWLEAGE;
                texTitle.setText("知识中心");
                break;
            case 7:
                url = Constant.NEWS;
                texTitle.setText("新闻中心");
                break;
            case 3:
                url = Constant.STATALM;
                texTitle.setText("故障统计");
                break;
            case 8:
                url = Constant.REPORT;
                texTitle.setText("报表统计");
                break;
            case 2:
                url = Constant.MAP;
                texTitle.setText("地理位置");
                break;
            case 6:
                url = Constant.PHONEBOOK;
                texTitle.setText("通讯录");
                break;
            case 200:
                url = Constant.WORKSIGN;
                break;
            case 201:
                url = Constant.WORKSELF;
                texTitle.setText("我的工作");
                break;
            case 204:
                url = Constant.PARKRUN;
                texTitle.setText("小区巡查");
                break;
            case 205:
                url = Constant.DEVMCCHECK;
                texTitle.setText("维保确认");
                break;
            case 4:
                url=Constant.NOTICE;
                texTitle.setText("通知中心");
                break;
            default:
                break;

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webShow.canGoBack()) {

            webShow.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
