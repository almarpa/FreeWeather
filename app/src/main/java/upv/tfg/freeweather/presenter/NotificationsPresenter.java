package upv.tfg.freeweather.presenter;

import android.content.Context;
import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import upv.tfg.freeweather.model.NotificationsInteractor;
import upv.tfg.freeweather.model.interfaces.I_NotificationsInteractor;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.view.interfaces.I_NotificationsActivity;

public class NotificationsPresenter implements I_NotificationsPresenter {

    // View reference
    private I_NotificationsActivity notifActivity;
    // Model reference
    private I_NotificationsInteractor notifInteractor;

    private Context context;

    public NotificationsPresenter(I_NotificationsActivity notifActivity, Context context) {
        this.notifActivity = notifActivity;
        this.context = context;
    }

    @Override
    public void setModel(NotificationsInteractor model) {
        notifInteractor = model;
    }

    @Override
    public void notifyGetFavoriteLocations() {
        Map<String,?> mFavourites = notifInteractor.getFavouriteLocation();
        List<RadioButton> lRadioButtons = new ArrayList<>();
        RadioButton radioButton = new RadioButton(context);
        for (Map.Entry<String, ?> entry : mFavourites.entrySet()) {
            //Check if the location is saved as favourite
            if(entry.getValue().toString() == "true"){
                radioButton.setText(entry.getKey());
                radioButton.setTextColor(Color.BLACK);
                lRadioButtons.add(radioButton);
            }
        }
        notifActivity.setDataRadioGroup(lRadioButtons);
    }
}
