package upv.tfg.freeweather.presenter;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
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
    private I_HomeView view;
    // Model reference
    private I_HomeInteractor interactor;

    private Context context;
    private HourlyPrediction[] hp;
    private DailyPrediction[] dp;

    public HomePresenter(I_HomeView view, Context context) {
        this.view = view;
        this.context = context;

        // Creating the interactor that will interact with the database
        interactor = new HomeInteractor(this, context);
    }

    /**
     * Method called by the view to notify that favourite button has clicked
     * @param location
     */
    @Override
    public void notifyFavButtonClicked(String location) {
        if(!location.equals("")){
            boolean res = interactor.notifyFavButtonClicked(location);
            if (res) {
                interactor.removeFavouritefromPreferences(location);
                view.removeFavourite();
            } else {
                interactor.addFavouriteinPreferences(location);
                view.makeFavourite();
            }
        }else{
            view.showMsgLocationEmpty();
        }
    }
    /**
     * Method called by the view to ask the model if the location gave as parameter is favourite
     * @param location
     */
    @Override
    public void notifyIsItFavourite(String location) {
        boolean isFavourite = interactor.isItFavourite(location);
        if (isFavourite) {
            view.makeFavourite();
        }else{
            view.removeFavourite();
        }
    }
    /**
     * Method called by the view to notify that user has introduced some text
     * @param text
     */
    @Override
    public void notifySearchTextChanged(String text) {
        if(text.length() > 1) {
            //Obtain the list of possible locations suggested by the text introduced
            List<String> possibleLocations = interactor.findPossibleLocation(text);
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
                view.displaySearchSuggestions(c);
            }
        }
    }
    /**
     * Method called by the view to notify that user has send a search
     * @param location
     */
    @Override
    public void notifySearchPrediction(String location) {
        String code = interactor.getCodeByLocation(location);
        if (code != null) {
            view.setProgressBarVisible();
            //Obtain predictions from the API
            AsyncTaskGetPredictions task = new AsyncTaskGetPredictions();
            task.execute(code);
        } else {
            view.showMsgNoLocationFound(location);
        }
    }
    /**
     * Method called by the model to set the favourite icon
     */
    @Override
    public void makeFavourite() {
        view.makeFavourite();
    }
    /**
     * Method called by the model to set the favourite icon
     */
    @Override
    public void removeFavourite() {
        view.removeFavourite();
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
                view.displayPredictions(hp,dp);
                view.setProgressBarInvisible();
            }else{
                view.showMsgHTTPError();
            }
        }
    }
}
