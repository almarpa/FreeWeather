package upv.tfg.freeweather.data;


import android.content.Context;

import java.util.List;

import upv.tfg.freeweather.data.local.DatabaseHelper;
import upv.tfg.freeweather.data.local.PreferencesHelper;
import upv.tfg.freeweather.data.interfaces.I_HomeInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;

public class HomeInteractor implements I_HomeInteractor {

    //Presenter reference
    private I_HomePresenter presenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;

    private Context context;

    public HomeInteractor(I_HomePresenter presenter, Context context) {
        this.presenter = presenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
        this.context = context;
    }

    //////////////////////////////
    //        PREFERENCES       //
    //////////////////////////////
    @Override
    public boolean notifyFavButtonClicked(String location) {
        return prefHelper.getIsItFavourite(location);
    }

    @Override
    public void addFavouriteinPreferences(String location){
        prefHelper.addFavourite(location);
    }

    @Override
    public void removeFavouritefromPreferences(String location){
        prefHelper.deleteFavourite(location);
    }

    @Override
    public boolean isItFavourite(String location) {
        return prefHelper.getIsItFavourite(location);
    }

    //////////////////////////////
    //           MODEL          //
    //////////////////////////////
    @Override
    public String getCodeByLocation(String location) {
        return dbhelper.getCodeByLocation(location);
    }

    @Override
    public List<String> findPossibleLocation(String text) {
        return dbhelper.findPossibleLocation(text);
    }
}
