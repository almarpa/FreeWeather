package upv.tfg.freeweather.view.interfaces;

import android.content.Context;

import upv.tfg.freeweather.view.fragments.HomeFragment;

public interface I_FavouritesView {

    Context getContext();
    void replaceFragment(int frameLayout, HomeFragment homeFragment, String home);
    void setMenuSelection();
    void showMsgNoFavourites();
    void hideMsgNoFavourites();
}
