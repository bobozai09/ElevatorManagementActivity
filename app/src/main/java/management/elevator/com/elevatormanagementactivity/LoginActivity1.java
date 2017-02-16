package management.elevator.com.elevatormanagementactivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingle.widget.ShapeLoadingDialog;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.CountingRequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import java.util.logging.LogRecord;

import android.content.SharedPreferences.Editor;

import management.elevator.com.elevatormanagementactivity.activity.TransctionActivity;
import management.elevator.com.elevatormanagementactivity.broadcast.BroadcastManager;
import management.elevator.com.elevatormanagementactivity.fragment.*;
import management.elevator.com.elevatormanagementactivity.utils.GetMD5;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.utils.SharedPrefUtils;
import management.elevator.com.elevatormanagementactivity.utils.Utils;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import okhttp3.Call;
import okhttp3.Cookie;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class LoginActivity1 extends BaseActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etPassword;
    TextView mForgetPass;
    Button button;
    private static Context context;
    private ShapeLoadingDialog shapeLoadingDialog;
    private SharedPreferences sp;
    String email;
    private static final int RETRIVE = 1;
    private static final int LOADSUCCESS = 2;
    private static final int NONETWORK = 110;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RETRIVE:
                    button.setEnabled(true);
                    String error = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
                    break;
                case LOADSUCCESS:
                    String success = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "恭喜您登录成功", Toast.LENGTH_LONG).show();
                    IntentFoundPassword(2);
                    Editor editor = sp.edit();
                    editor.putString(Constant.LOGIN_TOKEN, success);
                    editor.commit();
                    Constant.TOKEN = success;
                    getMemessage(success);
                    button.setEnabled(true);
                    Constant.LOGIN_SUCCESS_USERN_NAME = email;
                    break;
                case NONETWORK:
                    button.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "请检查网络并尝试再次连接", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        sp = getSharedPreferences("userinfo", context.MODE_PRIVATE);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#9900FF"));
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        if (Constant.LOGIN_SUCCESS_USERN_NAME.equals("")) {
            etEmail.setText("");
        } else {
            etEmail.setText(Constant.LOGIN_SUCCESS_USERN_NAME + "");
        }
        button = (Button) findViewById(R.id.login_in_button);
        button.setOnClickListener(this);
        mForgetPass = (TextView) findViewById(R.id.tx_forget_password);
        mForgetPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_in_button:

                tryLogin();
                break;
            case R.id.tx_forget_password:
                IntentFoundPassword(1);
                break;
            default:
                break;
        }
    }

    void tryLogin() {
        email = String.valueOf(etEmail.getText()).trim();
        String password = String.valueOf(etPassword.getText()).trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity1.this, "请输入手机号或者密码", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean result = Utils.isPhone(email);
        if (result == false) {
            Toast.makeText(LoginActivity1.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        button.setEnabled(false);
        login(email, password);
    }


    private void getMemessage(final String gettoken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.INFO;
                String params = "oper=" +
                        Constant.AUTH + "&" + Constant.LOGIN_TOKEN + "=" + gettoken;
                String json = GetSession.post(domain, params);
                if (!json.equals("json")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int id = jsonObject.getInt("ID");
                        String username = jsonObject.getString("USERNAME");
                        String NAME = jsonObject.getString("NAME");
                        String HEAD = jsonObject.getString("HEAD");
                        String CORP_NAME = jsonObject.getString("CORP_NAME");
                        String DEPT_NAME = jsonObject.getString("DEPT_NAME");
                        String DUTY_NAME = jsonObject.getString("DUTY_NAME");
                        String ROLE_NAME = jsonObject.getString("ROLE_NAME");
                        Constant.LOGIN_USERID = id;
                        Constant.LOGIN_USERNAME = username;
                        Constant.LOGINNAME = NAME;
                        Constant.HEAD = HEAD;
                        Constant.CORP_NAME = CORP_NAME;
                        Constant.DEPT_NAME = DEPT_NAME;
                        Constant.DUTY_NAME = DUTY_NAME;
                        Constant.ROLE_NAME = ROLE_NAME;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();

    }

    private void login(final String uname, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.LOGIN;
                String params = "oper=" +
                        Constant.LOGIN_INFO + "&" + Constant.USERNAME + "=" + uname + "&" + Constant.PASSWORD + "=" + GetMD5.string2MD5(uname + password) + "&" + Constant.DEVICESID + "=" + Constant.LOCATION_IME;
                String json = GetSession.post(domain, params);
                Log.i("---login---", json);
                if (json.equals("+ER+")) {
                    Message message = new Message();
                    message.what = NONETWORK;
                    handler.sendMessage(message);
                } else if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String result = jsonObject.getString("result");
                        String reason = jsonObject.getString("reason");
                        String token = jsonObject.getString("token");
                        Log.i("---token", token + "");
                        Message message = handler.obtainMessage();
                        if (result.equals("fail")) {
                            message.obj = reason;
                            message.what = RETRIVE;
                        } else if (result.equals("succ")) {
                            message.obj = token;
                            message.what = LOADSUCCESS;
                        }
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void IntentFoundPassword(int person) {
        Intent intent = new Intent();
        if (person == 1) {
            intent.setClass(LoginActivity1.this, FoundPasswordActivity.class);
            startActivity(intent);
        } else if (person == 2) {
            shapeLoadingDialog.setLoadingText("加载中....");
            shapeLoadingDialog.show();
            intent.setClass(LoginActivity1.this, management.elevator.com.elevatormanagementactivity.fragment.MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
