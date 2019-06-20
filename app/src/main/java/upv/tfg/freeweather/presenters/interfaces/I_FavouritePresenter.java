package upv.tfg.freeweather.presenters.interfaces;


import upv.tfg.freeweather.adapters.FavouritesRecyclerViewAdapter;

public interface I_FavouritePresenter {

    void attachAdapter(FavouritesRecyclerViewAdapter adapter);
    void startPresenter();
    void onLocationPressed(String location);
    void onLocationDeleted(String location);
}
