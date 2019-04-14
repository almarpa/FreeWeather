package upv.tfg.freeweather.model;

import android.content.Context;

import java.util.Map;

import upv.tfg.freeweather.model.db.DatabaseHelper;
import upv.tfg.freeweather.model.db.PreferencesHelper;
import upv.tfg.freeweather.model.interfaces.I_FavouritesInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritesPresenter;

public class FavouritesInteractor implements I_FavouritesInteractor {

    //Presenter reference
    private I_FavouritesPresenter favPresenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;

    private Context context;

    public FavouritesInteractor(I_FavouritesPresenter favPresenter, Context context) {
        this.favPresenter = favPresenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
        this.context = context;
    }
    @Override
    public Map<String, ?> getAllFavourites() {
        return prefHelper.getAllPreferences();
    }

    @Override
    public void goFromFavPrediction(String location) {
        prefHelper.addFavItemToSearch(location);
    }
}
