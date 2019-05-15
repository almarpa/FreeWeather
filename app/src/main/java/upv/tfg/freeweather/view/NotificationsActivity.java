package upv.tfg.freeweather.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.presenter.NotificationsPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_NotificationsPresenter;
import upv.tfg.freeweather.view.interfaces.I_NotificationsActivity;

public class NotificationsActivity extends AppCompatActivity implements I_NotificationsActivity {

    // Presenter reference
    private I_NotificationsPresenter presenter;

    private Toolbar toolbar;
    private Switch notifics;
    private RadioGroup rgTime;
    private RadioGroup rgLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rgTime = findViewById(R.id.rgTime);
        rgLocations = findViewById(R.id.rgLocations);
        notifics = findViewById(R.id.stchNotifications);

        // Create the presenter
        presenter = new NotificationsPresenter(this, getApplicationContext());

        notifics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    presenter.notifySwitchChecked();
                }else {
                    presenter.notifySwitchUnchecked();
                }
            }
        });
        rgTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                presenter.notifyRdBtnTimeChanged(group, checkedId);
            }
        });
        rgLocations.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                presenter.notifyRdBtnLocationChanged(group, checkedId);
            }
        });
    }
    /**
     * Method called by the presenter to set rgTime (RadioButton) view
     * @param rgTime
     */
    @Override
    public void setIntervalTimesView(RadioGroup rgTime) {
        this.rgTime.addView(rgTime);
    }
    /**
     * Method called by the presenter to set rgLocations (RadioButton) view
     * @param rgLocations
     */
    @Override
    public void setLocationsView(RadioGroup rgLocations) {
        this.rgLocations.addView(rgLocations);
    }
    /**
     * Method called by the presenter to show a message error
     */
    @Override
    public void showHTTPMsgError() {
        Toast.makeText(this,"HTTP error getting predictions",Toast.LENGTH_SHORT).show();
    }
}
