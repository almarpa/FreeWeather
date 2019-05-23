package upv.tfg.freeweather.presenter;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import upv.tfg.freeweather.data.NotificationsInteractor;
import upv.tfg.freeweather.data.interfaces.I_NotificationsInteractor;
import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.data.model.HourlyPrediction;
import upv.tfg.freeweather.data.model.serializations.Init;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.utils.AlarmReceiver;
import upv.tfg.freeweather.view.interfaces.I_NotificationsActivity;


public class NotificationsPresenter extends AppCompatActivity implements I_NotificationsPresenter   {

    private static final String CHANNEL_1_ID = "channel1";

    // View reference
    private I_NotificationsActivity view;
    // Model reference
    private I_NotificationsInteractor interactor;

    private Context context;
    private HourlyPrediction[] hp;
    private DailyPrediction[] dp;
    private NotificationManagerCompat manager;
    private int hour;

    public NotificationsPresenter(I_NotificationsActivity view, Context context) {
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
        // Create the notification channel in order to be able to send notifications
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        CharSequence name = "name";
        String description = "description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
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

        RadioGroup rg = new RadioGroup(context);
        rg.setOrientation(LinearLayout.VERTICAL);
        for (Map.Entry<String, ?> entry : mFavourites.entrySet()) {
            //Check if the location is saved as favourite
            if (entry.getValue().toString() == "true") {
                RadioButton rdbtn = new RadioButton(context);
                rdbtn.setId(View.generateViewId());
                rdbtn.setText(entry.getKey());
                if(entry.getKey().equals(interactor.getLocationSelected())){
                    rdbtn.setChecked(true);
                }
                rg.addView(rdbtn);
            }
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                RadioButton rb = rg.getRootView().findViewById(checkedId);
                interactor.saveLocationChoosed(rb.getText().toString());            }
        });
        view.setLocationsView(rg);
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
                    view.showAlarmConfigurated("Your notification will arrive at " + hour + ":00");
                    break;
                case "14:00":
                    hour = 14;
                    view.showAlarmConfigurated("Your notification will arrive at " + hour + ":00");
                    break;
                case "18:00":
                    hour = 18;
                    view.showAlarmConfigurated("Your notification will arrive at " + hour + ":00");
                    break;
                case "22:00":
                    hour = 22;
                    view.showAlarmConfigurated("Your notification will arrive  at " + hour + ":00");
                    break;
            }
            interactor.saveCurrentNotification("Active notification: " + locationSelected + " at " + timeSelected);
            view.setCurrentNotification("Active notification: " + locationSelected + " at " + timeSelected);

            // Save the Switch state in preferences
            interactor.saveSwitchState(1);

            // Run async task to get the prediction at location selected
            AsyncTaskGetPredictions task = new AsyncTaskGetPredictions();
            task.execute(interactor.getCodeByLocation(locationSelected));
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

    private void setNotificationData(DailyPrediction[] dp) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("IMAGE", dp[0].getStateImage());
        intent.putExtra("NOMBRE", dp[0].getNombre());
        intent.putExtra("ESTADO_CIELO", dp[0].getEstadoCielo());
        intent.putExtra("TEMPERATURA", dp[0].getTemperatura());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /**
     * Obtain a DailyPrediction object and an HourlyPrediction object
     */
    public class AsyncTaskGetPredictions extends AsyncTask<String, Void, Void> {

        private Init init;
        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;
        private Gson gson;
        private String location;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... params) {
            location = params[0];
            try {
                //  HOURLY PREDICTION   //
                url = new URL("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/horaria/" + params[0] + "?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                init = gson.fromJson(reader, Init.class);
                reader.close();

                connection.disconnect();

                url = new URL(init.datos);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                hp = gson.fromJson(reader, HourlyPrediction[].class);
                reader.close();

                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //DAILY PREDICTION
            try {
                url = new URL("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" + params[0] + "?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                    builder = new GsonBuilder();
                    gson = builder.create();
                    init = gson.fromJson(reader, Init.class);
                    reader.close();
                }
                connection.disconnect();

                url = new URL(init.datos);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                    builder = new GsonBuilder();
                    gson = builder.create();
                    try {
                        dp = gson.fromJson(reader, DailyPrediction[].class);
                    }catch (RuntimeException e){
                        Log.d("error", "HA SALTADO EXCEPCIÓN");
                    }
                    reader.close();
                }
                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            if(dp!=null && hp!=null){
                setNotificationData(dp);
            }else if(location == null){
                view.showNoFavouriteExists();
            }else{
                view.showHTTPMsgError();
            }
        }

    }
}