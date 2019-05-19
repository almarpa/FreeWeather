package upv.tfg.freeweather.data;

import android.content.Context;

import java.util.Map;

import upv.tfg.freeweather.data.local.DatabaseHelper;
import upv.tfg.freeweather.data.local.PreferencesHelper;
import upv.tfg.freeweather.data.interfaces.I_NotificationsInteractor;
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
