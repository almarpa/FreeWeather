package upv.tfg.freeweather.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.FavouritesRecyclerViewAdapter;
import upv.tfg.freeweather.presenter.FavouritesPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritePresenter;
import upv.tfg.freeweather.view.interfaces.I_FavouritesView;

/**
 * This fragment shows the favourite locations selected in HomeFragment window
 */
public class FavouritesFragment extends Fragment implements I_FavouritesView {

    //Presenter reference
    private I_FavouritePresenter presenter;
    // Adapter reference
    private FavouritesRecyclerViewAdapter adapter;

    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private NavigationView navigationView;
    private TextView tvMsg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Initialize presenter
        presenter = new FavouritesPresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_favorites, container, false);
        navigationView = getActivity().findViewById(R.id.navView);
        tvMsg = view.findViewById(R.id.tvMsgNoFavourites);
        recyclerView = view.findViewById(R.id.favourites_recycler);

        setupRecyclerView();

        // Initialize adapter
        adapter = new FavouritesRecyclerViewAdapter(getContext(),this);
        recyclerView.setAdapter(adapter);

        // Associate the adapter to the presenter
        presenter.attachAdapter(adapter);

        presenter.startPresenter();

        return view;
    }

    /**
     * Initialize and position the adapter
     */
    private void setupRecyclerView() {
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * Replace the actual fragment
     * @param frameLayout  new layout
     * @param homeFragment main fragment that shows the prediction
     * @param tag fragment tag
     */
    @Override
    public void replaceFragment(int frameLayout, HomeFragment homeFragment, String tag) {
        getFragmentManager()
                .beginTransaction()
                .replace(frameLayout, homeFragment, tag)
                .commit();
    }

    /**
     * Change the selected menu option of the NavigationDrawer to 'Home' menu option
     */
    @Override
    public void setMenuSelection() {
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    /**
     * Shows a message about no favorite locations saved
     */
    @Override
    public void showMsgNoFavourites() {
        tvMsg.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the message about no favourite locations saved
     */
    @Override
    public void hideMsgNoFavourites() {
        tvMsg.setVisibility(View.GONE);
    }
}