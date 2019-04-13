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
import upv.tfg.freeweather.presenter.HomePresenter;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;
import upv.tfg.freeweather.model.HourlyPrediction;
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

    private ShowPredictions sp;

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ImageButton b;
    private TextView tv;

    //Presenter reference
    I_HomePresenter homePresenter;

    /**
     * Setup Model View Presenter pattern
     */
    private void setupMVP() {
        // Create the Presenter
        I_HomePresenter presenter = new HomePresenter(this);
        // Create the Model
        HomeInteractor model = new HomeInteractor(presenter,getContext());
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

        tabLayout = (TabLayout) view.findViewById(R.id.tlPredicciones);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getFragmentManager());

        adapter.addFragment(new TodayFragment(),"Hoy");
        adapter.addFragment(new HourlyFragment(),"Horaria");
        adapter.addFragment(new DailyFragment(),"Diaria");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        b =  view.findViewById(R.id.imageButton);
        b.setOnClickListener(this);
        tv = view.findViewById(R.id.tvLocalidad);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        inflater.inflate(R.menu.menu_notifications, menu);
        inflater.inflate(R.menu.menu_configuration,menu);

        //Search option in toolbar
        MenuItem searchItem = menu.findItem(R.id.mSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {
                //Recover the location code by the location name and obtain the prediction
                Integer code = homePresenter.notifyGetCode(location);
                HTTPConnection hc = new HTTPConnection();
                hc.execute(code);

                //Check if the location is already favourite or not
                if(homePresenter.notifyIsItFavourite(location)){
                    b.setBackgroundResource(R.drawable.favourite_item_pressed);
                }else{
                    b.setBackgroundResource(R.drawable.favourite_item_default);
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
        String location = tv.getText().toString();
        switch (v.getId()) {
            case R.id.imageButton:
                //El presentador notifica al interactor que es quien decide si añade o elimina en favoritos
                homePresenter.notifyFavButtonClicked(location);
                break;
        }
    }

    private void displayData(HourlyPrediction[] sp) {

        TextView location =  getView().findViewById(R.id.tvLocalidad);
        TextView date = getView().findViewById(R.id.tvFechayHora);

        String dat = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        date.setText(dat);
        location.setText(sp[0].getNombre());
    }

    /////////////////////////////////
    ////////   MVP METHODS   ////////
    /////////////////////////////////
    @Override
    public void makeFavourite() {
        b.setBackgroundResource(R.drawable.favourite_item_pressed);
    }
    @Override
    public void removeFavourite() {
        b.setBackgroundResource(R.drawable.favourite_item_default);
    }

    /////////////////////////////////
    ////////   CONEXION API  ////////
    /////////////////////////////////
    public class HTTPConnection extends AsyncTask<Integer, Void, Void> {

        private Init init;
        private HourlyPrediction[] sp;

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
                //Primera Peticion GET
                url = new URL("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/horaria/"+params[0]+"?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4X21hcmNvN0BvdXRsb29rLmVzIiwianRpIjoiM2YxYmQyZDAtYTdjNy00MjNhLTljMDktYWFiMmQ4OTdlN2RmIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1NDU3Nzk0NTIsInVzZXJJZCI6IjNmMWJkMmQwLWE3YzctNDIzYS05YzA5LWFhYjJkODk3ZTdkZiIsInJvbGUiOiIifQ.rf0HtYhn5FEGYUhZn_y2wnel8GrpuPKuQj2JZ35GG7Q");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(),StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                init = gson.fromJson(reader, Init.class);

                connection.disconnect();

                //Segunda Peticion GET
                url = new URL(init.datos);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset=ISO_8859_1");
                connection.setDoInput(true);
                connection.connect();

                reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.ISO_8859_1);
                builder = new GsonBuilder();
                gson = builder.create();
                sp = gson.fromJson(reader, HourlyPrediction[].class);

                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            displayData(sp);
        }
    }
}
