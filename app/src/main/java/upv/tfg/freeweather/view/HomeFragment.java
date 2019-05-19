package upv.tfg.freeweather.view;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.ViewPagerAdapter;
import upv.tfg.freeweather.presenter.HomePresenter;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;
import upv.tfg.freeweather.view.interfaces.I_HomeView;

/**
 * Main window allows the view of daily and hourly predictions.
 */
public class HomeFragment extends Fragment implements I_HomeView {

    //Presenter reference
    private I_HomePresenter presenter;

    private ViewPagerAdapter adapter;
    private CursorAdapter suggestionAdapter;
    private TabLayout tabLayout;
    private SearchView searchView;
    private ViewPager viewPager;
    private ImageButton ivFavourite;
    private TextView tvLocation;
    private TextView tvDate;
    private ProgressBar progressBar;

    public HomeFragment() { }

    public HomeFragment newInstance(String location){
        HomeFragment home = new HomeFragment();

        // Get arguments passed in, if any
        Bundle args = home.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        // Add parameters to the argument bundle
        args.putString("FAVOURITE_ITEM", location);
        home.setArguments(args);

        return home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Create the presenter
        presenter = new HomePresenter(this, getContext());

        //Check if it has Bundle args and in that case, search the fav. location
        Bundle args = getArguments();
        if (args != null) {
            String favLocation = args.getString("FAVOURITE_ITEM");
            presenter.notifySearchPrediction(favLocation);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Initialize view elements
        tvLocation = view.findViewById(R.id.tvLocalidad);
        tvDate = view.findViewById(R.id.tvFechayHora);
        ivFavourite = view.findViewById(R.id.imageButton);
        progressBar = view.findViewById(R.id.progressBar);
        tabLayout = view.findViewById(R.id.tlPredicciones);
        viewPager = view.findViewById(R.id.viewpager_id);

        tvDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        adapter = new ViewPagerAdapter(getChildFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Associate the adapter to the presenter
        presenter.attachAdapter(adapter);

        ivFavourite.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.notifyFavButtonClicked(tvLocation.getText().toString());
            }
        }));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        inflater.inflate(R.menu.menu_notifications, menu);
        inflater.inflate(R.menu.menu_configuration, menu);

        //Search View initialization
        final MenuItem searchItem = menu.findItem(R.id.mSearch);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Find a location...");

        //Suggestion adapter initialization
        suggestionAdapter = new SimpleCursorAdapter(getContext(),
                R.layout.layout_suggested_item, null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{R.id.tvSuggestion}, 0);

        searchView.setSuggestionsAdapter(suggestionAdapter);
        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {
                presenter.notifySearchPrediction(location);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                presenter.notifySearchTextChanged(text);
                return true;
            }
        }));
        searchView.setOnSuggestionListener((new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }
            @Override
            public boolean onSuggestionClick(int position) {
                CursorAdapter adapter = searchView.getSuggestionsAdapter();
                presenter.notifySearchPrediction(adapter.getCursor().getString(1));
                return true;
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.mNotifications:
                i = new Intent(getContext(), NotificationsActivity.class);
                startActivity(i);
                return true;
            case R.id.mConfiguration:
                //i = new Intent(getContext(), ConfigurationActivity.class);
                //startActivity(i);
                //return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displaySearchSuggestions(Cursor locations) {
        suggestionAdapter.swapCursor(locations);
    }

    @Override
    public void closeSearchView() {
        // close the keyboard on load
        searchView.clearFocus();
        searchView.onActionViewCollapsed();
    }
    @Override
    public void setLocation(String newLocation){
        tvLocation.setText(newLocation);
    }

    @Override
    public void makeFavourite() {
        ivFavourite.setBackgroundResource(R.drawable.favourite_icon);
    }
    @Override
    public void removeFavourite() {
        ivFavourite.setBackgroundResource(R.drawable.not_favourite_icon);
    }
    @Override
    public void setProgressBarVisible() {
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void setProgressBarInvisible() {
        if(progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void showMsgNoLocationFound(String location) {
        Toast.makeText(getContext(), "No hay información acerca de la localidad: "+ location,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showMsgHTTPError() {
        Toast.makeText(getContext(), "No se pudo obtener la predicción debido a un error",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showMsgLocationEmpty() {
        Toast.makeText(getContext(),"Please, search a location before",Toast.LENGTH_SHORT).show();
    }
}
