package upv.tfg.freeweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import upv.tfg.freeweather.Serializaciones.*;
import upv.tfg.freeweather.Serializaciones.Objetos.Horaria;

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

    private void displayData(Prediccion[] sp) {
        TextView tvDatos =  findViewById(R.id.tvDatos);
        String text =
                "HORARIA\n"+
                " Descripcion de predicciones: " + sp[0].getPredic().getElement(0).getElement(5).getDescripcion()+",\n" +
                " Elaborado: "+sp[0].getElaborado()+",\n" +
                " Nombre: "+sp[0].getNombre()+",\n" +
                " Provincia: "+sp[0].getProvincia();
        tvDatos.setText(text);
    }

    //Conexion con la API y obtencion del JSON
    public class HTTPConnection extends AsyncTask<Void, Void, Void> {

        private SerializadorInicial gs;
        private Prediccion[] sp;

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
                gs = gson.fromJson(reader, SerializadorInicial.class);

                connection.disconnect();

                //Segunda Peticion GET
                url = new URL(gs.datos);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset=utf-8");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream());
                builder = new GsonBuilder();
                gson = builder.create();
                sp = gson.fromJson(reader, Prediccion[].class);

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
