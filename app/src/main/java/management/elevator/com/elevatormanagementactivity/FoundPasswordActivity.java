package management.elevator.com.elevatormanagementactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;

import management.elevator.com.elevatormanagementactivity.utils.GetMD5;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class FoundPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mCode, mPassword2, mPassword1, mPhone;
    Button getCode, mOk;
    private static final int GETCODEERROR = 1;
    private static final int GETCODESUCC = 2;
    private static final int CODESUCCESS = 3;
    private static final int CODEFAIL = 4;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETCODEERROR:
                    String error = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "获取验证码失败" , Toast.LENGTH_LONG).show();
                    break;
                case GETCODESUCC:
                    String succ = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "获取验证码成功！" , Toast.LENGTH_LONG).show();
                    break;
                case CODESUCCESS:
                    String codesuccess = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "恭喜您修改密码成功！" , Toast.LENGTH_LONG).show();

                    break;
                case CODEFAIL:
                    String codefail = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), "很抱歉　修改密码失败" , Toast.LENGTH_LONG).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_password);
        init();


    }

    private void init() {
        mCode = (EditText) findViewById(R.id.ed_code);
        mPassword1 = (EditText) findViewById(R.id.et_password1);
        mPassword2 = (EditText) findViewById(R.id.et_password2);
        mPhone = (EditText) findViewById(R.id.et_phone);
        getCode = (Button) findViewById(R.id.btn_getcode);
        getCode.setOnClickListener(this);
        mOk = (Button) findViewById(R.id.btn_commit);
        mOk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                String phonenumber = mPhone.getText().toString();
                String password = mPassword2.getText().toString();
                String code = mCode.getText().toString();
                if (cangetcommit()) {
                    loadNewPassword(phonenumber, password, code);
                }

                break;
            case R.id.btn_getcode:
                get_Code();
                break;

        }
    }

    private void get_Code() {
        if (canGetCode()) {
            loadCode(mPhone.getText().toString());
        }
    }

    private boolean cangetcommit() {
        String password1 = mPassword1.getText().toString().trim();
        String password2 = mPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(password1)) {
            showSnackBar("新的密码不能为空");
            return false;
        } else if (TextUtils.isEmpty(password2)) {
            showSnackBar("请在确认新的密码");
            return false;

        } else if (!password1.contentEquals(password2)) {
            showSnackBar("两次密码不相同");
            return false;

        } else if (password1.length() < 4) {
            showSnackBar("新的密码太短了");

            return false;
        }
        return true;
    }

    private boolean canGetCode() {
        String phonenumber = mPhone.getText().toString().trim();

        if (TextUtils.isEmpty(phonenumber)) {
            showSnackBar("用户名不能为空");
            return false;
        }
        return true;
    }

    private void loadCode(final String uname) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.LOGIN;
                String params = "oper=" +
                        Constant.IDCODE + "&" + Constant.USERNAME + "=" + uname + "&" + Constant.DEVICESID + "=" + Constant.LOCATION_IME;
                String json = GetSession.post(domain, params);
                Log.i("", domain);
                Log.i("", params);
                Log.i("---login---", json);
                if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String result = jsonObject.getString("result");
                        String reason = jsonObject.getString("reason");
                        Message message = handler.obtainMessage();
                        message.obj = reason;
                        if (result.equals("fail")) {
                            message.what = GETCODEERROR;
                        } else if (result.equals("succ")) {
                            message.what = GETCODESUCC;
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


    private void showSnackBar(String showtext) {
        SnackbarManager.show(Snackbar.with(getApplicationContext()).text(showtext).textColor(Color.RED).color(Color.BLUE).actionLabel("Action").actionColor(Color.RED).actionListener(new ActionClickListener() {
            @Override
            public void onActionClicked(Snackbar snackbar) {

            }
        }), this);

    }

    private void loadNewPassword(final String uname, final String pass, final String code) {
//        String md5=GetMD5.string2MD5(uname+pass);
//        Log.i("--md5",md5);
//        OkHttpUtils.post().tag(this).
//                url(Constant.BASE_URL+Constant.LOGIN).

//                addParams(Constant.USERNAME,uname).
//                addParams(Constant.PASSWORD, GetMD5.string2MD5(uname+pass)).
//                addParams(Constant.CODE,code).
//                build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Log.i("---loadnewpassword----",response);
//
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.LOGIN;
                String params = "oper=" +
                        Constant.RESET + "&" + Constant.USERNAME + "=" + uname + "&" + Constant.PASSWORD + "=" + GetMD5.string2MD5(uname + pass) + "&" + Constant.CODE + "=" + code;
                String json = GetSession.post(domain, params);
                Log.i("", domain);
                Log.i("", params);
                Log.i("---login---", json);
                if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String result = jsonObject.getString("result");
                        String reason = jsonObject.getString("reason");
                        Message message = handler.obtainMessage();
                        message.obj = reason;
                        if (result.equals("fail")) {
                            message.what = CODEFAIL;
                        } else if (result.equals("succ")) {
                            message.what = CODESUCCESS;
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
}
