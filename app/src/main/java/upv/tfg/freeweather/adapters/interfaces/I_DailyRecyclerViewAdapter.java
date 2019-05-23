package upv.tfg.freeweather.adapters.interfaces;

import java.util.ArrayList;

import upv.tfg.freeweather.presenter.interfaces.I_DailyPresenter;

public interface I_DailyRecyclerViewAdapter {
    void onAttach(I_DailyPresenter presenter);
    void onDetach();

    void setDays(ArrayList<String> days);

    void setImages(ArrayList<Integer> urls);

    void setMaxTemperatures(ArrayList<String> maxTemps);

    void setMinTemperatures(ArrayList<String> minTemps);
}
