package upv.tfg.freeweather.presenter;

import android.content.Context;
import upv.tfg.freeweather.model.HomeInteractor;
import upv.tfg.freeweather.model.interfaces.I_HomeInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;
import upv.tfg.freeweather.view.interfaces.I_HomeView;

public class HomePresenter implements I_HomePresenter {

    // View reference
    private I_HomeView homeView;
    // Model reference
    private I_HomeInteractor homeInteractor;

    public HomePresenter(I_HomeView view) {
        homeView = view;
    }

    @Override
    public Context getContext() {
        return getContext();
    }
    @Override
    public void setModel(HomeInteractor model) {
        homeInteractor = model;
    }

    //////////////////////////////
    //    AFFECTS TO THE MODEL  //
    //////////////////////////////
    @Override
    public void notifyFavButtonClicked(String location) {
        homeInteractor.notifyFavButtonClicked(location);
    }
    /**
     * Check if is this a favorite location
     * @param location
     */
    @Override
    public boolean notifyIsItFavourite(String location) {
        return homeInteractor.isItFavourite(location);
    }
    @Override
    public void notifySearchTextChanged(String text) {
        homeInteractor.findPossibleLocation(text);
    }
    @Override
    public Integer notifyGetCode(String location) {
        return homeInteractor.getCodeByLocation(location);
    }

    @Override
    public String notifyGetLocation(String fav_item_to_search) {
        return homeInteractor.getLocationByName(fav_item_to_search);
    }

    //////////////////////////////
    //    AFFECTS TO THE VIEW   //
    //////////////////////////////
    @Override
    public void makeFavourite() {
        homeView.makeFavourite();
    }
    @Override
    public void removeFavourite() {
        homeView.removeFavourite();
    }


}
