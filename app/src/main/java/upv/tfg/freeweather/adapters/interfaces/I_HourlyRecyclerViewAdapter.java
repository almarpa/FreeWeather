package upv.tfg.freeweather.adapters.interfaces;

import java.util.ArrayList;

import upv.tfg.freeweather.presenter.interfaces.I_HourlyPresenter;

public interface I_HourlyRecyclerViewAdapter {
    void onAttach(I_HourlyPresenter presenter);
    void onDetach();

    void setDays(ArrayList<String> days);
    void setHours(ArrayList<String> hours);
    void setImageUrls(ArrayList<Integer> urls);
    void setTempImages(ArrayList<Integer> tempImages);
    void setTemperatures(ArrayList<String> temperatures);
}
