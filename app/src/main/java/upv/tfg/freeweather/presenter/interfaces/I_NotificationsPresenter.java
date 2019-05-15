package upv.tfg.freeweather.presenter.interfaces;


import android.widget.RadioGroup;

public interface I_NotificationsPresenter {

    // Available methods for the view
    void notifySwitchChecked();
    void notifySwitchUnchecked();
    void notifyRdBtnTimeChanged(RadioGroup rg, int checkedId);
    void notifyRdBtnLocationChanged(RadioGroup rg, int checkedId);
}
