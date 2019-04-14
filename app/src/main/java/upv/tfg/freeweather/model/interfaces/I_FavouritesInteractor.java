package upv.tfg.freeweather.model.interfaces;

import java.util.Map;

public interface I_FavouritesInteractor {
    Map<String, ?> getAllFavourites();
    void goFromFavPrediction(String location);
}
