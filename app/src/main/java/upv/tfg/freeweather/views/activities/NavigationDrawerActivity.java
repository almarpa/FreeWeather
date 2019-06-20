package upv.tfg.freeweather.views.activities;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.presenters.NavigationDrawerPresenter;
import upv.tfg.freeweather.presenters.interfaces.I_NavigationDrawerPresenter;
import upv.tfg.freeweather.views.fragments.FavouritesFragment;
import upv.tfg.freeweather.views.fragments.HelpFragment;
import upv.tfg.freeweather.views.fragments.HomeFragment;
import upv.tfg.freeweather.views.fragments.MapsFragment;
import upv.tfg.freeweather.views.fragments.NotificationsFragment;
import upv.tfg.freeweather.views.interfaces.I_NavigationDrawerView;

/**
 * Shows a navigation drawer activity with some menu options.
 */
public class NavigationDrawerActivity extends AppCompatActivity implements I_NavigationDrawerView, NavigationView.OnNavigationItemSelectedListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    //Presenter reference
    private I_NavigationDrawerPresenter presenter;

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
            case R.id.mFavorites:
                tag = "favorites";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new FavouritesFragment();
                }
                toolBar.setTitle(R.string.title_favourites);
                break;
            case R.id.mMaps:
                tag = "maps";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new MapsFragment();
                }
                toolBar.setTitle(R.string.title_map);
                break;
            case R.id.mNotifications:
                tag = "notifications";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new NotificationsFragment();
                }
                toolBar.setTitle(R.string.title_notifications);
                break;
            case R.id.mHelp:
                tag = "help";
                fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new HelpFragment();
                }
                toolBar.setTitle(R.string.title_help);
                break;

                default:
                    super.onOptionsItemSelected(item);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment, tag)
                .commit();

        item.setChecked(true);

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     *
     * @param requestCode code of the requisted permission
     * @param permissions list of permissions
     * @param grantResults list of permissions granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result array is empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Show a msg
                        Toast.makeText(getApplicationContext(),R.string.permGranted,Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }

        }
    }


}
