package management.elevator.com.elevatormanagementactivity.widget;

import android.app.Application;

import com.github.yoojia.anyversion.AnyVersion;
import com.github.yoojia.anyversion.Version;
import com.github.yoojia.anyversion.VersionParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class AnyVersionApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AnyVersion.init(this, new VersionParser() {
            @Override
            public Version onParse(String response) {
                final JSONTokener token=new JSONTokener(response);
                try{
                    JSONObject json= (JSONObject) token.nextValue();
                    return new Version(
                            json.getString("appname"),
                            json.getString("verdesc"),
                            json.getString("vername"),
                            json.getInt("vercode")
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
