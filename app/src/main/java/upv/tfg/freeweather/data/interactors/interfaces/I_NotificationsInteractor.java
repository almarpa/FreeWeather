package upv.tfg.freeweather.data.interactors.interfaces;

import java.util.Map;

public interface I_NotificationsInteractor {
    Map<String,?> getFavouriteLocations();
    String getCodeByLocation(String location);

    void saveCurrentNotification(String text);
    void saveSwitchState(int checked);
    void saveTimeOptionChoosed(String time);
    void saveLocationChoosed(String location);
    String getCurrenteNotification();
    int getLastSwitch();
    String getTimeSelected();
    String getLocationSelected();
}
