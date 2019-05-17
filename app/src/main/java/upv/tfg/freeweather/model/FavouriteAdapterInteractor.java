package upv.tfg.freeweather.model;

import android.content.Context;

import upv.tfg.freeweather.model.db.DatabaseHelper;
import upv.tfg.freeweather.model.db.PreferencesHelper;
import upv.tfg.freeweather.model.interfaces.I_FavouriteAdapterInteractor;
import upv.tfg.freeweather.presenter.FavouriteAdapterPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_FavouriteAdapterPresenter;

public class FavouriteAdapterInteractor implements I_FavouriteAdapterInteractor {

    //Presenter reference
    private I_FavouriteAdapterPresenter presenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;
    private Context context;

    public FavouriteAdapterInteractor(FavouriteAdapterPresenter presenter, Context context) {
        this.presenter = presenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
        this.context = context;
    }
}
