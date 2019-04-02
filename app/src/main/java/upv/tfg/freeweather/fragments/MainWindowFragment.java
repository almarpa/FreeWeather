package upv.tfg.freeweather.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.serializations.HourlyPrediction;
import upv.tfg.freeweather.serializations.Init;
import upv.tfg.freeweather.serializations.predictions.PD;
import upv.tfg.freeweather.serializations.predictions.PH;

/**
 * Main window allows the view of daily and hourly predictions.
 */
public class MainWindowFragment extends Fragment {

    //Hourly prediction
    PH hp;
    //Dialy prediction
    PD dp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The Fragment can now add actions to the ActionBar and react when they are clicked
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        HTTPConnection hc = new HTTPConnection();
        hc.execute();

        return inflater.inflate(R.layout.fragment_main_window, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        inflater.inflate(R.menu.menu_notifications, menu);
        inflater.inflate(R.menu.menu_configuration,menu);

        //Search option in toolbar
        MenuItem searchItem = menu.findItem(R.id.mSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // There was no custom behaviour for that action, so let the system take care of it
        return super.onOptionsItemSelected(item);
    }


    private void displayData(HourlyPrediction[] sp) {
        /*
        hp = sp[0].getPrediccion();
        TextView tvDatos =  getView().findViewById(R.id.tvDatos);
        String text = "Horaria\n";
        for (int i = 0; i < hp.getHoraria().size(); i ++){
            text += "Dia " + hp.getHoraria().get(i).getFecha() +": \n";
            for (int j = 0; j < hp.getHoraria().get(i).getProbPrecipitacion().size(); j++) {
                text += hp.getHoraria().get(i).getProbPrecipitacion().get(j).getPeriodo() + " " + hp.getHoraria().get(i).getProbPrecipitacion().get(j).getValue()+",\n" ;
            }
        }
        tvDatos.setText(text);

        // EJEMPLO DE CONSULTA A LA TABLA MUNICIPIOS
        db = myhelper.getWritableDatabase();
        String[] args = new String[] {"Utiel"};
        Cursor c = db.rawQuery(" SELECT * FROM tblLocalidades WHERE nombre=? ", args);
        if(c.moveToFirst() && c.getCount() >= 1) {
            do {
                String cd = c.getInt(1) + "" + c.getInt(2) + "" + c.getInt(3) + "" + c.getInt(4) + " " + c.getString(5);
                tvDatos.setText(cd);
            } while (c.moveToNext());
        }
        c.close();
        */
    }

    //Conexion con la API y obtencion del JSON
    public class HTTPConnection extends AsyncTask<Void, Void, Void> {

        private Init init;
        private HourlyPrediction[] sp;

        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;
        private Gson gson;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Primera Peticion GET
                url = new URL("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/horaria/46250?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset=utf-8");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream());
                builder = new GsonBuilder();
                gson = builder.create();
                init = gson.fromJson(reader, Init.class);

                connection.disconnect();

                //Segunda Peticion GET
                url = new URL(init.datos);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset=ISO-8859-1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream());
                builder = new GsonBuilder();
                gson = builder.create();
                sp = gson.fromJson(reader, HourlyPrediction[].class);

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
            displayData(sp);
        }
    }
}
