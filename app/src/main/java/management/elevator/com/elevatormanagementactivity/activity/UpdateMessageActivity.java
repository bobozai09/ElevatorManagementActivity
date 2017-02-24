package management.elevator.com.elevatormanagementactivity.activity;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.yoojia.anyversion.AnyVersion;
import com.github.yoojia.anyversion.Callback;
import com.github.yoojia.anyversion.NotifyStyle;
import com.github.yoojia.anyversion.Version;
import com.github.yoojia.anyversion.VersionParser;
import com.github.yoojia.anyversion.VersionReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.UpdateInfo;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.utils.HttpUtils;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
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
    @BindById(R.id.btn_update_version)
    Button btnUpdateVersion;

    private UpdateInfo info;
    private UpdateInfo.Data data;

    private final int UPDATA_NONEED = 0;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int SDCARD_NOMOUNTED = 3;
    private final int DOWN_ERROR = 4;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA_NONEED:
                    Toast.makeText(UpdateMessageActivity.this, "不需要更新", Toast.LENGTH_SHORT).show();
                    break;
                case UPDATA_CLIENT:
                    //弹出对话框
                    showUpdateDialog(info.getDatas().get(0).getVername());
                    break;
                case GET_UNDATAINFO_ERROR:
                    Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_messge);
        PreIOC.binder(this);
        texTitle.setText("修改资料");
        init();
    }

    private void init() {
        imgBack.setOnClickListener(this);
        btnUpdateVersion.setOnClickListener(this);
    }

    /**
     * @param messageInfo 详细的描述信息
     */
    private void showUpdateDialog(String messageInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("版本升级");
        builder.setMessage(messageInfo);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载apk
                downLoadApk();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消弹框
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void downLoadApk() {
        final ProgressDialog pd;    //进度条框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
////                    File file = DownLoadManager.getFileFromServer(info.getUrl(), pd);
//                    sleep(3000);
//                    installApk(file);
                    pd.dismiss(); //结束掉进度条框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    mHandler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 安装apk
     *
     * @param file
     */
    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 获取版本信息
     *
     * @param context
     * @return
     */
    public String getVersion(Context context)//获取版本号
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_update_version:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        String domain = Constant.BASE_URL + Constant.DOWNLOADURL;
                        String json = HttpUtils.submitPostData(domain, null, "utf-8");
                        Log.d("logd", "run: " + json);
                        try {
                            ArrayList<UpdateInfo.Data> mlist = new ArrayList<UpdateInfo.Data>();
                            info = new UpdateInfo();
                            data = new UpdateInfo.Data();
                            JSONObject jsonob = new JSONObject(json);
                            int vercode = jsonob.getInt("vercode");
                            String vername = jsonob.getString("vername");
                            String appname = jsonob.getString("appname");
                            String verdes = jsonob.getString("verdesc");
                            data.setAppname(appname);
                            data.setVercode(vercode);
                            data.setVerdesc(verdes);
                            data.setVername(vername);
                            mlist.add(data);
                            if (vercode == getVersionCode(getApplicationContext())) {
                                message.what = UPDATA_NONEED;
                                mHandler.sendMessage(message);
                            } else {
                                System.out.println("版本不一致,已检测到新版本");
                                message.what = UPDATA_CLIENT;
                                mHandler.sendMessage(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }).start();

                break;

            default:
                break;
        }
    }

//    @Override
//    protected void onStart() {
//        AnyVersion.registerReceiver(this,newVersionReceiver);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        AnyVersion.unregisterReceiver(this,newVersionReceiver);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
