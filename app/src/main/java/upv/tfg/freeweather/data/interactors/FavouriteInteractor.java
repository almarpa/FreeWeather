package upv.tfg.freeweather.data.interactors;

import android.content.Context;

import java.util.Map;

import upv.tfg.freeweather.data.local.DatabaseHelper;
import upv.tfg.freeweather.data.local.PreferencesHelper;
import upv.tfg.freeweather.data.interactors.interfaces.I_FavouriteInteractor;
import upv.tfg.freeweather.presenters.interfaces.I_FavouritePresenter;

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

    @Override
    public void removeLocationFromFavourites(String location) {
        prefHelper.deleteFavourite(location);
    }
}
