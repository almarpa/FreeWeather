package upv.tfg.freeweather.tasks;

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

import upv.tfg.freeweather.model.entities.DailyPrediction;
import upv.tfg.freeweather.model.entities.HourlyPrediction;
import upv.tfg.freeweather.presenter.HomePresenter;
import upv.tfg.freeweather.serializations.Init;

/////////////////////////////////
////////  API CONNECTION    /////
////////  HOURLY and DAILY  /////
////////  PREDICTION        /////
/////////////////////////////////
public class TaskGetPredictions extends AsyncTask<String, Void, Void> {

    HomePresenter caller;

    private Init init;
    private HourlyPrediction[] hp;
    private DailyPrediction[] dp;

    private URL url;
    private HttpURLConnection connection;
    private InputStreamReader reader;
    private GsonBuilder builder;
    private Gson gson;

    public TaskGetPredictions(HomePresenter mainActivity) {
        this.caller = mainActivity;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(String... params) {
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
        if (hp != null && dp != null){
            caller.onTaskCompleted(hp, dp);
        }else{
            caller.onTaskError();
        }
    }
}