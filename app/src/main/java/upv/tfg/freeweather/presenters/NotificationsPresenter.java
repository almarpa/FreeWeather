package upv.tfg.freeweather.presenters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.data.interactors.NotificationsInteractor;
import upv.tfg.freeweather.data.interactors.interfaces.I_NotificationsInteractor;
import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.data.model.HourlyPrediction;
import upv.tfg.freeweather.presenters.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.utils.AlarmReceiver;
import upv.tfg.freeweather.views.interfaces.I_NotificationsView;


public class NotificationsPresenter extends AppCompatActivity implements I_NotificationsPresenter   {

    private static final String CHANNEL_1_ID = "channel1";

    // View reference
    private I_NotificationsView view;
    // Model reference
    private I_NotificationsInteractor interactor;

    private Context context;
    private HourlyPrediction[] hp;
    private DailyPrediction[] dp;
    private NotificationManagerCompat manager;
    private int hour;

    public NotificationsPresenter(I_NotificationsView view, Context context) {
        this.view = view;
        this.context = context;

        // Creating the interactor that will interact with the database
        interactor = new NotificationsInteractor(this, context);
        // Initialize the notification manager
        manager = NotificationManagerCompat.from(context);

        if(interactor.getLastSwitch() == 1){
            view.doSwitch();
            view.setCurrentNotification(interactor.getCurrenteNotification());
        }
        // Poblate the radiogroup items in the view
        getNotificationTimes();
        getFavouriteLocations();
        //TODO: createNotificationsChannel();
    }

    private void createNotificationsChannel() {
        /*
     //TODO:
        CharSequence name = "name";
        String description = "description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
         */
    }

    private void getNotificationTimes(){
        RadioGroup rg = new RadioGroup(context);
        rg.setOrientation(LinearLayout.VERTICAL);
        List<String> list = new ArrayList<>();
        list.add("10:00");
        list.add("14:00");
        list.add("18:00");
        list.add("22:00");
        for (int i = 0; i < list.size(); i++) {
            RadioButton rdbtn = new RadioButton(context);
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(list.get(i));
            if(list.get(i).equals(interactor.getTimeSelected())) {
                rdbtn.setChecked(true);
            }
            rg.addView(rdbtn);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                RadioButton rb = rg.getRootView().findViewById(checkedId);
                interactor.saveTimeOptionChoosed(rb.getText().toString());            }
        });
        view.setTimeView(rg);
    }
    private void getFavouriteLocations() {
        // Obtain the favourite locations saved by the user
        Map<String, ?> mFavourites = interactor.getFavouriteLocations();
        List<String> list = new ArrayList<>();
        final Spinner sp = new Spinner(context);
        for (Map.Entry<String, ?> entry : mFavourites.entrySet()) {

            //Check if the location is saved as favourite
            if (entry.getValue().toString() == "true") {
                list.add(entry.getKey().toString());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        view.setLocationsView(adapter);
    }

    /**
     * Method called by the view to notify that the switch item has been checked
     */
    @Override
    public void notifySwitchChecked() {
        //Cancel previous configured alarms
        manager.cancelAll();

        // Obtain the chosen time and location
        String timeSelected = interactor.getTimeSelected();
        String locationSelected = interactor.getLocationSelected();

        if(timeSelected != null && locationSelected != null){
            switch (timeSelected) {
                case "10:00":
                    hour = 10;
                    view.showAlarmConfigurated(context.getString(R.string.notifMsg) + " " + hour + ":00");
                    break;
                case "14:00":
                    hour = 14;
                    view.showAlarmConfigurated(context.getString(R.string.notifMsg) + " " + hour + ":00");
                    break;
                case "18:00":
                    hour = 18;
                    view.showAlarmConfigurated(context.getString(R.string.notifMsg) + " " + hour + ":00");
                    break;
                case "22:00":
                    hour = 22;
                    view.showAlarmConfigurated(context.getString(R.string.notifMsg) + " " + hour + ":00");
                    break;
            }
            interactor.saveCurrentNotification(context.getString(R.string.activeMsg)  + " " + locationSelected + " at " + timeSelected);
            view.setCurrentNotification(context.getString(R.string.activeMsg) + locationSelected + ", " + timeSelected);

            // Save the Switch state in preferences
            interactor.saveSwitchState(1);

            interactor.getPredictions(locationSelected);
        }else{
            view.clearSwitch();
            if(timeSelected != null){
                if(interactor.getFavouriteLocations().containsValue(true)){
                    view.showNoLocationChecked();
                }else{
                    view.showNoFavouriteExists();
                }
            }else{
                view.showNoTimeChecked();
            }
        }
    }

    /**
     * Method called by the view to notify that the switch item has been unchecked
     */
    @Override
    public void notifySwitchUnchecked() {
        manager.cancelAll();
        interactor.saveSwitchState(0);
        view.clearCurrentNotification();
    }

    /**
     * Method called by the view to notify that the spinner item has been selected
     */
    @Override
    public void notifySpinner2Clicked(String location) {
        interactor.saveLocationChoosed(location);
    }

    @Override
    public void setNotificationData(DailyPrediction[] dp) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("IMAGE", dp[0].getStateImage());
        intent.putExtra("NAME", dp[0].getNombre());
        intent.putExtra("STATE", dp[0].getEstadoCielo());
        intent.putExtra("TEMPERATURE", dp[0].getTemperatura());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public void showNoFavouriteExists() {
        view.showNoFavouriteExists();
    }

    @Override
    public void showHTTPMsgError() {
        view.showHTTPMsgError();
    }
}