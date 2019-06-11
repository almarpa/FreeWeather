package upv.tfg.freeweather.data.interactors;

import android.content.Context;

import java.util.Map;

import upv.tfg.freeweather.data.local.DatabaseHelper;
import upv.tfg.freeweather.data.local.PreferencesHelper;
import upv.tfg.freeweather.data.interactors.interfaces.I_NotificationsInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;

public class NotificationsInteractor implements I_NotificationsInteractor {

    //Presenter reference
    private I_NotificationsPresenter presenter;
    //Database helper reference
    private DatabaseHelper dbhelper;
    //Preferences helper reference
    private PreferencesHelper prefHelper;


    public NotificationsInteractor(I_NotificationsPresenter presenter, Context context) {
        this.presenter = presenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
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
    public void saveCurrentNotification(String text) {
        prefHelper.saveCurrentNotification(text);
    }

    @Override
    public void saveSwitchState(int checked) {
        prefHelper.saveSwicthState(checked);
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
    public String getCurrenteNotification() {
        return prefHelper.getCurrentNotification();
    }

    @Override
    public int getLastSwitch() {
        return prefHelper.getLastSwitch();
    }

    @Override
    public String getTimeSelected() {
        return prefHelper.getIntervalTimeChoosed();
    }

    @Override
    public String getLocationSelected() {
        return prefHelper.getLocationChoosed();
    }


}
