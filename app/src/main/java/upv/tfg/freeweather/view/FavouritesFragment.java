package upv.tfg.freeweather.view;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.FavouriteAdapter;
import upv.tfg.freeweather.model.FavouritesInteractor;
import upv.tfg.freeweather.presenter.FavouritesPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritesPresenter;
import upv.tfg.freeweather.view.interfaces.I_FavouritesView;

/**
 * This fragment shows the favourite locations selected in HomeFragment window
 */
public class FavouritesFragment extends Fragment implements I_FavouritesView {

    //Presenter reference
    I_FavouritesPresenter favPresenter;
    //Fragment home view
    HomeFragment homeFragment;

    View view;
    ListView lView;

    /**
     * Setup Model View Presenter pattern
     */
    private void setupMVP() {
        // Create the Presenter
        I_FavouritesPresenter presenter = new FavouritesPresenter(this);
        // Create the Model
        FavouritesInteractor model = new FavouritesInteractor(presenter,getContext());
        // Set presenter model
        presenter.setModel(model);
        // Set the Presenter as a interface
        favPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setupMVP();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_favorites, container, false);
        lView = view.findViewById(R.id.favourites_listview);

        //Notifies the presenter to get all favourite locations saved
        favPresenter.notifyGetAllFavorites();

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String location = lView.getAdapter().getItem(position).toString();

                NavigationView navigationView = getActivity().findViewById(R.id.navView);
                navigationView.getMenu().getItem(0).setChecked(true);

                //Create a new HomeFragment with the location to search as a parameter
                homeFragment = new HomeFragment().newInstance(location);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, homeFragment, "home")
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorites, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mFavorites) {
            Toast.makeText(getContext(), R.string.menu_fragment_favoritos, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setAdapter(FavouriteAdapter favAdapter) {
        //Assign adapter
        lView.setAdapter(favAdapter);
    }
}