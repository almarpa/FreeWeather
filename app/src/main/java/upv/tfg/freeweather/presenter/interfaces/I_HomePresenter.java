package upv.tfg.freeweather.presenter.interfaces;


import upv.tfg.freeweather.adapters.ViewPagerAdapter;

public interface I_HomePresenter {

    void attachAdapter(ViewPagerAdapter adapter);

    //METHODS ACCESSED BY THE VIEW
    void notifyFavButtonClicked(String location);
    void notifyIsItFavourite(String location);
    void notifySearchTextChanged(String text);
    void notifySearchPrediction(String location);
    void notifyGeolocate();
}
