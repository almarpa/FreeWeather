package upv.tfg.freeweather.presenter;

import android.content.Context;

import upv.tfg.freeweather.adapters.interfaces.I_FavouriteAdapter;
import upv.tfg.freeweather.model.FavouriteAdapterInteractor;
import upv.tfg.freeweather.model.interfaces.I_FavouriteAdapterInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_FavouriteAdapterPresenter;

public class FavouriteAdapterPresenter implements I_FavouriteAdapterPresenter {

    // View reference
    private I_FavouriteAdapter view;
    // Model reference
    private I_FavouriteAdapterInteractor interactor;

    private Context context;

    public FavouriteAdapterPresenter(I_FavouriteAdapter view, Context context) {
        this.view = view;
        this.context = context;

        interactor = new FavouriteAdapterInteractor(this, context);

    }
}
