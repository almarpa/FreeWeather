package upv.tfg.freeweather.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class PreferencesHelper {

    private SharedPreferences userPrefs;
    private SharedPreferences dataPrefs;
    final String userPreferences = "USER_PREFERENCES";
    final String dataPreferences = "DATA_PREFERENCES";

    public PreferencesHelper(Context context){
        userPrefs = context.getSharedPreferences(userPreferences,Context.MODE_PRIVATE);
        dataPrefs = context.getSharedPreferences(dataPreferences,Context.MODE_PRIVATE);
    }

    public boolean getIsFirstTimeRunning() {
        return userPrefs.getBoolean("firstStart", true);
    }

    public void saveDBCreation() {
        userPrefs.edit().putBoolean("firstStart", false).apply();
    }

    public boolean getIsItFavourite(String location){
        return dataPrefs.getBoolean(location,false);
    }

    public void addFavourite(String location){
        dataPrefs.edit().putBoolean(location,true).apply();
    }

    public void deleteFavourite(String location){
        dataPrefs.edit().putBoolean(location,false).apply();
        dataPrefs.edit().putString("LOCATION_CHOOSED",null).apply();
    }

    public Map<String,?> getAllFavourites(){
        Map<String,?> keys = dataPrefs.getAll();
        return keys;
    }

    public void saveCurrentNotification(String text) {
        userPrefs.edit().putString("CURRENT_NOTIFICATION",text).apply();
    }

    public void saveSwicthState(int checked) {
        userPrefs.edit().putInt("SWITCH_STATE",checked).apply();
    }

    public void saveTimeOptionChoosed(String time) {
        userPrefs.edit().putString("TIME_CHOOSED",time).apply();
    }

    public void saveLocationChoosed(String location) {
        userPrefs.edit().putString("LOCATION_CHOOSED",location).apply();
    }

    public String getCurrentNotification() {
        return userPrefs.getString("CURRENT_NOTIFICATION",null);
    }

    public int getLastSwitch() {
        return userPrefs.getInt("SWITCH_STATE",0);
    }

    public String getIntervalTimeChoosed() {
        Map<String,?> keys = userPrefs.getAll();
        if(keys.containsKey("TIME_CHOOSED")){
            return userPrefs.getString("TIME_CHOOSED",null);
        }else{
            return null;
        }
    }

    public String getLocationChoosed() {
        Map<String, ?> keys = userPrefs.getAll();
        if (keys.containsKey("LOCATION_CHOOSED")) {
            return userPrefs.getString("LOCATION_CHOOSED", null);
        }else{
            return null;
        }
    }
}