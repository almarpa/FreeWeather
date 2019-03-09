package upv.tfg.freeweather;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import upv.tfg.freeweather.Serializaciones.*;
import upv.tfg.freeweather.fragments.GridImageFragment;
import upv.tfg.freeweather.fragments.ListStringFragment;
import upv.tfg.freeweather.fragments.LogInFragment;
import upv.tfg.freeweather.fragments.SignInFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DBController myhelper = new DBController(this);
    SQLiteDatabase db;

    Toolbar toolBar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        if (firstStart==true){
            fillBD();
        }

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle(R.string.app_name);

        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.openDrawer(GravityCompat.START);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new LogInFragment())
                    .commit();

            ((NavigationView) findViewById(R.id.navView)).setCheckedItem(R.id.mLoginNavigation);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        String tag = null;

        switch (item.getItemId()) {

            case R.id.mLoginNavigation:
                tag = "login";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = LogInFragment.newInstance("David");
                }
                toolBar.setTitle(R.string.title_login_fragment);
                break;

            case R.id.mSignInNavigation:
                tag = "signin";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new SignInFragment();
                }
                toolBar.setTitle(R.string.title_signin_fragment);
                break;

            case R.id.mListNavigation:
                tag = "list";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new ListStringFragment();
                }
                toolBar.setTitle(R.string.title_list_fragment);
                break;

            case R.id.mGridNavigation:
                tag = "grid";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new GridImageFragment();
                }
                toolBar.setTitle(R.string.title_grid_fragment);
                break;
        }

        // Replace the existing fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment, tag)
                .commit();

        item.setChecked(true);

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void fillBD(){
        String mCSVfile = "codmunicip_nuevo.csv";

        AssetManager manager = getAssets();
        InputStream inStream = null;

        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;

        db = myhelper.getWritableDatabase();
        db.beginTransaction();

        try {
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");

                ContentValues cv = new ContentValues();
                cv.put("codAuto", colums[0].trim());
                cv.put("cPro", colums[1].trim());
                cv.put("cMun", colums[2].trim());
                cv.put("DC", colums[3].trim());
                cv.put("nombre", colums[4].trim());
                db.insert("tblLocalidades", null, cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
    }

    public void onClickButton(View view) {
        HTTPConnection task = new HTTPConnection();
        task.execute();
    }

    private void displayData(PrediccionHoraria[] sp) {
        /*
        TextView tvDatos =  findViewById(R.id.tvDatos);
        String text =
                "HORARIA\n"+
                " Descripcion de predicciones: " + sp[0].getPredic().getElementHorario(0).getElement(5).getDescripcion()+",\n" +
                " Elaborado: "+sp[0].getElaborado()+",\n" +
                " Nombre: "+sp[0].getNombre()+",\n" +
                " Provincia: "+sp[0].getProvincia();
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

        private SerializadorInicial gs;
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
                sp = gson.fromJson(reader, PrediccionHoraria[].class);

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
