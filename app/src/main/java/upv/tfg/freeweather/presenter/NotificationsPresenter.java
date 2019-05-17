package upv.tfg.freeweather.presenter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
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
import java.util.List;
import java.util.Map;

import upv.tfg.freeweather.model.NotificationsInteractor;
import upv.tfg.freeweather.model.entities.DailyPrediction;
import upv.tfg.freeweather.model.entities.HourlyPrediction;
import upv.tfg.freeweather.model.interfaces.I_NotificationsInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.serializations.Init;
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

    public NotificationsPresenter(I_NotificationsActivity view, Context context) {
        this.view = view;
        this.context = context;

        // Creating the interactor that will interact with the database
        interactor = new NotificationsInteractor(this, context);
        // Initialize the notification manager
        manager = NotificationManagerCompat.from(context);

        if(interactor.getLastSwitch()){
            view.doSwitch();
        }
        // Poblate the radiogroup items in the view
        getIntervalTimes();
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
    private void getIntervalTimes(){
        RadioGroup rg = new RadioGroup(context);
        rg.setOrientation(LinearLayout.VERTICAL);
        List<String> list = new ArrayList<>();
        list.add("Morning (12:00 am)");
        list.add("Afternoon (06:00 pm)");
        for (int i = 0; i < list.size(); i++) {
            RadioButton rdbtn = new RadioButton(context);
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(list.get(i));
            if(list.get(i).equals(interactor.getTimeChoosed())) {
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
        view.setIntervalTimesView(rg);
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
                if(entry.getKey().equals(interactor.getLocationChoosed())){
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
        // Obtain the radio button text (interval time) selected
        String timeChoosed = interactor.getTimeChoosed();
        // Obtain the radio button text (location) selected
        String locationChoosed = interactor.getLocationChoosed();
        if(timeChoosed != null && locationChoosed != null){
            String code = interactor.getCodeByLocation(locationChoosed);
            AsyncTaskGetNotificationInfo asyncTask = new AsyncTaskGetNotificationInfo();
            asyncTask.execute(code);
        }else{
            view.clearSwitch();
            if(timeChoosed != null){
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
    }

    /**
     * Asynchronous task that obtain a DailyPrediction object and a HourlyPrediction object
     */
    public class AsyncTaskGetNotificationInfo extends AsyncTask<String, Void, Void> {

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
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                        .setSmallIcon(dp[0].getStateImage())
                        .setContentTitle(dp[0].getNombre()+", España")
                        .setContentText(dp[0].getEstadoCielo()+ ", " + dp[0].getTemperatura())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(dp[0].getEstadoCielo()+ ", " + dp[0].getTemperatura()))
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                manager.notify(0, builder.build());
            }else if(location == null){
                view.showNoFavouriteExists();
            }else{
                view.showHTTPMsgError();
            }
        }
    }
}