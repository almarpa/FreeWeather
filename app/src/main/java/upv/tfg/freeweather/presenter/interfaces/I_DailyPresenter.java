package upv.tfg.freeweather.presenter.interfaces;

import upv.tfg.freeweather.adapters.DailyRecyclerViewAdapter;
import upv.tfg.freeweather.data.model.DailyPrediction;

public interface I_DailyPresenter {
    void attachAdapter(DailyRecyclerViewAdapter adapter);
    void startPresenter(DailyPrediction[] dp);
    void onItemSelected(int position);
}
