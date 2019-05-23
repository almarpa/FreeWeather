package upv.tfg.freeweather.presenter;

import android.content.Context;
import android.content.Intent;

import upv.tfg.freeweather.adapters.DailyRecyclerViewAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_DailyRecyclerViewAdapter;
import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.presenter.interfaces.I_DailyPresenter;
import upv.tfg.freeweather.view.DailyFragment;
import upv.tfg.freeweather.view.DailyInfoActivity;
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
    }

    @Override
    public void onItemSelected(int position) {
        Intent intent = new Intent(context, DailyInfoActivity.class);
        intent.putExtra("DailyPrediction",dp);
        intent.putExtra("Item_Position",position);
        view.initiateActivity(intent);
    }
}
