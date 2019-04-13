package upv.tfg.freeweather.model;


import android.content.Context;
import android.widget.Toast;

import java.util.Map;

import upv.tfg.freeweather.model.db.DatabaseHelper;
import upv.tfg.freeweather.model.db.PreferencesHelper;
import upv.tfg.freeweather.model.interfaces.I_HomeInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;

public class HomeInteractor implements I_HomeInteractor {

    private I_HomePresenter homePresenter;
    private DatabaseHelper dbhelper;
    private PreferencesHelper prefHelper;

    private Context context;

    public HomeInteractor(I_HomePresenter homePresenter, Context context) {
        this.homePresenter = homePresenter;
        dbhelper = new DatabaseHelper(context);
        prefHelper = new PreferencesHelper(context);
        this.context = context;
    }

    //////////////////////////////
    //        PREFERENCES       //
    //////////////////////////////
    @Override
    public void notifyFavButtonClicked(String location) {
        if(prefHelper.isItFavourite(location)){
            prefHelper.deleteFavourite(location);
            homePresenter.removeFavourite();
        }else{
            prefHelper.addFavourite(location);
            homePresenter.makeFavourite();
        }
    }

    @Override
    public Map<String, ?> getAllFavourites() {
        return prefHelper.getAllPreferences();
    }

    @Override
    public boolean isItFavourite(String location) {
        return prefHelper.isItFavourite(location);
    }


    //////////////////////////////
    //           MODEL          //
    //////////////////////////////
    @Override
    public Integer getCodeByLocation(String location) {
        return dbhelper.getCodeByLocation(location);
    }

    @Override
    public void findPossibleLocation(String text) {
        dbhelper.findPossibleLocation(text);
    }

    @Override
    public void onAttachPresenter(I_HomePresenter presenter) {
        dbhelper.onAttachPresenter(presenter);
    }

    @Override
    public void onDetachPresenter() {
        dbhelper.onDetachPresenter();
    }
}
