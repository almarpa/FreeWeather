package upv.tfg.freeweather.presenter.interfaces;

import upv.tfg.freeweather.adapters.HourlyRecyclerViewAdapter;
import upv.tfg.freeweather.data.model.HourlyPrediction;

public interface I_HourlyPresenter {
    void attachAdapter(HourlyRecyclerViewAdapter adapter);
    void startPresenter(HourlyPrediction[] hp);
    void onLocationSelected(int position);
}
