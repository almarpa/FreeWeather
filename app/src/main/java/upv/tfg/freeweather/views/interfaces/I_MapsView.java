package upv.tfg.freeweather.views.interfaces;

import android.graphics.Bitmap;

public interface I_MapsView {
    void setMapImage(Bitmap img);
    void setMapImage2(Bitmap img);
    void setTextDescription(String description);
    void initProgressBar();

    void setMapImage2Invisible();
    void closeProgressBar();
    void clearData();

}
