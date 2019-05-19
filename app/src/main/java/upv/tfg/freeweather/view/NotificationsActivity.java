package upv.tfg.freeweather.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.presenter.NotificationsPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.view.interfaces.I_NotificationsActivity;

public class NotificationsActivity extends AppCompatActivity implements I_NotificationsActivity {

    // Presenter reference
    private I_NotificationsPresenter presenter;

    private Switch swchNotification;
    private RadioGroup rgTime;
    private RadioGroup rgLocations;
    private TextView tvCurrentNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rgTime = findViewById(R.id.rgTime);
        rgLocations = findViewById(R.id.rgLocations);
        swchNotification = findViewById(R.id.stchNotifications);
        tvCurrentNotification = findViewById(R.id.tvNotification);

        // Create the presenter
        presenter = new NotificationsPresenter(this, getApplicationContext());

        swchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    presenter.notifySwitchChecked();
                }else {
                    presenter.notifySwitchUnchecked();
                }
            }
        });
    }
    /**
     * Method called by the presenter to set rgTime (RadioButton) view
     * @param rgTime radiogroup item
     */
    @Override
    public void setIntervalTimesView(RadioGroup rgTime) {
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
        swchNotification.setChecked(true);
    }

    /**
     * Uncheck the switch item
     */
    @Override
    public void clearSwitch() {
        swchNotification.setChecked(false);
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
        Toast.makeText(this,"HTTP error getting predictions, try again",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoTimeChecked() {
        Toast.makeText(this,"Please, choose a notification interval time before",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoLocationChecked() {
        Toast.makeText(this,"Please, choose a location before",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoFavouriteExists() {
        Toast.makeText(this,"Please, save one location as favourite in order to be notified of it",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlarmConfigurated(String alarmConfigurated) {
        Toast.makeText(this,alarmConfigurated,Toast.LENGTH_LONG).show();
    }
}
