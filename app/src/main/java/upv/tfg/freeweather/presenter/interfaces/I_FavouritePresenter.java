package upv.tfg.freeweather.presenter.interfaces;


import android.view.View;
import android.widget.AdapterView;

import upv.tfg.freeweather.adapters.FavouritesAdapter;

public interface I_FavouritePresenter {

    void attachAdapter(FavouritesAdapter adapter);
    void startPresenter();
    void onLocationSelected(AdapterView<?> adapter, View v, int position);
    void deleteLocationFromFavourites(String location);
}
