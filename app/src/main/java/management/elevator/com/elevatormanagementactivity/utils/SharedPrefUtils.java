package management.elevator.com.elevatormanagementactivity.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class SharedPrefUtils {
    private static final String SHARED_PREF_LOGIN = "login";

    public static void login(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", true);
        editor.commit();
    }

    public static void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", false);
        editor.commit();
    }

    public static boolean isLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login", false);
    }
}
