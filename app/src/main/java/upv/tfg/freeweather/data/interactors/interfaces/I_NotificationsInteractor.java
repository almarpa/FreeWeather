package upv.tfg.freeweather.data.interactors.interfaces;

import java.util.Map;

public interface I_NotificationsInteractor {
    Map<String,?> getFavouriteLocations();

    ////////////////////////////////
    ///       DATABASE          ////
    ////////////////////////////////
    String getCodeByLocation(String location);

    ////////////////////////////////
    ///       PREFERENCES       ////
    ////////////////////////////////
    void saveCurrentNotification(String text);
    void saveSwitchState(int checked);
    void saveTimeOptionChoosed(String time);
    void saveLocationChoosed(String location);
    String getCurrenteNotification();
    int getLastSwitch();
    String getTimeSelected();
    String getLocationSelected();

    ////////////////////////////////
    ///       ASYNCTASKS        ////
    ////////////////////////////////
    void getPredictions(String code);
}
