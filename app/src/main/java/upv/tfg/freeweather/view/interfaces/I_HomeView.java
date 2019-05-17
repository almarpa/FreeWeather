package upv.tfg.freeweather.view.interfaces;

import android.content.Context;
import android.database.Cursor;

import upv.tfg.freeweather.data.model.DailyPrediction;
import upv.tfg.freeweather.data.model.HourlyPrediction;

public interface I_HomeView {
    Context getContext();

    // AVAILABLE METHODS FOR THE PRESENTER
    void displayPredictions(HourlyPrediction[] hp, DailyPrediction[] dp);
    void displaySearchSuggestions(Cursor c);
    void makeFavourite();
    void removeFavourite();
    void setProgressBarVisible();
    void setProgressBarInvisible();
    void showMsgNoLocationFound(String location);
    void showMsgHTTPError();
    void showMsgLocationEmpty();
}
