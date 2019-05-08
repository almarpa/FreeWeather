package upv.tfg.freeweather.presenter.interfaces;

import upv.tfg.freeweather.model.NavigationDrawerInteractor;

public interface I_NavigationDrawerPresenter {

    void setModel(NavigationDrawerInteractor model);
    void notifyFillDatabase();
}
