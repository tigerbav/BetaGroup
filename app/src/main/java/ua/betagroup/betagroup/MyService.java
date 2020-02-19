package ua.betagroup.betagroup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;

import androidx.core.app.NotificationCompat;

import ua.betagroup.betagroup.CheckInfo.Checking;
import ua.betagroup.betagroup.WebView.MainActivity;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String message;
        int a =  (int) ( Math.random() * 2);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();

        if(countryCodeValue.equals("ua") ||
                countryCodeValue.equals("ru") ||
                countryCodeValue.equals("kz"))
            message = Constants.SNG[a];
        else
            message = Constants.ENG[a];

        MainActivity.setUpAlarm(getApplicationContext(), new Intent(this, MyBroadCastReceiver.class), 28_800_000);

        Intent resultIntent = new Intent(this, Checking.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channel_id = createNotificationChannel(getApplicationContext());

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


            NotificationChannel mChannel = null;
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), channel_id);
            notificationBuilder.setContentTitle(getApplicationContext().getString(R.string.app_name))
                    .setSmallIcon(R.drawable.ic_launcher_foreground)

                    .setContentText(message)
                    .setContentIntent(resultPendingIntent);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(channel_id, getString(R.string.app_name), importance);
                // Configure the notification channel.
                mChannel.setDescription(message);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(mChannel);
            } else {
                notificationBuilder.setContentTitle(getApplicationContext().getString(R.string.app_name))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[]{100, 250})
                        .setLights(Color.YELLOW, 500, 5000)
                        .setAutoCancel(true);
            }
            mNotificationManager.notify(1, notificationBuilder.build());
        }
        else {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getApplicationContext().getString(R.string.app_name))
                            .setContentText(message);

            Notification notification = builder.build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }

        return super.onStartCommand(intent, flags, startId);
    }
    public static String createNotificationChannel(Context context) {

        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "Channel_id";

            CharSequence channelName = "Application_name";

            String channelDescription = "Application_name Alert";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            boolean channelEnableVibrate = true;

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, channelImportance);
            notificationChannel.setDescription(channelDescription);
            notificationChannel.enableVibration(channelEnableVibrate);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);

            return channelId;
        } else {
            // Returns null for pre-O (26) devices.
            return null;
        }
    }
}
