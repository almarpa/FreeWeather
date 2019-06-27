package upv.tfg.freeweather.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.presenters.NotificationsPresenter;
import upv.tfg.freeweather.presenters.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.views.interfaces.I_NotificationsView;

public class NotificationsFragment extends Fragment implements I_NotificationsView {
    // Presenter reference
    private I_NotificationsPresenter presenter;

    private Switch swthActivate;
    private RadioGroup rgTime;
    private Spinner spLocations;
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
        spLocations = view.findViewById(R.id.spLocations);
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
        spLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.notifySpinner2Clicked(spLocations.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
     * @param adapter Spinner adapter
     */
    @Override
    public void setLocationsView(SpinnerAdapter adapter) {
        this.spLocations.setAdapter(adapter);
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
