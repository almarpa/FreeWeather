package upv.tfg.freeweather.views.interfaces;

import android.content.Context;
import android.database.Cursor;

public interface I_HomeView {
    Context getContext();

    // AVAILABLE METHODS FOR THE PRESENTER
    void closeSearchView();
    void displaySearchSuggestions(Cursor c);

    void setLocation(String newLocation);

    void makeFavourite();
    void removeFavourite();
    void setProgressBarVisible();
    void setProgressBarInvisible();
    void showAlertMsg(String string);
}
