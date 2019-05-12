package upv.tfg.freeweather.view.interfaces;

import android.content.Context;

import upv.tfg.freeweather.adapters.FavouriteItemAdapter;

public interface I_FavouritesView {

    Context getContext();
    void setAdapter(FavouriteItemAdapter favAdapter);
}
