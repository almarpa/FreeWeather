package upv.tfg.freeweather.data.interactors.interfaces;


import java.util.List;

public interface I_HomeInteractor {
    //Called by presenter
    boolean notifyFavButtonClicked(String location);
    void addFavouriteinPreferences(String location);
    void removeFavouritefromPreferences(String location);
    boolean isItFavourite(String location);
    String getCodeByLocation(String location);
    List<String> findPossibleLocation(String text);
    void getPredictions(String code);
}
