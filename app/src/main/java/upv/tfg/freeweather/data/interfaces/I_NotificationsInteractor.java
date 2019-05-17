package upv.tfg.freeweather.data.interfaces;

import java.util.Map;

public interface I_NotificationsInteractor {
    Map<String,?> getFavouriteLocations();
    String getCodeByLocation(String location);
    void saveTimeOptionChoosed(String time);
    void saveLocationChoosed(String location);
    boolean getLastSwitch();
    String getTimeChoosed();
    String getLocationChoosed();
}
