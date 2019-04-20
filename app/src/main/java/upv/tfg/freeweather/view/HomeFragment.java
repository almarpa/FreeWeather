package upv.tfg.freeweather.view;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.ViewPagerAdapter;
import upv.tfg.freeweather.model.HomeInteractor;
import upv.tfg.freeweather.model.entities.DailyPrediction;
import upv.tfg.freeweather.presenter.HomePresenter;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;
import upv.tfg.freeweather.model.entities.HourlyPrediction;
import upv.tfg.freeweather.view.interfaces.I_HomeView;

/**
 * Main window allows the view of daily and hourly predictions.
 */
public class HomeFragment extends Fragment implements I_HomeView, View.OnClickListener {

    private View view;
    private Context context;

    //Presenter reference
    I_HomePresenter homePresenter;

    //Adapters
    private ViewPagerAdapter adapter;
    private CursorAdapter suggestionAdapter;
    //Widgets
    private TabLayout tabLayout;
    private SearchView searchView;
    private ViewPager viewPager;
    private ImageButton ivFavourite;
    private TextView tvLocation;
    private TextView tvDate;
    private TextView location;

    public HomeFragment() { }

    public static HomeFragment newInstance(String location){
        HomeFragment hFragment = new HomeFragment();

        // Get arguments passed in, if any
        Bundle args = hFragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        // Add parameters to the argument bundle
        args.putString("FAVOURITE_ITEM", location);
        hFragment.setArguments(args);

        return hFragment;
    }

    /**
     * Setup Model View Presenter pattern
     */
    private void setupMVP() {
        // Create the Presenter
        I_HomePresenter presenter = new HomePresenter(this);
        // Create the Model
        HomeInteractor model = new HomeInteractor(presenter, getContext());
        // Set presenter model
        presenter.setModel(model);
        // Set the Presenter as a interface
        homePresenter = presenter;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setupMVP();

        //Check if it comes from FavouritesFragment and in that case, search the fav. location
        Bundle args = getArguments();
        if (args != null) {
            String code = args.getString("FAVOURITE_ITEM");
            homePresenter.notifySearchPrediction(code);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

        ivFavourite = view.findViewById(R.id.imageButton);
        ivFavourite.setOnClickListener(this);
        tvLocation = view.findViewById(R.id.tvLocalidad);
        tvDate = view.findViewById(R.id.tvFechayHora);

        tvDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        inflater.inflate(R.menu.menu_notifications, menu);
        inflater.inflate(R.menu.menu_configuration, menu);

        //Search View initialization
        MenuItem searchItem = menu.findItem(R.id.mSearch);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Busque una localidad...");

        //Suggestion adapter initialization
        suggestionAdapter = new SimpleCursorAdapter(getContext(),
                R.layout.layout_suggested_item, null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{R.id.tvSuggestion}, 0);
        searchView.setSuggestionsAdapter(suggestionAdapter);

        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {
                homePresenter.notifySearchPrediction(location);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                homePresenter.notifySearchTextChanged(text);
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
                Cursor cursor = adapter.getCursor();
                if (cursor != null) {
                    String location = cursor.getString(1);
                    homePresenter.notifySearchPrediction(location);
                }
                return true;
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String location = tvLocation.getText().toString();
        switch (v.getId()) {
            case R.id.imageButton:
                //Notifies the presenter that button fav has been pressed
                homePresenter.notifyFavButtonClicked(location);
                break;
        }
    }

    ///////////////////////////////////////////////////////
    ////////  AVAILABLE METHODS FOR THE PRESENTER  ////////
    ///////////////////////////////////////////////////////
    @Override
    public void displayPredictions(HourlyPrediction[] hp, DailyPrediction[] dp) {
        tabLayout = view.findViewById(R.id.tlPredicciones);
        viewPager = view.findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getFragmentManager());

        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();

        //Tab TodayFragment
        TodayFragment tFragment = new TodayFragment();
        bundle1.putSerializable("TODAY", dp);
        tFragment.setArguments(bundle1);

        //Tab HourlyFragment
        HourlyFragment hFragment = new HourlyFragment();
        bundle2.putSerializable("HOURLY", hp);
        hFragment.setArguments(bundle2);

        //Tab DialyFragment
        DailyFragment dFragment = new DailyFragment();
        bundle3.putSerializable("DAILY", dp);
        dFragment.setArguments(bundle3);

        adapter.addFragment(tFragment, "Ahora");
        adapter.addFragment(hFragment, "Horaria");
        adapter.addFragment(dFragment, "Diaria");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        location = getView().findViewById(R.id.tvLocalidad);
        location.setText(dp[0].getNombre());

        //Ask the presenter if the location is already favourite or not
        homePresenter.notifyIsItFavourite(location.getText().toString());
    }

    @Override
    public void displaySearchSuggestions(Cursor c) {
        suggestionAdapter.swapCursor(c);
    }

    @Override
    public void makeFavourite() {
        ivFavourite.setBackgroundResource(R.drawable.favourite_item_pressed);
    }

    @Override
    public void removeFavourite() {
        ivFavourite.setBackgroundResource(R.drawable.favourite_item_default);
    }

    @Override
    public void showMsgNoLocation(String location) {
        Toast.makeText(context, "No hay información acerca de la localidad: "+ location,Toast.LENGTH_SHORT);
    }
}
