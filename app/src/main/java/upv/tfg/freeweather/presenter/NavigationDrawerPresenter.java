package upv.tfg.freeweather.presenter;

import upv.tfg.freeweather.model.NavigationDrawerInteractor;
import upv.tfg.freeweather.model.interfaces.I_NavigationDrawerInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_NavigationDrawerPresenter;
import upv.tfg.freeweather.view.interfaces.I_NavigationDrawerView;

public class NavigationDrawerPresenter implements I_NavigationDrawerPresenter {

    // View reference
    private I_NavigationDrawerView view;
    // Model reference
    private I_NavigationDrawerInteractor interactor;

    public NavigationDrawerPresenter(I_NavigationDrawerView view) {
        this.view = view;
    }

    ////////////////////////////////////
    // AVAILABLE METHODS FOR THE VIEW //
    ////////////////////////////////////
    @Override
    public void setModel(NavigationDrawerInteractor model) {
        interactor = model;
    }

    @Override
    public void notifyFillDatabase() {
        interactor.isFirstTimeRunning();
    }
}
