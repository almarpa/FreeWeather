package upv.tfg.freeweather.presenters;

import android.content.Context;
import android.graphics.Bitmap;

import upv.tfg.freeweather.data.interactors.MapsInteractor;
import upv.tfg.freeweather.data.interactors.interfaces.I_MapsInteractor;
import upv.tfg.freeweather.presenters.interfaces.I_MapsPresenter;
import upv.tfg.freeweather.views.interfaces.I_MapsView;

public class MapsPresenter implements I_MapsPresenter {

    // View reference
    private I_MapsView view;
    // Interactor reference
    private I_MapsInteractor interactor;
    private Context context;

    public MapsPresenter(I_MapsView view, Context context) {
        this.view = view;
        this.context = context;

        interactor = new MapsInteractor(this, context);
    }

    ///////////////////////////////////
    //    Called by the view         //
    ///////////////////////////////////
    /**
     * Obtain the suitable map from the model
     * @param position list item position
     */
    @Override
    public void notifySpinnerClicked(int position) {
        switch (position) {
            case 0:
                view.setMapImage2Invisible();
                view.closeProgressBar();
                view.clearData();
                break;
            case 1:
                view.initProgressBar();
                interactor.getPredictionMap();
                break;
            case 2:
                view.setMapImage2Invisible();
                view.initProgressBar();
                interactor.getRadarImage();
                break;
            case 3:
                view.setMapImage2Invisible();
                view.initProgressBar();
                interactor.getRayMap();
                break;
            case 4:
                view.setMapImage2Invisible();
                view.initProgressBar();
                interactor.getFireMap();
                break;
            case 5:
                view.setMapImage2Invisible();
                view.initProgressBar();
                interactor.getVegetationMap();
                break;
        }
    }

    ///////////////////////////////////
    //    Called by the model        //
    ///////////////////////////////////
    @Override
    public void setMapImage(Bitmap img) {
        view.setMapImage(img);
    }

    @Override
    public void setMapImage2(Bitmap img2) {
        view.setMapImage2(img2);
    }

    @Override
    public void closeProgressBar() {
        view.closeProgressBar();
    }

    @Override
    public void setTextDescription(String s) {
        view.setTextDescription(s);
    }
}
