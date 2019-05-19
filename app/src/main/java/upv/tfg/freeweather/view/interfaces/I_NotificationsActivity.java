package upv.tfg.freeweather.view.interfaces;

import android.widget.RadioGroup;


public interface I_NotificationsActivity {
    void setIntervalTimesView(RadioGroup rg);
    void setLocationsView(RadioGroup rg);
    void doSwitch();
    void clearSwitch();
    void setCurrentNotification(String text);
    void clearCurrentNotification();
    void showHTTPMsgError();
    void showNoTimeChecked();
    void showNoLocationChecked();
    void showNoFavouriteExists();
    void showAlarmConfigurated(String alarmConfigurated);
}