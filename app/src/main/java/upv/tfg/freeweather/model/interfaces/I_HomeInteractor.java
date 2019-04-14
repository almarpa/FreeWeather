package upv.tfg.freeweather.model.interfaces;


import java.util.Map;

import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;

public interface I_HomeInteractor {
    void notifyFavButtonClicked(String location);
    boolean isItFavourite(String location);
    Integer getCodeByLocation(String location);
    String getLocationByName(String fav_item_to_search);
    void findPossibleLocation(String text);
    void onAttachPresenter(I_HomePresenter presenter);
    void onDetachPresenter();
}
