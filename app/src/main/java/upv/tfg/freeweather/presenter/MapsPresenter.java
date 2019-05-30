package upv.tfg.freeweather.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import upv.tfg.freeweather.data.model.serializations.Init;
import upv.tfg.freeweather.presenter.interfaces.I_MapsPresenter;
import upv.tfg.freeweather.view.interfaces.I_MapsView;

public class MapsPresenter implements I_MapsPresenter {

    // View reference
    I_MapsView view;

    public MapsPresenter(I_MapsView view, Context context) {
        this.view = view;

        // Creating the interactor that will interact with the database
        //interactor = new MapsPresenter(this, context);
    }

    @Override
    public void notifySpinnerClicked(int position) {
        switch (position) {
            case 0:
                view.setMapImage2Invisible();
                view.closeProgressBar();
                view.clearData();
                break;
            case 1:
                view.initProgressBar();
                ObtainPredictionImage opi = new ObtainPredictionImage();
                opi.execute();
                break;
            case 2:
                view.setMapImage2Invisible();
                view.initProgressBar();
                ObtainRadarImage ori = new ObtainRadarImage();
                ori.execute();
                break;
            case 3:
                view.setMapImage2Invisible();
                view.initProgressBar();
                ObtainThunderImage oti = new ObtainThunderImage();
                oti.execute();
                break;
            case 4:
                view.setMapImage2Invisible();
                view.initProgressBar();
                ObtainFireImage ofi = new ObtainFireImage();
                ofi.execute();
                break;
        }
    }

    /**
     * Asynchronous task that obtain a radar image from JSON
     */
    public class ObtainRadarImage extends AsyncTask<Void, Void, Void> {


        private Init init;
        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;
        private Bitmap img;

        private Gson gson;
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                url = new URL("https://opendata.aemet.es/opendata/api/red/radar/nacional/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");
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
                InputStream is = connection.getInputStream();
                img = BitmapFactory.decodeStream(is, null,null);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void params) {
            view.setMapImage(img);
            view.closeProgressBar();
            view.setTextDescription("-Reflectivity: the color scale indicates reflectivity intervals in decibels Z.\n \n" +
                    "-Height of the echo stop: the color scale indicates altitude intervals in km." + "\n \n" +
                    "-Accumulation of precipitation: the color scale indicates intervals of amount of precipitation, in millimeters");
        }
/*
Reflectividad: la escala de color señala intervalos de reflectividad en decibelios Z." +'\n' +
                                    "Altura del tope de ecos: la escala de color señala intervalos de altitud en km."  + '\n' +
                                    "Acumulación de precipitación: la escala de color indica intervalos de cantidad de precipitación, en milímetros.
*/
    }

    /**
     * Asynchronous task that obtain a climate image from JSON
     */
    public class ObtainPredictionImage extends AsyncTask<Void, Void, Void> {


        private Init init;
        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;
        private Bitmap img;
        private Bitmap img2;

        private Gson gson;
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... params) {
            //First image
            try {
                url = new URL("https://opendata.aemet.es/opendata/api/mapasygraficos/mapassignificativos/esp/c/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");
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
                InputStream is = connection.getInputStream();
                img = BitmapFactory.decodeStream(is, null,null);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Second image
            try {
                url = new URL("https://opendata.aemet.es/opendata/api/mapasygraficos/mapassignificativos/esp/d/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");
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
                InputStream is = connection.getInputStream();
                img2 = BitmapFactory.decodeStream(is, null,null);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void params) {
            view.setMapImage(img);
            view.setMapImage2(img2);
            view.setTextDescription("Significant national maps for the next day, in the time period of 00:12 and 12-24.");
            view.closeProgressBar();
        }
    }

    /**
     * Asynchronous task that obtain a thunderbolt prediction image from JSON
     */
    public class ObtainThunderImage extends AsyncTask<Void, Void, Void> {


        private Init init;
        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;
        private Bitmap img;

        private Gson gson;
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                url = new URL("https://opendata.aemet.es/opendata/api/red/rayos/mapa/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");
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
                InputStream is = connection.getInputStream();
                img = BitmapFactory.decodeStream(is, null,null);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void params) {
            view.setMapImage(img);
            view.closeProgressBar();
            view.setTextDescription("Image of the electric shocks that fell in the national territory during a period of 12 hours.");
        }

    }

    /**
     * Asynchronous task that obtain a fire prediction image from JSON
     */
    public class ObtainFireImage extends AsyncTask<Void, Void, Void> {


        private Init init;
        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;
        private Bitmap img;

        private Gson gson;
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                url = new URL("https://opendata.aemet.es/opendata/api/incendios/mapasriesgo/estimado/area/p/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");
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
                InputStream is = connection.getInputStream();
                img = BitmapFactory.decodeStream(is, null,null);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void params) {
            view.setMapImage(img);
            view.closeProgressBar();
            view.setTextDescription("\n" +
                    "Last map prepared of estimated meteorological risk levels of forest fires in Spain");
        }

    }
}
