package upv.tfg.freeweather.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.presenter.NotificationsPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.view.interfaces.I_NotificationsView;

public class NotificationsFragment extends Fragment implements I_NotificationsView {
    // Presenter reference
    private I_NotificationsPresenter presenter;

    private Switch swthActivate;
    private RadioGroup rgTime;
    private RadioGroup rgLocations;
    private TextView tvCurrentNotification;

    private View view;
    private Context context;

    public NotificationsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_menu_notifications, container, false);
        context = view.getContext();

        rgTime = view.findViewById(R.id.rgTime);
        rgLocations = view.findViewById(R.id.rgLocations);
        swthActivate = view.findViewById(R.id.stchNotifications);
        tvCurrentNotification = view.findViewById(R.id.tvNotification);

        // Create the presenter
        presenter = new NotificationsPresenter(this, context);

        swthActivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    presenter.notifySwitchChecked();
                }else {
                    presenter.notifySwitchUnchecked();
                }
            }
        });

        return view;
    }

    /**
     * Method called by the presenter to set rgTime (RadioButton) view
     * @param rgTime radiogroup item
     */
    @Override
    public void setTimeView(RadioGroup rgTime) {
        this.rgTime.addView(rgTime);
    }

    /**
     * Method called by the presenter to set rgLocations (RadioButton) view
     * @param rgLocations radiogroup item
     */

    @Override
    public void setLocationsView(RadioGroup rgLocations) {
        this.rgLocations.addView(rgLocations);
    }

    /**
     * Check the switch item
     */
    @Override
    public void doSwitch() {
        swthActivate.setChecked(true);
    }

    /**
     * Uncheck the switch item
     */
    @Override
    public void clearSwitch() {
        swthActivate.setChecked(false);
    }

    @Override
    public void setCurrentNotification(String text) {
        tvCurrentNotification.setText(text);
    }

    @Override
    public void clearCurrentNotification() {
        tvCurrentNotification.setText("");
    }

    /**
     * Method called by the presenter to show a message error
     */
    @Override
    public void showHTTPMsgError() {
        Toast.makeText(context,"HTTP error getting predictions, try again",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoTimeChecked() {
        Toast.makeText(context,"Please, choose a notification interval time before",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoLocationChecked() {
        Toast.makeText(context,"Please, choose a location before",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoFavouriteExists() {
        Toast.makeText(context,"Please, save one location as favourite in order to be notified of it",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlarmConfigurated(String alarmConfigurated) {
        Toast.makeText(context,alarmConfigurated,Toast.LENGTH_LONG).show();
    }
}
