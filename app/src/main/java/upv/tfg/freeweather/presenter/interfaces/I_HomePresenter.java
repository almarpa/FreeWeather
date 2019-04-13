package upv.tfg.freeweather.presenter.interfaces;

import android.content.Context;

import java.util.ConcurrentModificationException;

import upv.tfg.freeweather.model.HomeInteractor;

public interface I_HomePresenter {
    Context getContext();
    void setModel(HomeInteractor model);

    //VIEW NOTIFICATIONS THAT AFFECT TO THE MODEL
    void notifyFavButtonClicked(String location);
    boolean notifyIsItFavourite(String location);
    void notifySearchTextChanged(String text);
    Integer notifyGetCode(String location);

    //MODEL NOTIFICATIONS THAT AFFECT TO THE VIEW
    void makeFavourite();
    void removeFavourite();

}
