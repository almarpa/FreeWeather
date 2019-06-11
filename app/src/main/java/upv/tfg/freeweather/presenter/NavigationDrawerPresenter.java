package upv.tfg.freeweather.presenter;

import android.content.Context;

import upv.tfg.freeweather.data.interactors.NavigationDrawerInteractor;
import upv.tfg.freeweather.data.interactors.interfaces.I_NavigationDrawerInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_NavigationDrawerPresenter;
import upv.tfg.freeweather.view.interfaces.I_NavigationDrawerView;

public class NavigationDrawerPresenter implements I_NavigationDrawerPresenter {

    // View reference
    private I_NavigationDrawerView view;
    // Model reference
    private I_NavigationDrawerInteractor interactor;

    private Context context;

    public NavigationDrawerPresenter(I_NavigationDrawerView view, Context context) {
        this.view = view;
        this.context = context;

        // Creating the interactor that will interact with the database
        interactor = new NavigationDrawerInteractor(this, context);

        // Fill the database with locations
        interactor.isFirstTimeRunning();
    }
}
