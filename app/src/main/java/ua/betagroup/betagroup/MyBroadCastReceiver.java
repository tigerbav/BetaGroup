package ua.betagroup.betagroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;



public class MyBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, MyService.class);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            context.startService(serviceIntent);
        else
            context.startForegroundService(serviceIntent);
    }
}