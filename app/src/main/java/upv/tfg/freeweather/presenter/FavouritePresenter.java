package upv.tfg.freeweather.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

import upv.tfg.freeweather.adapters.FavouriteAdapter;
import upv.tfg.freeweather.model.FavouriteInteractor;
import upv.tfg.freeweather.model.interfaces.I_FavouriteInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritePresenter;
import upv.tfg.freeweather.view.interfaces.I_FavouritesView;

public class FavouritePresenter implements I_FavouritePresenter {

    // View reference
    private I_FavouritesView view;
    // Model reference
    private I_FavouriteInteractor interactor;

    private Context context;
    private FavouriteAdapter adapter;

    public FavouritePresenter(I_FavouritesView view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new FavouriteInteractor(this, context);
    }

    @Override
    public void notifyGetAllFavorites() {

        Map<String, ?> map =  interactor.getAllFavourites();

        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            //Check if the location is saved as favourite
            if(entry.getValue().toString() == "true"){
                list.add(entry.getKey());
            }
        }

        //Instantiate adapter
        adapter = new FavouriteAdapter(list, view.getContext());
        view.setAdapter(adapter);
    }
}
