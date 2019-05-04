package upv.tfg.freeweather.presenter;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import upv.tfg.freeweather.model.HomeInteractor;
import upv.tfg.freeweather.model.entities.DailyPrediction;
import upv.tfg.freeweather.model.entities.HourlyPrediction;
import upv.tfg.freeweather.model.interfaces.I_HomeInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;
import upv.tfg.freeweather.tasks.TaskGetPredictions;
import upv.tfg.freeweather.view.interfaces.I_HomeView;

public class HomePresenter implements I_HomePresenter {

    // View reference
    private I_HomeView homeView;
    // Model reference
    private I_HomeInteractor homeInteractor;

    public HomePresenter(I_HomeView view) {
        homeView = view;
    }

    ////////////////////////////////////
    // AVAILABLE METHODS FOR THE VIEW //
    ////////////////////////////////////
    @Override
    public void setModel(HomeInteractor model) {
        homeInteractor = model;
    }
    @Override
    public void notifyFavButtonClicked(String location) {
        homeInteractor.notifyFavButtonClicked(location);
    }
    @Override
    public void notifyIsItFavourite(String location) {
        boolean isFavourite = homeInteractor.isItFavourite(location);
        if (isFavourite) {
            homeView.makeFavourite();
        }else{
            homeView.removeFavourite();
        }
    }
    @Override
    public void notifySearchTextChanged(String text) {
        //Obtain the list of possible locations suggested by the text introduced
        List<String> possibleLocations = homeInteractor.findPossibleLocation(text);
        List<String> suggestions = new ArrayList<>();

        if (possibleLocations != null) {
            for (int i = 0; i < possibleLocations.size(); i++) {
                suggestions.add(possibleLocations.get(i));
            }

            String[] columns = {BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1};
            MatrixCursor c = new MatrixCursor(columns);
            for (int i = 0; i < possibleLocations.size(); i++) {
                String[] tmp = {Integer.toString(i), suggestions.get(i)};
                c.addRow(tmp);
            }
            //Notify the view to show the suggestions
            homeView.displaySearchSuggestions(c);
        }
    }
    @Override
    public void notifySearchPrediction(String location) {
        String code = homeInteractor.getCodeByLocation(location);
        if (code != null) {
            homeView.setProgressBarVisible();
            //Obtain predictions from the API
            TaskGetPredictions task = new TaskGetPredictions(this);
            task.execute(code);
        } else {
            homeView.showMsgNoLocationFound(location);
        }
    }

    /////////////////////////////////////
    // AVAILABLE METHODS FOR THE MODEL //
    /////////////////////////////////////
    @Override
    public void makeFavourite() {
        homeView.makeFavourite();
    }
    @Override
    public void removeFavourite() {
        homeView.removeFavourite();
    }

    /////////////////////////////////////
    // METHODS CALLED FROM ASYNCTASK  //
    /////////////////////////////////////
    public void onTaskCompleted(HourlyPrediction[] hp, DailyPrediction[] dp){
        homeView.displayPredictions(hp,dp);
        homeView.setProgressBarInvisible();
    }
    public void onTaskError(){
        homeView.showMsgHTTPError();
    }


}
