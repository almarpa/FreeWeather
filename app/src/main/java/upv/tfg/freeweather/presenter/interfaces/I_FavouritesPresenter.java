package upv.tfg.freeweather.presenter.interfaces;

import android.content.Context;

import java.util.Map;

import upv.tfg.freeweather.model.FavouritesInteractor;

public interface I_FavouritesPresenter {
    Context getContext();
    void setModel(FavouritesInteractor model);
    void notifyGetAllFavorites();
}
