package upv.tfg.freeweather.presenters;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.FavouritesRecyclerViewAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_FavouritesRecyclerViewAdapter;
import upv.tfg.freeweather.data.interactors.FavouriteInteractor;
import upv.tfg.freeweather.data.interactors.interfaces.I_FavouriteInteractor;
import upv.tfg.freeweather.presenters.interfaces.I_FavouritePresenter;
import upv.tfg.freeweather.views.fragments.HomeFragment;
import upv.tfg.freeweather.views.interfaces.I_FavouritesView;

public class FavouritesPresenter implements I_FavouritePresenter {

    // View reference
    private I_FavouritesView view;
    // Model reference
    private I_FavouriteInteractor interactor;
    // Adapter reference
    private I_FavouritesRecyclerViewAdapter adapter;

    private Context context;

    public FavouritesPresenter(I_FavouritesView view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new FavouriteInteractor(this, context);
    }

    /**
     * Assigns the adapter to the presenter
     * @param adapter
     */
    @Override
    public void attachAdapter(FavouritesRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        this.adapter.onAttach(this );
    }

    /**
     * This method obtain favourite locations if exist and notifies the view
     */
    @Override
    public void startPresenter() {
        Map<String, ?> map =  interactor.getAllFavourites();

        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            //Check if the location is saved as favourite
            if(entry.getValue().toString() == "true"){
                list.add(entry.getKey());
            }
        }
        if(list.size() > 0) {
            adapter.setFavourites(list);
            view.hideMsgNoFavourites();
        }else {
            view.showMsgNoFavourites();
        }
    }

    /**
     * Open a new HomeFragment with the appropriate predictions
     * @param location place
     */
    @Override
    public void onLocationPressed(String location) {
        // Change the selected menu option to 'Home' option
        view.setMenuSelection();

        //Create a new HomeFragment with location to get predictions
        HomeFragment fragment = new HomeFragment().newInstance(location);
        view.replaceFragment(R.id.frameLayout, fragment, "home");
    }

    /**
     * Remove a location from preferences
     * @param location place to delete
     */
    @Override
    public void onLocationDeleted(String location){
        interactor.removeLocationFromFavourites(location);
    }
}
