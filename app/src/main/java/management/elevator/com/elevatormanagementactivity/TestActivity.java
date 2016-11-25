package management.elevator.com.elevatormanagementactivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String myIEMI=android.provider.Settings.System.getString(getContentResolver(),"android_id");
//        System.out.println(myIEMI+"shuju");
        Log.i("IEMI 数据",""+myIEMI);
//        String myIEM_id= android.os.

    }
}
