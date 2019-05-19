package upv.tfg.freeweather.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.view.NavigationDrawerActivity;

/**
 * Create the notification
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_1_ID = "channel1";
    private int MID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, NavigationDrawerActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        notificationIntent.putExtra("FROM_NOTIFICATION",true);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(intent.getIntExtra("IMAGE", R.drawable.copo_nieve))
                .setContentTitle(intent.getStringExtra("NOMBRE")+", España")
                .setContentText(intent.getStringExtra("ESTADO_CIELO")+ ", " + intent.getStringExtra("TEMPERATURA"))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(intent.getStringExtra("ESTADO_CIELO")+ ", " + intent.getStringExtra("TEMPERATURA")))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;
    }
}

