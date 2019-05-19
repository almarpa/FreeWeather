package upv.tfg.freeweather.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class PreferencesHelper {

    private SharedPreferences prefs;
    final String preferencesFile = "SHARED_PREFERENCES";

    public PreferencesHelper(Context context){
        prefs = context.getSharedPreferences(preferencesFile,Context.MODE_PRIVATE);
    }

    public boolean getIsFirstTimeRunning() {
        return prefs.getBoolean("firstStart", true);
    }

    public void saveDBCreation() {
        prefs.edit().putBoolean("firstStart", false).apply();
    }

    public boolean getIsItFavourite(String location){
        return prefs.getBoolean(location,false);
    }

    public void addFavourite(String location){
        prefs.edit().putBoolean(location,true).apply();
    }

    public void deleteFavourite(String location){
        prefs.edit().putBoolean(location,false).apply();
        prefs.edit().putString("LOCATION_CHOOSED",null).apply();
    }

    public Map<String,?> getAllFavourites(){
            Map<String,?> keys = prefs.getAll();
        return keys;
    }

    public void saveCurrentNotification(String text) {
        prefs.edit().putString("CURRENT_NOTIFICATION",text).apply();
    }

    public void saveSwicthState(int checked) {
        prefs.edit().putInt("SWITCH_STATE",checked).apply();
    }

    public void saveTimeOptionChoosed(String time) {
        prefs.edit().putString("TIME_CHOOSED",time).apply();
    }

    public void saveLocationChoosed(String location) {
        prefs.edit().putString("LOCATION_CHOOSED",location).apply();
    }

    public String getCurrentNotification() {
        return prefs.getString("CURRENT_NOTIFICATION",null);
    }

    public int getLastSwitch() {
        return prefs.getInt("SWITCH_STATE",0);
    }

    public String getIntervalTimeChoosed() {
        Map<String,?> keys = prefs.getAll();
        if(keys.containsKey("TIME_CHOOSED")){
            return prefs.getString("TIME_CHOOSED",null);
        }else{
            return null;
        }
    }

    public String getLocationChoosed() {
        Map<String, ?> keys = prefs.getAll();
        if (keys.containsKey("LOCATION_CHOOSED")) {
            return prefs.getString("LOCATION_CHOOSED", null);
        }else{
            return null;
        }
    }
}