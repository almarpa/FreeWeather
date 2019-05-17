package upv.tfg.freeweather.adapters.interfaces;

import java.util.ArrayList;

import upv.tfg.freeweather.presenter.interfaces.I_FavouritePresenter;

public interface I_FavouritesAdapter {
    void onAttach(I_FavouritePresenter presenter);
    void onDetach();
    void setFavourites(ArrayList<String> list);
}
