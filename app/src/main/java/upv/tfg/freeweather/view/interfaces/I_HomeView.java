package upv.tfg.freeweather.view.interfaces;

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
    void showMsgNoLocationFound(String location);
    void showMsgHTTPError();
    void showMsgLocationEmpty();
}
