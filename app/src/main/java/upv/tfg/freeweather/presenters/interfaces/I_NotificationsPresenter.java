package upv.tfg.freeweather.presenters.interfaces;


import upv.tfg.freeweather.data.model.DailyPrediction;

public interface I_NotificationsPresenter {

    // Available methods for the view
    void notifySwitchChecked();
    void notifySwitchUnchecked();

    // Available methods for the model
    void setNotificationData(DailyPrediction[] dp);
    void showNoFavouriteExists();
    void showHTTPMsgError();
}
