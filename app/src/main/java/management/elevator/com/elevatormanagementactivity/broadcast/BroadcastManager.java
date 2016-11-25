package management.elevator.com.elevatormanagementactivity.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import management.elevator.com.elevatormanagementactivity.Action;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class BroadcastManager {
    public static void register(Context context, BroadcastReceiver receiver, String... actions){
        IntentFilter filter = new IntentFilter();
        for (String action: actions){
            filter.addAction(action);
        }
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

    public static void unregister(Context context, BroadcastReceiver receiver){
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }

    public static void sendLoginBroadcast(Context context, int position){
        Intent intent = new Intent(Action.LOGIN);
        intent.putExtra("EXTRA_POSITION", position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendLogoutBroadcast(Context context, int position){
        Intent intent = new Intent(Action.LOGOUT);
        intent.putExtra("EXTRA_POSITION", position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
