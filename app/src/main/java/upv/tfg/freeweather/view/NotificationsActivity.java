package upv.tfg.freeweather.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.util.List;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.model.NotificationsInteractor;
import upv.tfg.freeweather.presenter.NotificationsPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.view.interfaces.I_NotificationsActivity;

public class NotificationsActivity extends AppCompatActivity implements I_NotificationsActivity {

    public static final String CHANNEL_1_ID = "channel1";

    private I_NotificationsPresenter notifPresenter;

    private NotificationManagerCompat notificationManager;
    private Switch notifics;

    private RadioGroup rgLocations;

    private void setupMVP() {
        // Create the Presenter
        I_NotificationsPresenter presenter = new NotificationsPresenter(this, getApplicationContext());
        // Create the Model
        NotificationsInteractor model = new NotificationsInteractor(presenter, getApplicationContext());
        // Set presenter model
        presenter.setModel(model);
        // Set the Presenter as a interface
        notifPresenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupMVP();

        notifics = findViewById(R.id.stchNotifications);
        rgLocations = findViewById(R.id.rgLocations);

        notifPresenter.notifyGetFavoriteLocations();

        createNotificationChannel();

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
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

    @Override
    public void setDataRadioGroup(List<RadioButton> lRadioButton) {
        for(int i = 0; i < lRadioButton.size(); i++){
            //RadioButton r = lRadioButton.get(i);
            //rgLocations.addView(lRadioButton.get(i));
            //rgLocations.addChildrenForAccessibility();

            RadioButton rb_flash = new RadioButton(getApplicationContext());

            // Set a Text for new RadioButton
            rb_flash.setText("Flash");
            // Set the text color of Radio Button
            rb_flash.setTextColor(Color.BLACK);

            // Finally, add the new RadioButton to the RadioGroup
            rgLocations.addView(rb_flash);
        }
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
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
