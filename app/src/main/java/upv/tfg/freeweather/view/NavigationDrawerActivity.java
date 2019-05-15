package upv.tfg.freeweather.view;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.presenter.NavigationDrawerPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_NavigationDrawerPresenter;
import upv.tfg.freeweather.view.interfaces.I_NavigationDrawerView;

/**
 * Shows a navigation drawer activity with some menu options.
 */
public class NavigationDrawerActivity extends AppCompatActivity implements I_NavigationDrawerView, NavigationView.OnNavigationItemSelectedListener {

    //Presenter reference
    private I_NavigationDrawerPresenter presenter;

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        toolBar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new HomeFragment())
                    .commit();
        }

        // Create the Presenter
        presenter = new NavigationDrawerPresenter(this, getApplicationContext());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return false;
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
}
