package upv.tfg.freeweather.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import upv.tfg.freeweather.R;

public class NotificationsActivity extends AppCompatActivity {

    public static final String CHANNEL_1_ID = "channel1";

    private NotificationManagerCompat notificationManager;

    private Switch notifics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createNotificationChannel();

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notifics = findViewById(R.id.stchNotifications);
        // initiate listener
        notifics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.nube,3)
                            .setContentTitle("València, España")
                            .setContentText("Nublado")
                            .setColor(Color.BLUE)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("Nublado"))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    
                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(0, builder.build());
                }else {
                    notificationManager.cancelAll();
                }
            }
        });

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
