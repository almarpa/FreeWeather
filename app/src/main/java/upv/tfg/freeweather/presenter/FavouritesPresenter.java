package upv.tfg.freeweather.presenter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Map;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.FavouritesAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_FavouritesAdapter;
import upv.tfg.freeweather.data.FavouriteInteractor;
import upv.tfg.freeweather.data.interfaces.I_FavouriteInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritePresenter;
import upv.tfg.freeweather.view.HomeFragment;
import upv.tfg.freeweather.view.interfaces.I_FavouritesView;

public class FavouritesPresenter implements I_FavouritePresenter {

    // View reference
    private I_FavouritesView view;
    // Model reference
    private I_FavouriteInteractor interactor;
    // Adapter reference
    private I_FavouritesAdapter adapter;

    private Context context;
    private HomeFragment homeFragment;

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
    public void attachAdapter(FavouritesAdapter adapter) {
        this.adapter = adapter;
        this.adapter.onAttach(this );
    }

    /**
     * This method obtain favourite locations if exist and notifies the view
     */
    @Override
    public void startPresenter() {
        Map<String, ?> map =  interactor.getAllFavourites();

        ArrayList<String> list = new ArrayList<String>();
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
     * @param adapter actual adapter with all favorite locations
     * @param v actual view
     * @param position the position of the chosen location at the list
     */
    @Override
    public void onLocationSelected(AdapterView<?> adapter, View v, int position) {
        String place = adapter.getItemAtPosition(position).toString();

        // Change the selected menu option to 'Home' option
        view.setMenuSelection();

        //Create a new HomeFragment with the location to search as a parameter
        homeFragment = new HomeFragment().newInstance(place);
        view.replaceFragment(R.id.frameLayout, homeFragment, "home");
    }

    /**
     * Remove a location from preferences
     * @param location place to delete
     */
    @Override
    public void deleteLocationFromFavourites(String location){
        interactor.removeLocationFromFavourites(location);
    }
}
