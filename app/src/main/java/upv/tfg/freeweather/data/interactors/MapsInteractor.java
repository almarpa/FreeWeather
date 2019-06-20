package upv.tfg.freeweather.data.interactors;

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

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.data.interactors.interfaces.I_MapsInteractor;
import upv.tfg.freeweather.data.model.serializations.Init;
import upv.tfg.freeweather.presenters.interfaces.I_MapsPresenter;

public class MapsInteractor implements I_MapsInteractor {

    private I_MapsPresenter presenter;
    private Context context;

    public MapsInteractor(I_MapsPresenter presenter, Context context){
        this.presenter = presenter;
        this.context = context;
    }

    ///////////////////////////////////
    //        AsyncTasks             //
    ///////////////////////////////////
    @Override
    public void getPredictionMap() {
        ObtainPredictionImage opi = new ObtainPredictionImage();
        opi.execute();
    }

    @Override
    public void getRadarImage() {
        ObtainRadarImage ori = new ObtainRadarImage();
        ori.execute();
    }

    @Override
    public void getRayMap() {
        ObtainThunderImage oti = new ObtainThunderImage();
        oti.execute();
    }

    @Override
    public void getFireMap() {
        ObtainFireImage ofi = new ObtainFireImage();
        ofi.execute();
    }

    @Override
    public void getVegetationMap() {
        ObtainVegetationImage ovi = new ObtainVegetationImage();
        ovi.execute();
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
            presenter.setMapImage(img);
            presenter.closeProgressBar();
            presenter.setTextDescription(context.getString(R.string.radarDescription1) + "\n" +
                    context.getString(R.string.radarDescription2) + "\n" +
                    context.getString(R.string.radarDescription3));
        }
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
            presenter.setMapImage(img);
            presenter.setMapImage2(img2);
            presenter.setTextDescription(context.getString(R.string.predictionDescription));
            presenter.closeProgressBar();
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
            presenter.setMapImage(img);
            presenter.closeProgressBar();
            presenter.setTextDescription(context.getString(R.string.thunderDescription));
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
            presenter.setMapImage(img);
            presenter.closeProgressBar();
            presenter.setTextDescription(context.getString(R.string.fireDescription));
        }

    }

    /**
     * Asynchronous task that obtain a vegetation prediction image from JSON
     */
    public class ObtainVegetationImage extends AsyncTask<Void, Void, Void> {


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
                url = new URL("https://opendata.aemet.es/opendata/api/satelites/producto/nvdi?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");
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
            presenter.setMapImage(img);
            presenter.closeProgressBar();
            presenter.setTextDescription(context.getString(R.string.vegetationDescription));
        }

    }
}
