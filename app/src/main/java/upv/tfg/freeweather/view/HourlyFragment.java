package upv.tfg.freeweather.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.HourlyRecyclerViewAdapter;
import upv.tfg.freeweather.data.model.HourlyPrediction;
import upv.tfg.freeweather.presenter.HourlyPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_HourlyPresenter;
import upv.tfg.freeweather.view.interfaces.I_HourlyView;

public class HourlyFragment extends Fragment implements I_HourlyView {

    //Presenter reference
    private I_HourlyPresenter presenter;
    // Adapter reference
    private HourlyRecyclerViewAdapter adapter;

    private View view;
    private Context context;

    private HourlyPrediction[] hp;

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;

    public HourlyFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize presenter
        presenter = new HourlyPresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tab_hourly, container, false);
        context = view.getContext();

        recyclerView = view.findViewById(R.id.rvHourly);

        setupRecyclerAdapter();

        // Initialize adapter
        adapter = new HourlyRecyclerViewAdapter(context,this);
        recyclerView.setAdapter(adapter);

        // Associate the adapter to the presenter
        presenter.attachAdapter(adapter);

        // Bundle args (Getting hourly prediction)
        hp = (HourlyPrediction[]) getArguments().getSerializable("HOURLY");

        presenter.startPresenter(hp);

        return view;
    }

    /**
     * Initialize and position the adapter
     */
    private void setupRecyclerAdapter() {
        manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * Starts a new activity
     * @param intent new intent
     */
    @Override
    public void initiateActivity(Intent intent){
        startActivity(intent);
    }
}
