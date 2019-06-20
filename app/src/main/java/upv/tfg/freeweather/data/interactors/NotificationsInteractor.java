package upv.tfg.freeweather.data.interactors;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import upv.tfg.freeweather.data.interactors.interfaces.I_NotificationsInteractor;
import upv.tfg.freeweather.data.local.DatabaseHelper;
import upv.tfg.freeweather.data.local.PreferencesHelper;
import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.data.model.serializations.Init;
import upv.tfg.freeweather.presenters.interfaces.I_NotificationsPresenter;

public class NotificationsInteractor implements I_NotificationsInteractor {

    //Presenter reference
    private I_NotificationsPresenter presenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;

    private DailyPrediction[] dp;

    public NotificationsInteractor(I_NotificationsPresenter presenter, Context context) {
        this.presenter = presenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
    }

    ////////////////////////////////
    ///       DATABASE          ////
    ////////////////////////////////
    @Override
    public String getCodeByLocation(String location) {
        return dbhelper.getCodeByLocation(location);
    }

    ////////////////////////////////
    ///       PREFERENCES       ////
    ////////////////////////////////
    @Override
    public Map<String,?> getFavouriteLocations() {
        return prefHelper.getAllFavourites();
    }

    @Override
    public void saveCurrentNotification(String text) {
        prefHelper.saveCurrentNotification(text);
    }

    @Override
    public void saveSwitchState(int checked) {
        prefHelper.saveSwicthState(checked);
    }

    @Override
    public void saveTimeOptionChoosed(String time) {
        prefHelper.saveTimeOptionChoosed(time);
    }

    @Override
    public void saveLocationChoosed(String location) {
        prefHelper.saveLocationChoosed(location);
    }

    @Override
    public String getCurrenteNotification() {
        return prefHelper.getCurrentNotification();
    }

    @Override
    public int getLastSwitch() {
        return prefHelper.getLastSwitch();
    }

    @Override
    public String getTimeSelected() {
        return prefHelper.getIntervalTimeChoosed();
    }

    @Override
    public String getLocationSelected() {
        return prefHelper.getLocationChoosed();
    }

    ////////////////////////////////
    ///       ASYNCTASKS        ////
    ////////////////////////////////
    @Override
    public void getPredictions(String location){
        //Obtain code by location passed
        String code = getCodeByLocation(location);

        // Run async task to get the prediction at location selected
        AsyncTaskGetPredictions task = new AsyncTaskGetPredictions();
        task.execute(code);
    }

    /**
     * Asynchronous task that obtain a DailyPrediction object and a HourlyPrediction object
     */
    public class AsyncTaskGetPredictions extends AsyncTask<String, Void, Void> {

        private Init init;
        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;

        private Gson gson;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... params) {
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
                        Log.d("error", "Exception");
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
            if(dp!=null){
                presenter.setNotificationData(dp);
            }else if(params == null){
                presenter.showNoFavouriteExists();
            }else{
                presenter.showHTTPMsgError();
            }
        }
    }
}