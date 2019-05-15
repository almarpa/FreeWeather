package upv.tfg.freeweather.view.interfaces;

import android.widget.RadioGroup;


public interface I_NotificationsActivity {
    void setIntervalTimesView(RadioGroup rg);
    void setLocationsView(RadioGroup rg);
    void showHTTPMsgError();
}
