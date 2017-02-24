package management.elevator.com.elevatormanagementactivity.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import management.elevator.com.elevatormanagementactivity.widget.Constant;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class GetSession {
    public static String tag=GetSession.class.getSimpleName();
    public static String post(String domain, String param) {
        Log.i("POST", domain + "?" + param);
        String result = "+ER+";
//        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            url = new URL(domain);
            connection = (HttpURLConnection) url.openConnection();
            if(Constant.HttpSessionId != null) {
                Log.i(tag, "SESSIONID=" +Constant.HttpSessionId );
                connection.setRequestProperty("Cookie",Constant.HttpSessionId );
            }
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestMethod("POST");
            DataOutputStream dop = new DataOutputStream(connection.getOutputStream());
            dop.writeBytes(param);
            dop.flush();
            dop.close();
            int code = connection.getResponseCode();
            Log.d("GetSession", "code: "+code);
            if(code == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer strBuffer = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    strBuffer.append(line);
                }
                result = strBuffer.toString();
                Log.d("GetSession", "post: "+result);
                if(Constant.HttpSessionId == null) {
                    Constant.HttpSetCookies = connection.getHeaderFields().get("Set-Cookie");
                    for(String value : Constant.HttpSetCookies) {
                        if(value.toUpperCase(Locale.US).indexOf("SESSIONID") > 0) {
                            Constant.HttpSessionId = snatchVal(value, ";", 0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null) connection.disconnect();
            if(reader != null) try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result.trim();
    }
  public static String snatchVal(String src, String delimeter, int index) {
        String ret = "";
        if(src.isEmpty()) return ret;
        String[] ss = src.split(delimeter);
        if(ss.length > index) {
            ret = ss[index];
        }
        return ret;
    }

}
