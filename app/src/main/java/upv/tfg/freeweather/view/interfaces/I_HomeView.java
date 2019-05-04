package upv.tfg.freeweather.view.interfaces;

import android.content.Context;
import android.database.Cursor;

import upv.tfg.freeweather.model.entities.DailyPrediction;
import upv.tfg.freeweather.model.entities.HourlyPrediction;

public interface I_HomeView {
    Context getContext();

    void displayPredictions(HourlyPrediction[] hp, DailyPrediction[] dp);
    void displaySearchSuggestions(Cursor c);

    void makeFavourite();
    void removeFavourite();

    void setProgressBarVisible();
    void setProgressBarInvisible();

    void showMsgNoLocationFound(String location);
    void showMsgHTTPError();
}
