package upv.tfg.freeweather.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

import upv.tfg.freeweather.adapters.FavouriteAdapter;
import upv.tfg.freeweather.model.FavouritesInteractor;
import upv.tfg.freeweather.model.interfaces.I_FavouritesInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritesPresenter;
import upv.tfg.freeweather.view.interfaces.I_FavouritesView;

public class FavouritesPresenter implements I_FavouritesPresenter {

    // View reference
    private I_FavouritesView favView;
    // Model reference
    private I_FavouritesInteractor favInteractor;
    private FavouriteAdapter favAdapter;

    public FavouritesPresenter(I_FavouritesView view) {
        favView = view;
    }

    @Override
    public Context getContext() {
        return getContext();
    }

    @Override
    public void setModel(FavouritesInteractor model) {
        favInteractor = model;
    }

    @Override
    public void notifyGetAllFavorites() {

        Map<String, ?> map =  favInteractor.getAllFavourites();

        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            //Check if the location is saved as favourite
            if(entry.getValue().toString() == "true"){
                list.add(entry.getKey());
            }
        }

        //Instantiate adapter
        favAdapter = new FavouriteAdapter(list, favView.getContext());
        favView.setAdapter(favAdapter);
    }
}
