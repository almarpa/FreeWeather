package upv.tfg.freeweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import upv.tfg.freeweather.Serializations.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButton(View view) {
        HTTPConnection task = new HTTPConnection();
        task.execute();
    }

    private void displayData(PrediccionHoraria[] sp) {
        TextView tvDatos =  findViewById(R.id.tvDatos);
        String texto =
                "Origen: " + sp[0].getOrigen().getProductor()+"," +
                " Elaborado: "+sp[0].getElaborado()+"," +
                " Nombre: "+sp[0].getNombre()+"," +
                " Provincia: "+sp[0].getProvincia();
        tvDatos.setText(texto);
    }

    // Conexion http con la API
    public class HTTPConnection extends AsyncTask<Void, Void, Void> {

        private GsonData gs;
        private PrediccionHoraria[] sp;

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
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream());
                builder = new GsonBuilder();
                gson = builder.create();
                gs = gson.fromJson(reader, GsonData.class);

                connection.disconnect();


                //Segunda Peticion GET
                url = new URL(gs.datos);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream());
                builder = new GsonBuilder();
                gson = builder.create();
                sp = gson.fromJson(reader, PrediccionHoraria[].class);

                connection.disconnect();
            /*
                InputStream is = new URL(gs.datos).openStream();
                try {

                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);

                    Gson gson = new Gson();
                    sp = gson.fromJson(jsonText, SpecificPrediction[].class);
                } finally {
                    is.close();
                }
            */
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

        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }
    }
}
