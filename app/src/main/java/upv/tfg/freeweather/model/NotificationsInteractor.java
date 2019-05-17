package upv.tfg.freeweather.model;

import android.content.Context;

import java.util.Map;

import upv.tfg.freeweather.model.db.DatabaseHelper;
import upv.tfg.freeweather.model.db.PreferencesHelper;
import upv.tfg.freeweather.model.interfaces.I_NotificationsInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;

public class NotificationsInteractor implements I_NotificationsInteractor {

    //Presenter reference
    private I_NotificationsPresenter notifPresenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;

    private Context context;


    public NotificationsInteractor(I_NotificationsPresenter notifPresenter, Context context) {
        this.notifPresenter = notifPresenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
        this.context = context;
    }

    @Override
    public Map<String,?> getFavouriteLocations() {
        return prefHelper.getAllFavourites();
    }

    @Override
    public String getCodeByLocation(String location) {
        return dbhelper.getCodeByLocation(location);
    }

    @Override
    public void saveTimeOptionChoosed(String time) {
        prefHelper.saveTimeOptionChoosed(time);
    }

    @Override
    public void saveLocationChoosed(String location) {
        prefHelper.saveLocationChoosed(location);
    }

    @Override
    public boolean getLastSwitch() {
        return prefHelper.getLastSwitch();
    }

    @Override
    public String getTimeChoosed() {
        return prefHelper.getIntervalTimeChoosed();
    }

    @Override
    public String getLocationChoosed() {
        return prefHelper.getLocationChoosed();
    }


}
