package upv.tfg.freeweather.view.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.data.model.HourlyPrediction;

public class HourlyInfoActivity extends AppCompatActivity {

    // Hourly prediction object to get info
    private HourlyPrediction[] hp;
    // Item position at the list
    private Integer pos;

    private Toolbar toolbar;
    private ImageView ivState;
    private TextView tvTime;
    private TextView tvDegrees;
    private TextView tvDescription;
    private TextView tvThermSense;
    private TextView tvPrecipProb;
    private TextView tvPrecipitation;
    private TextView tvHumidity;
    private TextView tvSnow;
    private TextView tvStormProb;
    private TextView tvSnowProb;
    private TextView tvWind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_hour_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        hp = (HourlyPrediction[]) getIntent().getSerializableExtra("HourlyPrediction");
        pos = getIntent().getIntExtra("Item_Position",0);

        initializeViews();
        displayInfo(hp);
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.tbHourly);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTime = findViewById(R.id.tvHour);
        ivState = findViewById(R.id.ivState);
        tvDegrees = findViewById(R.id.tvDegrees);
        tvDescription = findViewById(R.id.tvDescription);
        tvThermSense = findViewById(R.id.tvThermSens);
        tvPrecipitation = findViewById(R.id.tvPrecipitation);
        tvPrecipProb = findViewById(R.id.tvPrecipProb);
        //tvWind = findViewById(R.id.tvWind);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvSnow = findViewById(R.id.tvSnow);
        tvStormProb = findViewById(R.id.tvStormProb);
        tvSnowProb = findViewById(R.id.tvSnowProb);
    }

    private void displayInfo(HourlyPrediction[] hp) {
        Glide.with(getApplicationContext())
                .load(hp[0].getStateImage(pos))
                .into(ivState);
        tvTime.setText(hp[0].getTime(pos));
        tvDegrees.setText(hp[0].getDegrees(pos));
        tvDescription.setText(hp[0].getStateDescription(pos));
        tvThermSense.setText(hp[0].getThermSense(pos));
        tvHumidity.setText(hp[0].getHumidity(pos));
        tvSnow.setText(hp[0].getSnow(pos));
        tvPrecipitation.setText(hp[0].getRain(pos));
        tvPrecipProb.setText(hp[0].getProbPrecipitation(pos));
        tvSnowProb.setText(hp[0].getProbSnow(pos));
        tvStormProb.setText(hp[0].getProbStorm(pos));
        //tvWind.setText(hp[0].getWind(pos));
    }
}
