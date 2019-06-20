package upv.tfg.freeweather.presenters.interfaces;

import android.graphics.Bitmap;

public interface I_MapsPresenter {
    //Called by the view
    void notifySpinnerClicked(int position);

    //Called by the model
    void setMapImage(Bitmap img);
    void setMapImage2(Bitmap img2);
    void closeProgressBar();
    void setTextDescription(String s);
}
