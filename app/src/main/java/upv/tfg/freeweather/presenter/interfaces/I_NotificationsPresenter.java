package upv.tfg.freeweather.presenter.interfaces;


import upv.tfg.freeweather.model.NotificationsInteractor;

public interface I_NotificationsPresenter {
    void setModel(NotificationsInteractor model);
    void notifyGetFavoriteLocations();
}
