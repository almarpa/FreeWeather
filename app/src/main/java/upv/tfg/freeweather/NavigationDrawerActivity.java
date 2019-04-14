package upv.tfg.freeweather;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import upv.tfg.freeweather.model.db.DBInitialization;
import upv.tfg.freeweather.view.HomeFragment;
import upv.tfg.freeweather.view.MapsFragment;
import upv.tfg.freeweather.view.WarningsFragment;
import upv.tfg.freeweather.view.GeolocationFragment;
import upv.tfg.freeweather.view.FavouritesFragment;

/**
 * Shows a navigation drawer activity with some options.
 */
public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DBInitialization myhelper = new DBInitialization(this);
    private SQLiteDatabase db;

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        //If the app starts for the first time, the database is loaded.
        SharedPreferences prefs = getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        if (firstStart==true){
            fillBD();
        }

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new HomeFragment())
                    .commit();
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
            case R.id.mHome:
                tag = "home";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                toolBar.setTitle(R.string.title_home);
                break;

            case R.id.mGeolocation:
                tag = "geolocation";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new GeolocationFragment();
                }
                toolBar.setTitle(R.string.title_localizame);
                break;

            case R.id.mFavorites:
                tag = "favorites";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new FavouritesFragment();
                }
                toolBar.setTitle(R.string.title_favoritos);
                break;

            case R.id.mWarnings:
                tag = "warnings";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new WarningsFragment();
                }
                toolBar.setTitle(R.string.title_avisos);
                break;

            case R.id.mMaps:
                tag = "maps";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new MapsFragment();
                }
                toolBar.setTitle(R.string.title_mapa);
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment, tag)
                .commit();

        item.setChecked(true);

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    //Fill the database with all the locations from the .csv file.
    public void fillBD(){
        String mCSVfile = "codmunicip_v1.csv";

        AssetManager manager = getAssets();
        InputStream inStream = null;

        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream, "ISO-8859-1"));
            String line;

            db = myhelper.getWritableDatabase();
            db.beginTransaction();

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
        } catch (IOException e ) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
    }
}
