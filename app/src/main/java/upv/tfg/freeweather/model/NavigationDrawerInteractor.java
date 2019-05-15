package upv.tfg.freeweather.model;

import android.content.Context;

import upv.tfg.freeweather.model.db.DatabaseHelper;
import upv.tfg.freeweather.model.db.PreferencesHelper;
import upv.tfg.freeweather.presenter.interfaces.I_NavigationDrawerPresenter;

public class NavigationDrawerInteractor implements upv.tfg.freeweather.model.interfaces.I_NavigationDrawerInteractor {

    //Presenter reference
    private I_NavigationDrawerPresenter presenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;

    public NavigationDrawerInteractor(I_NavigationDrawerPresenter presenter, Context context) {
        this.presenter = presenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
    }

    @Override
    public void isFirstTimeRunning() {
        boolean firstStart = prefHelper.getIsFirstTimeRunning();
        if (firstStart == true) {
            dbhelper.putLocationsInDB();
            prefHelper.saveDBCreation();
        }
    }
}
