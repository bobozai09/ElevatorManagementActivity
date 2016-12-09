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
import management.elevator.com.elevatormanagementactivity.broadcast.BroadcastManager;
import management.elevator.com.elevatormanagementactivity.fragment.*;
import management.elevator.com.elevatormanagementactivity.utils.GetMD5;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.utils.SharedPrefUtils;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import okhttp3.Call;
import okhttp3.Cookie;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class LoginActivity1 extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etPassword;
    TextView mForgetPass;

    private static Context context;

    private SharedPreferences sp;
    private static final int RETRIVE = 1;
    private static final int LOADSUCCESS = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RETRIVE:
                    String error = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
                    break;
                case LOADSUCCESS:
                    String success = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "恭喜您登录成功", Toast.LENGTH_LONG).show();
                   Editor editor=sp.edit();
                    editor.putString(Constant.LOGIN_TOKEN,success);
                    editor.commit();
                    Constant.TOKEN=success;
                    IntentFoundPassword(2);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void showSnackbar(String showText) {
        SnackbarManager.show(com.nispok.snackbar.Snackbar.with(getApplicationContext()).text(showText).textColor(Color.GREEN).color(Color.BLUE).actionLabel("action").actionListener(new ActionClickListener() {
            @Override
            public void onActionClicked(com.nispok.snackbar.Snackbar snackbar) {

            }
        }), this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        sp=getSharedPreferences("userinfo",context.MODE_PRIVATE);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#9900FF"));
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        Button button = (Button) findViewById(R.id.login_in_button);
        button.setOnClickListener(this);
        mForgetPass = (TextView) findViewById(R.id.tx_forget_password);
        mForgetPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_in_button) {
            tryLogin();
        } else if (v.getId() == R.id.tx_forget_password) {

            IntentFoundPassword(1);

        }
    }

    void tryLogin() {
        String email = String.valueOf(etEmail.getText()).trim();
        String password = String.valueOf(etPassword.getText()).trim();
        login(email, password);
//        if(check(email, password)){
//            markUserLogin();
//            notifyUserLogin();
//            finish();
//        }
    }

    boolean check(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }

    private void markUserLogin() {
        SharedPrefUtils.login(this);
    }

    private void notifyUserLogin() {
        BroadcastManager.sendLoginBroadcast(this, 1);
    }

    private void login(final String uname, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.LOGIN;
                String params = "oper=" +
                        Constant.LOGIN_INFO + "&" + Constant.USERNAME + "=" + uname + "&" + Constant.PASSWORD + "=" + GetMD5.string2MD5(uname + password) + "&" + Constant.DEVICESID + "=" + Constant.LOCATION_IME;
                String json = GetSession.post(domain, params);
                Log.i("", domain);
                Log.i("", params);
                Log.i("---login---", json);
                if (!json.equals("+ER+")) {
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
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void getauth(final String token) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.INFO;
                String params = "oper=" +
                        Constant.AUTH + "&" + Constant.LOGIN_TOKEN + token;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
//                        String
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
        } else if (person == 2) {
            intent.setClass(LoginActivity1.this, management.elevator.com.elevatormanagementactivity.fragment.MainActivity.class);
        }
        startActivity(intent);
    }

    @TargetApi(19)
    private void setTranslucentstatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winparams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winparams.flags |= bits;

        } else {
            winparams.flags &= ~bits;


        }
        win.setAttributes(winparams);
    }
}
