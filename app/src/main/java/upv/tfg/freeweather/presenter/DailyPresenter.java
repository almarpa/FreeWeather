package upv.tfg.freeweather.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import upv.tfg.freeweather.adapters.DailyRecyclerViewAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_DailyRecyclerViewAdapter;
import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.presenter.interfaces.I_DailyPresenter;
import upv.tfg.freeweather.view.fragments.DailyFragment;
import upv.tfg.freeweather.view.activities.DailyInfoActivity;
import upv.tfg.freeweather.view.interfaces.I_DailyView;

public class DailyPresenter implements I_DailyPresenter {

    // View reference
    private I_DailyView view;
    // Adapter reference
    private I_DailyRecyclerViewAdapter adapter;

    private Context context;
    private DailyPrediction[] dp;

    public DailyPresenter(DailyFragment view, Context context) {
        this.view = view;
        this.context = context;
    }

    /**
     * Assigns the adapter to the presenter
     * @param adapter
     */
    @Override
    public void attachAdapter(DailyRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        this.adapter.onAttach(this );
    }

    @Override
    public void startPresenter(DailyPrediction[] dp) {
        this.dp = dp;
        adapter.setDays(dp[0].getDays());
        adapter.setImages(dp[0].getStateImages());
        adapter.setMaxTemperatures(dp[0].getMaxTemperatures());
        adapter.setMinTemperatures(dp[0].getMinTemperatures());

        fillBarChart();
        setXAxisBarchart();
        fillLineChart();
        setXAxisLineChart();

    }

    /**
     * Fill the graphic with info about precipitation probabilities
     */
    private void fillBarChart() {
        List<Integer> lPrecip = dp[0].getPrecipitations();

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            yValues.add(new BarEntry(i, lPrecip.get(i)));
        }

        BarDataSet set = new BarDataSet(yValues, "Chance of precipitation");


        BarData bar = new BarData(set);

        //Notify the view to display the data
        view.setBarchartData(bar);
    }

    /**
     * Set the bar labels of the x axis
     */
    private void setXAxisBarchart() {
        ArrayList<String> xAxisLabel = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            xAxisLabel.add(dp[0].getDayOfTheWeek(i));
        }
        //Notify the view to ser x axis information
        view.setBarchartXAxis(new IndexAxisValueFormatter(xAxisLabel));
    }

    /**
     * Fill the graphic with info about maximum and minimum precipitations
     */
    private void fillLineChart() {
        ArrayList<String> lMax = dp[0].getMaxTemperatures();
        ArrayList<String> lMin = dp[0].getMinTemperatures();

        ArrayList<Entry> lMaxTemps = new ArrayList<>();
        ArrayList<Entry> lMinTemps = new ArrayList<>();
        for(int i = 0; i < lMax.size(); i++){
            lMaxTemps.add(new Entry(i, Integer.parseInt(lMax.get(i).replace("º",""))));
            lMinTemps.add(new Entry(i, Integer.parseInt(lMin.get(i).replace("º",""))));
        }

        LineDataSet lineMaxT, lineMinT;
        lineMaxT = new LineDataSet(lMaxTemps,"Maximum temperatures");
        lineMaxT.setColor(Color.RED);
        lineMaxT.setCircleColor(Color.RED);
        lineMinT = new LineDataSet(lMinTemps, "Minimum temperatures");
        lineMinT.setColor(Color.BLUE);
        lineMinT.setCircleColor(Color.BLUE);

        LineData data = new LineData(lineMaxT, lineMinT);
        // Notify the view to display the data
        view.setLinechartData(data);
    }

    private void setXAxisLineChart() {
        ArrayList<String> xAxisLabel = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            xAxisLabel.add(dp[0].getDayOfTheWeek(i));
        }
        //Notify the view to ser x axis information
        view.setLinechartXAxis(new IndexAxisValueFormatter(xAxisLabel));
    }

    /**
     * Initiates a new window with detailed info for the day selected
     * @param position item adapter position
     */
    @Override
    public void onItemSelected(int position) {
        Intent intent = new Intent(context, DailyInfoActivity.class);
        intent.putExtra("DailyPrediction",dp);
        intent.putExtra("Item_Position",position);
        view.initiateActivity(intent);
    }
}
