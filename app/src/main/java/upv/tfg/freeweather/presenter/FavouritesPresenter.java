package upv.tfg.freeweather.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

import upv.tfg.freeweather.adapters.FavouritesAdapter;
import upv.tfg.freeweather.model.FavouritesInteractor;
import upv.tfg.freeweather.model.interfaces.I_FavouritesInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritesPresenter;
import upv.tfg.freeweather.view.interfaces.I_FavouritesView;

public class FavouritesPresenter implements I_FavouritesPresenter {

    // View reference
    private I_FavouritesView view;
    // Model reference
    private I_FavouritesInteractor interactor;

    private Context context;
    private FavouritesAdapter adapter;

    public FavouritesPresenter(I_FavouritesView view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new FavouritesInteractor(this, context);
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
        adapter = new FavouritesAdapter(list, view.getContext());
        view.setAdapter(adapter);
    }
}
