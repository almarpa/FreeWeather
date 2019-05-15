package upv.tfg.freeweather.model.interfaces;

import java.util.Map;

public interface I_NotificationsInteractor {
    Map<String,?> getFavouriteLocation();
    String getCodeByLocation(String location);
}
