package upv.tfg.freeweather.presenter;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.util.Log;

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

import upv.tfg.freeweather.model.HomeInteractor;
import upv.tfg.freeweather.model.entities.DailyPrediction;
import upv.tfg.freeweather.model.entities.HourlyPrediction;
import upv.tfg.freeweather.model.interfaces.I_HomeInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;
import upv.tfg.freeweather.serializations.Init;
import upv.tfg.freeweather.view.interfaces.I_HomeView;

public class HomePresenter implements I_HomePresenter {

    // View reference
    private I_HomeView homeView;
    // Model reference
    private I_HomeInteractor homeInteractor;

    public HomePresenter(I_HomeView view) {
        homeView = view;
    }

    ////////////////////////////////////
    // AVAILABLE METHODS FOR THE VIEW //
    ////////////////////////////////////
    @Override
    public Context getContext() {
        return getContext();
    }

    @Override
    public void setModel(HomeInteractor model) {
        homeInteractor = model;
    }

    @Override
    public void notifyFavButtonClicked(String location) {
        homeInteractor.notifyFavButtonClicked(location);
    }

    @Override
    public void notifyIsItFavourite(String location) {
        boolean isFavourite = homeInteractor.isItFavourite(location);
        if (isFavourite) {
            homeView.makeFavourite();
        }
    }

    @Override
    public void notifySearchTextChanged(String text) {
        //Obtain the list of possible locations suggested by the text
        List<String> possibleLocations = homeInteractor.findPossibleLocation(text);
        List<String> suggestions = new ArrayList<>();

        if (possibleLocations != null) {
            for (int i = 0; i < possibleLocations.size(); i++) {
                suggestions.add(possibleLocations.get(i));
            }

            String[] columns = {BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1};
            MatrixCursor c = new MatrixCursor(columns);
            for (int i = 0; i < possibleLocations.size(); i++) {
                String[] tmp = {Integer.toString(i), suggestions.get(i)};
                c.addRow(tmp);
            }
            //Notify the view to show the suggestions
            homeView.displaySearchSuggestions(c);
        }
    }

    @Override
    public void notifySearchPrediction(String location) {
        Integer code = homeInteractor.getCodeByLocation(location);
        if (code != null) {
            //Obtain the predictions from the API
            HTTPConnection hc = new HTTPConnection();
            hc.execute(code);
        } else {
            //Notify view to show a warning msg
            homeView.showMsgNoLocation(location);
        }
    }

    /////////////////////////////////////
    // AVAILABLE METHODS FOR THE MODEL //
    /////////////////////////////////////
    @Override
    public void makeFavourite() {
        homeView.makeFavourite();
    }

    @Override
    public void removeFavourite() {
        homeView.removeFavourite();
    }


    /////////////////////////////////
    ////////  API CONNECTION    /////
    ////////  HOURLY and DAILY  /////
    ////////  PREDICTION        /////
    /////////////////////////////////
    public class HTTPConnection extends AsyncTask<Integer, Void, Void> {

        private Init init;
        private HourlyPrediction[] hp;
        private DailyPrediction[] dp;

        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;
        private Gson gson;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Integer... params) {
            /*
            try {
                //  HOURLY PREDICTION   //
                // 1º HTTP REQUEST
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

                connection.disconnect();

                // 2º HTTP REQUEST
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

                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                //DAILY PREDICTION
            */
            try {
                //Primera Peticion GET
                url = new URL("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" + params[0] + "?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                init = gson.fromJson(reader, Init.class);

                connection.disconnect();

                //Segunda Peticion GET
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
                    dp = gson.fromJson(reader, DailyPrediction[].class);
                } else {
                    Log.d("prueba", "Error");
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
            homeView.displayPredictions(hp, dp);
        }
    }
}
