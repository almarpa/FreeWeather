package upv.tfg.freeweather.views.interfaces;

import android.widget.RadioGroup;
import android.widget.SpinnerAdapter;


public interface I_NotificationsView {
    void setTimeView(RadioGroup rg);
    void setLocationsView(SpinnerAdapter adapter);

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
