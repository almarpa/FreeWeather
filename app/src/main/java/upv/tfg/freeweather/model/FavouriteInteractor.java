package upv.tfg.freeweather.model;

import android.content.Context;

import java.util.Map;

import upv.tfg.freeweather.model.db.DatabaseHelper;
import upv.tfg.freeweather.model.db.PreferencesHelper;
import upv.tfg.freeweather.model.interfaces.I_FavouriteInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritePresenter;

public class FavouriteInteractor implements I_FavouriteInteractor {

    //Presenter reference
    private I_FavouritePresenter presenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;

    private Context context;

    public FavouriteInteractor(I_FavouritePresenter presenter, Context context) {
        this.presenter = presenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
        this.context = context;
    }
    @Override
    public Map<String, ?> getAllFavourites() {
        return prefHelper.getAllFavourites();
    }
}
