package upv.tfg.freeweather.model.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class PreferencesHelper {

    private Context context;
    private SharedPreferences prefs;
    final String preferencesFile = "SHARED_PREFERENCES";

    public PreferencesHelper(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(preferencesFile,Context.MODE_PRIVATE);
    }

    public boolean isItFavourite(String location){
        return prefs.getBoolean(location,false);
    }

    public void addFavourite(String location){
        prefs.edit().putBoolean(location,true).apply();
    }

    public void deleteFavourite(String location){
        prefs.edit().putBoolean(location,false).apply();
    }

    public Map<String,?> getAllPreferences(){
            Map<String,?> keys = prefs.getAll();
        return keys;
    }

    public void addFavItemToSearch(String location) {
        prefs.edit().putString("FAV_ITEM_TO_SEARCH",location);
    }

    public String getLocationByName(String fav_item_to_search) {
        return prefs.getString(fav_item_to_search,null);
    }
}