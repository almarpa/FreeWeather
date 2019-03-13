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

import upv.tfg.freeweather.fragments.MainWindowFragment;
import upv.tfg.freeweather.fragments.RadarFragment;
import upv.tfg.freeweather.fragments.WarningsFragment;
import upv.tfg.freeweather.fragments.GeolocationFragment;
import upv.tfg.freeweather.fragments.FavoritesFragment;

/**
 * Shows a navigation drawer activity with some options to see predictions and another options.
 */
public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DBController myhelper = new DBController(this);
    SQLiteDatabase db;

    Toolbar toolBar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

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
                    .replace(R.id.frameLayout, new MainWindowFragment())
                    .commit();

            ((NavigationView) findViewById(R.id.navView)).setCheckedItem(R.id.mGeolocation);
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
                    fragment = new FavoritesFragment();
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

            case R.id.mRadar:
                tag = "radar";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new RadarFragment();
                }
                toolBar.setTitle(R.string.title_radar);
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

    //Fill the database with all the localities from the csv.
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

}
