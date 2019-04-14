package upv.tfg.freeweather.presenter;

import android.content.Context;

import java.util.Map;

import upv.tfg.freeweather.model.FavouritesInteractor;
import upv.tfg.freeweather.model.interfaces.I_FavouritesInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritesPresenter;
import upv.tfg.freeweather.view.interfaces.I_FavouritesView;

public class FavouritesPresenter implements I_FavouritesPresenter {

    // View reference
    private I_FavouritesView favView;
    // Model reference
    private I_FavouritesInteractor favInteractor;

    public FavouritesPresenter(I_FavouritesView view) {
        favView = view;
    }

    @Override
    public Context getContext() {
        return getContext();
    }

    @Override
    public void setModel(FavouritesInteractor model) {
        favInteractor = model;
    }

    @Override
    public Map<String, ?> notifyGetAllFavorites() {
        return favInteractor.getAllFavourites();
    }

    @Override
    public void notifyGoFromFavPrediction(String location) {
        favInteractor.goFromFavPrediction(location);
    }
}
