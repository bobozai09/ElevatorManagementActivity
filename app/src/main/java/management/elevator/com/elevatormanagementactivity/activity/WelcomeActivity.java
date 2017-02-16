package management.elevator.com.elevatormanagementactivity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.LoginActivity1;
import management.elevator.com.elevatormanagementactivity.R;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class WelcomeActivity extends Activity {
    private final int SPLASH_DISPLAY_TIME=2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(WelcomeActivity.this,
                        LoginActivity1.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }

        }, SPLASH_DISPLAY_TIME);

    }

}
