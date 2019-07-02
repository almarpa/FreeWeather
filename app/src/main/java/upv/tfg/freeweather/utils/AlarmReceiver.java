package upv.tfg.freeweather.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.views.activities.NavigationDrawerActivity;

/**
 * Create the notification
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, NavigationDrawerActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),intent.getIntExtra("IMAGE", 0)))
                .setContentTitle(String.format(context.getResources().getString(R.string.notification_title), intent.getStringExtra("NAME"),context.getResources().getString(R.string.country)))
                .setContentText(String.format(context.getResources().getString(R.string.notification_title), intent.getStringExtra("STATE"),intent.getStringExtra("TEMPERATURE")))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(String.format(context.getResources().getString(R.string.notification_title), intent.getStringExtra("STATE"), intent.getStringExtra("TEMPERATURE"))))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(0, mNotifyBuilder.build());
    }
}

