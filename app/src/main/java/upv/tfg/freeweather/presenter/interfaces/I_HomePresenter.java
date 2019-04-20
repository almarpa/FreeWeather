package upv.tfg.freeweather.presenter.interfaces;

import android.content.Context;

import java.util.List;

import upv.tfg.freeweather.model.HomeInteractor;

public interface I_HomePresenter {
    Context getContext();
    void setModel(HomeInteractor model);

    //METHODS ACCESSED BY THE VIEW
    void notifyFavButtonClicked(String location);
    void notifyIsItFavourite(String location);
    void notifySearchTextChanged(String text);
    void notifySearchPrediction(String location);

    //METHODS ACCESSED BY THE MODEL
    void makeFavourite();
    void removeFavourite();
}
