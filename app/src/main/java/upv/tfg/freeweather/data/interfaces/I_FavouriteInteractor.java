package upv.tfg.freeweather.data.interfaces;

import java.util.Map;

public interface I_FavouriteInteractor {
    Map<String, ?> getAllFavourites();
    void removeLocationFromFavourites(String location);
}
