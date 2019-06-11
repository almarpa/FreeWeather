package upv.tfg.freeweather.presenter;

import android.content.Context;
import android.content.Intent;

import upv.tfg.freeweather.adapters.HourlyRecyclerViewAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_HourlyRecyclerViewAdapter;
import upv.tfg.freeweather.data.model.HourlyPrediction;
import upv.tfg.freeweather.presenter.interfaces.I_HourlyPresenter;
import upv.tfg.freeweather.view.activities.HourlyInfoActivity;
import upv.tfg.freeweather.view.interfaces.I_HourlyView;

public class HourlyPresenter implements I_HourlyPresenter {

    // View reference
    private I_HourlyView view;
    // Adapter reference
    private I_HourlyRecyclerViewAdapter adapter;

    private Context context;
    private HourlyPrediction[] hp;

    public HourlyPresenter(I_HourlyView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void attachAdapter(HourlyRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        this.adapter.onAttach(this);
    }

    @Override
    public void startPresenter(HourlyPrediction[] hp) {
        this.hp = hp;
        adapter.setDays(hp[0].getDays());
        adapter.setHours(hp[0].getHours());
        adapter.setImageUrls(hp[0].getImages());
        adapter.setTempImages(hp[0].getTempImages());
        adapter.setTemperatures(hp[0].getTemperatures());
    }

    @Override
    public void onItemSelected(int position) {
        Intent intent = new Intent(context, HourlyInfoActivity.class);
        intent.putExtra("HourlyPrediction",hp);
        intent.putExtra("Item_Position",position);
        view.initiateActivity(intent);
    }
}
