package upv.tfg.freeweather.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.ViewPagerAdapter;
import upv.tfg.freeweather.model.HomeInteractor;
import upv.tfg.freeweather.model.entities.DailyPrediction;
import upv.tfg.freeweather.presenter.HomePresenter;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;
import upv.tfg.freeweather.model.entities.HourlyPrediction;
import upv.tfg.freeweather.serializations.Init;
import upv.tfg.freeweather.serializations.predictions.PD;
import upv.tfg.freeweather.serializations.predictions.PH;
import upv.tfg.freeweather.view.interfaces.I_HomeView;
import upv.tfg.freeweather.view.interfaces.ShowPredictions;

/**
 * Main window allows the view of daily and hourly predictions.
 */
public class HomeFragment extends Fragment implements I_HomeView, View.OnClickListener {

    //Hourly prediction and diarly prediction
    private PH hp;
    private PD dp;

    //Presenter reference
    I_HomePresenter homePresenter;
    private ShowPredictions sp;

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ImageButton ivFavourite;
    private TextView tvLocation;
    private TextView tvDate;


    public HomeFragment() {
        //HTTPConnection hc = new HTTPConnection();
        //hc.execute(code);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ivFavourite = view.findViewById(R.id.imageButton);
        ivFavourite.setOnClickListener(this);
        tvLocation = view.findViewById(R.id.tvLocalidad);
        tvDate = view.findViewById(R.id.tvFechayHora);

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        tvDate.setText(date);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        inflater.inflate(R.menu.menu_notifications, menu);
        inflater.inflate(R.menu.menu_configuration, menu);

        //Search option in toolbar
        MenuItem searchItem = menu.findItem(R.id.mSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {
                //Recover the location code with the location name and obtain the prediction
                Integer code = homePresenter.notifyGetCode(location);
                if (code != null) {
                    HTTPConnection hc = new HTTPConnection();
                    hc.execute(code);
                } else {
                    //Shows a warning msg
                    Toast.makeText(getContext(), "No se encuentra la localidad introducida", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                homePresenter.notifySearchTextChanged(text);
                return false;
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
                //Presenter notifies the interactor who decides if add or delete the favourite
                homePresenter.notifyFavButtonClicked(location);
                break;
        }
    }

    private void displayPredictions(HourlyPrediction[] hp, DailyPrediction[] dp) {
        tabLayout = view.findViewById(R.id.tlPredicciones);
        viewPager = view.findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getFragmentManager());

        Bundle bundle = new Bundle();

        //Tab TodayFragment
        TodayFragment tFragment = new TodayFragment();
        bundle.putSerializable("TODAY", hp);
        tFragment.setArguments(bundle);

        //Tab HourlyFragment
        HourlyFragment hFragment = new HourlyFragment();
        bundle.putSerializable("HOURLY", hp);
        tFragment.setArguments(bundle);

        //Tab DialyFragment
        DailyFragment dFragment = new DailyFragment();
        bundle.putSerializable("DAILY", dp);
        tFragment.setArguments(bundle);

        adapter.addFragment(tFragment, "Hoy");
        adapter.addFragment(hFragment, "Horaria");
        adapter.addFragment(dFragment, "Diaria");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        TextView location = getView().findViewById(R.id.tvLocalidad);
        location.setText(hp[0].getNombre());

        //Check if the location is already favourite or not
        if (homePresenter.notifyIsItFavourite(location.getText().toString())) {
            ivFavourite.setBackgroundResource(R.drawable.favourite_item_pressed);
        } else {
            ivFavourite.setBackgroundResource(R.drawable.favourite_item_default);
        }
    }


    /////////////////////////////////
    ////////   MVP METHODS   ////////
    /////////////////////////////////
    @Override
    public void makeFavourite() {
        ivFavourite.setBackgroundResource(R.drawable.favourite_item_pressed);
    }

    @Override
    public void removeFavourite() {
        ivFavourite.setBackgroundResource(R.drawable.favourite_item_default);
    }


    /////////////////////////////////
    ////////  API CONNECTION    /////
    ////////  HOURLY and DAILY  /////
    ////////  PREDICTION        /////
    /////////////////////////////////
    public class HTTPConnection extends AsyncTask<Integer, Void, Void> {

        private Init init;
        private HourlyPrediction[] hp;
        private DailyPrediction[] dp;

        private URL url;
        private HttpURLConnection connection;
        private InputStreamReader reader;
        private GsonBuilder builder;
        private Gson gson;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                //  HOURLY PREDICTION   //
                //1º HTTP REQUEST
                url = new URL("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/horaria/" + params[0] + "?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                init = gson.fromJson(reader, Init.class);

                connection.disconnect();

                //2º HTTP REQUEST
                url = new URL(init.datos);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                hp = gson.fromJson(reader, HourlyPrediction[].class);

                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                //DAILY PREDICTION
            /*
            try {
                //Primera Peticion GET
                url = new URL("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" + params[0] + "?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                init = gson.fromJson(reader, Init.class);

                connection.disconnect();

                //Segunda Peticion GET
                url = new URL(init.datos);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                dp = gson.fromJson(reader, DailyPrediction[].class);

                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            displayPredictions(hp,dp);
        }
    }
}
