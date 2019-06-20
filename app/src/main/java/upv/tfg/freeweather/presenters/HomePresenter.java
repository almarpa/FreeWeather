package upv.tfg.freeweather.presenters;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.MatrixCursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.ViewPagerAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_ViewPagerAdapter;
import upv.tfg.freeweather.data.interactors.HomeInteractor;
import upv.tfg.freeweather.data.interactors.interfaces.I_HomeInteractor;
import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.data.model.HourlyPrediction;
import upv.tfg.freeweather.presenters.interfaces.I_HomePresenter;
import upv.tfg.freeweather.views.fragments.DailyFragment;
import upv.tfg.freeweather.views.fragments.HourlyFragment;
import upv.tfg.freeweather.views.fragments.TodayFragment;
import upv.tfg.freeweather.views.interfaces.I_HomeView;

public class HomePresenter implements I_HomePresenter {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // View reference
    private I_HomeView view;
    // Model reference
    private I_HomeInteractor interactor;
    // Adapter reference
    private I_ViewPagerAdapter adapter;

    private Context context;
    private LocationManager mLocationManager;

    public HomePresenter(I_HomeView view, Context context) {
        this.view = view;
        this.context = context;

        // Creating the interactor that will interact with the database
        interactor = new HomeInteractor(this, context);
    }

    /**
     * Assigns the adapter to the presenter
     * @param adapter
     */
    @Override
    public void attachAdapter(ViewPagerAdapter adapter) {
        this.adapter = adapter;
        this.adapter.onAttach(this );
    }

    //////////////////////////////////////
    ///     CALLED BY THE VIEW      //////
    //////////////////////////////////////
    /**
     * Method called by the view to make or remove a favourite from the favourite list
     * @param location
     */
    @Override
    public void notifyFavButtonClicked(String location) {
        if(!location.equals("")){
            boolean res = interactor.notifyFavButtonClicked(location);
            if (res) {
                interactor.removeFavouritefromPreferences(location);
                view.removeFavourite();
            } else {
                interactor.addFavouriteinPreferences(location);
                view.makeFavourite();
            }
        }else{
            view.showAlertMsg(context.getString(R.string.msgLocationEmpty));
        }
    }

    /**
     * Method called by the view to ask the model if the location gave as parameter is favourite or not
     * @param location
     */
    @Override
    public void notifyIsItFavourite(String location) {
        boolean isFavourite = interactor.isItFavourite(location);
        if (isFavourite) {
            view.makeFavourite();
        }else{
            view.removeFavourite();
        }
    }

    /**
     * Method called by the view to show a list of suggested locations
     * @param text
     */
    @Override
    public void notifySearchTextChanged(String text) {
        if(text.length() > 1) {
            //Obtain the list of possible locations suggested by the text introduced
            List<String> possibleLocations = interactor.findPossibleLocation(text);
            List<String> suggestions = new ArrayList<>();

            if (possibleLocations != null) {
                for (int i = 0; i < possibleLocations.size(); i++) {
                    suggestions.add(possibleLocations.get(i));
                }

                String[] columns = {BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1};
                MatrixCursor c = new MatrixCursor(columns);
                for (int i = 0; i < possibleLocations.size(); i++) {
                    String[] tmp = {Integer.toString(i), suggestions.get(i)};
                    c.addRow(tmp);
                }
                //Notify the view to show the suggestions
                view.displaySearchSuggestions(c);
            }
        }
    }

    /**
     * Method called by the view to search a prediction
     * @param location
     */
    @Override
    public void notifySearchPrediction(String location) {
        getPredictions(location);
    }

    /**
     * Method called by the view to search the prediction for current coordinates
     */
    @Override
    public void notifyGeolocate() {
        //Check if location permission is granted
        if(checkLocationPermission()){
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    view.showAlertMsg(context.getString(R.string.msgPermissionsRequired));
                } else {
                    Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    List<Address> adress = new ArrayList<>();
                    try{
                        adress = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    } catch (Exception e){}
                    if (adress.size() > 0) {
                        getPredictions(adress.get(0).getLocality());
                    }
                }
            }
        }
    }

    /**
     * Check if Location permission is granted
     * @return true or false
     */
    public boolean checkLocationPermission() {
        final Activity activity = (Activity) context;

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

            return false;
        } else {
            return true;
        }
    }

    /**
     * Obtain hourly and daily prediction from the API
     * @param location location to get predictions
     */
    private void getPredictions(String location){
        String code = interactor.getCodeByLocation(location);
        //Check if location exists in the database
        if (code != null) {
            //Check internet connection
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isConnected){
                view.setProgressBarVisible();
                //Obtain predictions from the API
                interactor.getPredictions(code);
                //AsyncTaskGetPredictions task = new AsyncTaskGetPredictions();
                //task.execute(code);
            }else{
                view.showAlertMsg(context.getString(R.string.msgNoInternetConnection));
            }
        } else {
            view.showAlertMsg(context.getString(R.string.msgNoFound) + " " + location);
        }
    }

    //////////////////////////////////////
    ////     CALLED BY THE MODEL     /////
    //////////////////////////////////////
    /**
     * Create the viewpager fragments with their appropriate bundle(Serialized prediction Object)
     */
    @Override
    public void createFragments(HourlyPrediction[] hp, DailyPrediction[] dp) {
        adapter.clearFragments();

        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();

        //Tab TodayFragment
        TodayFragment tFragment = new TodayFragment();
        bundle1.putSerializable("TODAY", dp);
        tFragment.setArguments(bundle1);

        //Tab HourlyFragment
        HourlyFragment hFragment = new HourlyFragment();
        bundle2.putSerializable("HOURLY", hp);
        hFragment.setArguments(bundle2);

        //Tab DialyFragment
        DailyFragment dFragment = new DailyFragment();
        bundle3.putSerializable("DAILY", dp);
        dFragment.setArguments(bundle3);

        adapter.addFragment(tFragment, context.getString(R.string.tab_text_1));
        adapter.addFragment(hFragment, context.getString(R.string.tab_text_2));
        adapter.addFragment(dFragment, context.getString(R.string.tab_text_3));

        view.setLocation(dp[0].getNombre());

        //Ask if this location is favourite or not
        notifyIsItFavourite(dp[0].getNombre());
    }

    /**
     * Notifies the view to show an alert msg
     * @param msg
     */
    @Override
    public void showAlertMsg(String msg) {
        view.showAlertMsg(msg);
    }

    /**
     * Notifies the view to close the search view
     */
    @Override
    public void closeSearchView() {
        view.closeSearchView();
    }

    /**
     * Notifies the view to stop the progress bar
     */
    @Override
    public void setProgressBarInvisible() {
        view.setProgressBarInvisible();
    }

}
