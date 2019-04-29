package upv.tfg.freeweather.model.interfaces;


import android.database.Cursor;

import java.util.List;
import java.util.Map;

import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;

public interface I_HomeInteractor {
    void notifyFavButtonClicked(String location);
    boolean isItFavourite(String location);
    String getCodeByLocation(String location);
    String getLocationByName(String fav_item_to_search);
    List<String> findPossibleLocation(String text);
    void onAttachPresenter(I_HomePresenter presenter);
    void onDetachPresenter();
}
