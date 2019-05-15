package upv.tfg.freeweather.model.interfaces;


import android.database.Cursor;

import java.util.List;
import java.util.Map;

import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;

public interface I_HomeInteractor {
    boolean notifyFavButtonClicked(String location);
    void addFavouriteinPreferences(String location);
    void removeFavouritefromPreferences(String location);
    boolean isItFavourite(String location);
    String getCodeByLocation(String location);
    List<String> findPossibleLocation(String text);
}
