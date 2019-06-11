package upv.tfg.freeweather.data.interactors.interfaces;

import java.util.Map;

public interface I_FavouriteInteractor {
    Map<String, ?> getAllFavourites();
    void removeLocationFromFavourites(String location);
}
