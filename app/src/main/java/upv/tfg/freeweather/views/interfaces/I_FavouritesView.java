package upv.tfg.freeweather.views.interfaces;

import android.content.Context;

import upv.tfg.freeweather.views.fragments.HomeFragment;

public interface I_FavouritesView {

    Context getContext();
    void replaceFragment(int frameLayout, HomeFragment homeFragment, String home);
    void setMenuSelection();
    void showMsgNoFavourites();
    void hideMsgNoFavourites();
}
