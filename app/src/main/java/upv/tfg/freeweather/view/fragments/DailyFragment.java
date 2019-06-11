package upv.tfg.freeweather.view.fragments;

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.DailyRecyclerViewAdapter;
import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.presenter.DailyPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_DailyPresenter;
import upv.tfg.freeweather.view.interfaces.I_DailyView;


public class DailyFragment extends Fragment implements I_DailyView {

    //Presenter reference
    private I_DailyPresenter presenter;
    // Adapter reference
    private DailyRecyclerViewAdapter adapter;

    private View view;
    private Context context;

    private DailyPrediction[] dp;

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private BarChart barChart;
    private LineChart lineChart;

    public DailyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize presenter
        presenter = new DailyPresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_tab_daily, container, false);
        context = view.getContext();

        recyclerView = view.findViewById(R.id.rvDaily);
        barChart = view.findViewById(R.id.barchart);
        lineChart = view.findViewById(R.id.linechart);

        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);

        lineChart.getDescription().setEnabled(false);
        lineChart.setScaleEnabled(false);

        setupRecyclerAdapter();

        // Initialize adapter
        adapter = new DailyRecyclerViewAdapter(context,this);
        recyclerView.setAdapter(adapter);

        // Associate the adapter to the presenter
        presenter.attachAdapter(adapter);

        // Bundle args (Getting hourly prediction)
        dp = (DailyPrediction[]) getArguments().getSerializable("DAILY");

        presenter.startPresenter(dp);

        return view;
    }

    private void setupRecyclerAdapter() {
        manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void initiateActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void setBarchartData(BarData data){
        barChart.setData(data);
        barChart.invalidate();
    }

    @Override
    public void setBarchartXAxis(IndexAxisValueFormatter formatter) {
        barChart.getXAxis().setValueFormatter(formatter);
        barChart.invalidate();
    }

    @Override
    public void setLinechartData(LineData data) {
        lineChart.setData(data);
        lineChart.invalidate();
    }

    @Override
    public void setLinechartXAxis(IndexAxisValueFormatter formatter) {
        lineChart.getXAxis().setValueFormatter(formatter);
        lineChart.invalidate();
    }
}
