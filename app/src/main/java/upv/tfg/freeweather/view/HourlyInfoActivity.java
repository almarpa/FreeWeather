package upv.tfg.freeweather.view;

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

    private HourlyPrediction[] hp;
    //Item position at the list
    private Integer pos;

    private Toolbar toolbar;
    private ImageView ivState;
    private TextView tvTime;
    private TextView tvDegrees;
    private TextView tvDescrip;
    private TextView tvThermSense;
    private TextView tvRain;
    private TextView tvChancePrecip;
    private TextView tvWind;
    private TextView tvHumidity;
    private TextView tvSnow;
    private TextView tvGusts;
    private TextView tvStormProb;
    private TextView tvSnowProb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_hour_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        hp = (HourlyPrediction[]) getIntent().getSerializableExtra("HourlyPrediction");
        pos = getIntent().getIntExtra("Item_Position",0);

        initializeElements();
        displayInfo(hp);
    }

    private void initializeElements() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTime = findViewById(R.id.tvTime);
        ivState = findViewById(R.id.ivState);
        tvDegrees = findViewById(R.id.tvDegrees);
        tvDescrip = findViewById(R.id.tvDescrip);
        tvThermSense = findViewById(R.id.tvThermSense);
        tvRain = findViewById(R.id.tvRain);
        tvChancePrecip = findViewById(R.id.tvChancePrecip);
        tvWind = findViewById(R.id.tvWind);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvSnow = findViewById(R.id.tvSnow);
        tvGusts = findViewById(R.id.tvGusts);
        tvStormProb = findViewById(R.id.tvStormProb);
        tvSnowProb = findViewById(R.id.tvSnowProb);
    }

    private void displayInfo(HourlyPrediction[] hp) {
        Glide.with(getApplicationContext())
                .load(hp[0].getStateImage(pos))
                //.override(350, 350)
                .into(ivState);
        tvTime.setText(hp[0].getTime(pos));
        tvDegrees.setText(hp[0].getDegrees(pos));
        tvDescrip.setText(hp[0].getStateDescription(pos));
        tvThermSense.setText(hp[0].getThermSense(pos));
        tvRain.setText(hp[0].getRain(pos));
        tvHumidity.setText(hp[0].getHumidity(pos));
        tvSnow.setText(hp[0].getSnow(pos));
        tvChancePrecip.setText(hp[0].getProbPrecipitation(pos));
        tvSnowProb.setText(hp[0].getProbSnow(pos));
        tvStormProb.setText(hp[0].getProbStorm(pos));
        //tvWind.setText(hp[0].getWind(pos));
        //tvGusts.setText(hp[0].getGusts(pos));
    }
}
