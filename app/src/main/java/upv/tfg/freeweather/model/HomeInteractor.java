package upv.tfg.freeweather.model;


import android.content.Context;

import java.util.List;

import upv.tfg.freeweather.model.db.DatabaseHelper;
import upv.tfg.freeweather.model.db.PreferencesHelper;
import upv.tfg.freeweather.model.interfaces.I_HomeInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;

public class HomeInteractor implements I_HomeInteractor {

    //Presenter reference
    private I_HomePresenter homePresenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;

    private Context context;

    public HomeInteractor(I_HomePresenter homePresenter, Context context) {
        this.homePresenter = homePresenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
        this.context = context;
    }

    //////////////////////////////
    //        PREFERENCES       //
    //////////////////////////////
    @Override
    public void notifyFavButtonClicked(String location) {
        if (prefHelper.isItFavourite(location)) {
            prefHelper.deleteFavourite(location);
            homePresenter.removeFavourite();
        } else {
            prefHelper.addFavourite(location);
            homePresenter.makeFavourite();
        }
    }

    @Override
    public boolean isItFavourite(String location) {
        return prefHelper.isItFavourite(location);
    }


    //////////////////////////////
    //           MODEL          //
    //////////////////////////////
    @Override
    public String getCodeByLocation(String location) {
        return dbhelper.getCodeByLocation(location);
    }

    @Override
    public String getLocationByName(String fav_item_to_search) {
        return prefHelper.getLocationByName(fav_item_to_search);
    }

    @Override
    public List<String> findPossibleLocation(String text) {
        return dbhelper.findPossibleLocation(text);
    }
}
