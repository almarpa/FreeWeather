package upv.tfg.freeweather.presenters.interfaces;


import upv.tfg.freeweather.adapters.ViewPagerAdapter;
import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.data.model.HourlyPrediction;

public interface I_HomePresenter {

    void attachAdapter(ViewPagerAdapter adapter);

    //METHODS CALLED BY THE VIEW
    void notifyFavButtonClicked(String location);
    void notifyIsItFavourite(String location);
    void notifySearchTextChanged(String text);
    void notifySearchPrediction(String location);
    void notifyGeolocate();

    //METHODS CALLED BY THE MODEL
    void createFragments(HourlyPrediction[] hp, DailyPrediction[] dp);
    void showAlertMsg(String string);
    void closeSearchView();
    void setProgressBarInvisible();
}
