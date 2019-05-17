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

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.FavouriteAdapter;
import upv.tfg.freeweather.presenter.FavouritePresenter;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritePresenter;
import upv.tfg.freeweather.view.interfaces.I_FavouritesView;

/**
 * This fragment shows the favourite locations selected in HomeFragment window
 */
public class FavouritesFragment extends Fragment implements I_FavouritesView {

    //Presenter reference
    private I_FavouritePresenter presenter;
    //Fragment home view
    private HomeFragment homeFragment;

    private View view;
    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        presenter = new FavouritePresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_favorites, container, false);
        listView = view.findViewById(R.id.favourites_listview);

        //Notifies the presenter to get all favourite locations saved
        presenter.notifyGetAllFavorites();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String location = listView.getAdapter().getItem(position).toString();

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
        //inflater.inflate(R.menu.menu_favorites, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setAdapter(FavouriteAdapter favAdapter) {
        listView.setAdapter(favAdapter);
    }
}