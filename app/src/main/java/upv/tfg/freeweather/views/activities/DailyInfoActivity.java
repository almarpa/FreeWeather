package upv.tfg.freeweather.views.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.data.model.DailyPrediction;

public class DailyInfoActivity extends AppCompatActivity {

    // Daily prediction object to get info
    private DailyPrediction[] dp;
    // Item position at the list
    private Integer pos;

    private Toolbar toolbar;
    private TextView tvTime;
    private ImageView ivState;
    private TextView tvDescription;
    private TextView tvMaxTemp;
    private TextView tvMinTemp;
    private TextView tvMaxSense;
    private TextView tvMinSense;
    private TextView tvMaxHumidity;
    private TextView tvMinHumidity;
    private TextView tvRain;
    private TextView tvWind;
    private TextView tvGusts;
    private TextView tvSnow;
    private TextView tvUV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_daily_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dp = (DailyPrediction[]) getIntent().getSerializableExtra("DailyPrediction");
        pos = getIntent().getIntExtra("Item_Position",0);

        initializeViews();
        displayInfo(dp);
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.tbDaily);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTime = findViewById(R.id.tTime);
        ivState = findViewById(R.id.iState);
        tvDescription = findViewById(R.id.tDescription);
        tvMaxTemp = findViewById(R.id.tMaxTemp);
        tvMinTemp = findViewById(R.id.tMinTemp);
        tvMaxSense = findViewById(R.id.tMaxSens);
        tvMinSense = findViewById(R.id.tMinSens);
        tvMaxHumidity = findViewById(R.id.tMaxHumidity);
        tvMinHumidity = findViewById(R.id.tMinHumidity);
        tvRain = findViewById(R.id.tRain);
        tvWind = findViewById(R.id.tWind);
        tvGusts = findViewById(R.id.tGusts);
        tvSnow = findViewById(R.id.tSnow);
        tvUV = findViewById(R.id.tUV);
    }

    private void displayInfo(DailyPrediction[] dp) {
        tvTime.setText(dp[0].getDayOfTheWeek(pos));
        ivState.setBackgroundResource(dp[0].getStateImage(pos));
        tvDescription.setText(dp[0].getStateDescription(pos));
        tvMaxTemp.setText(dp[0].getMaxDegrees(pos));
        tvMinTemp.setText(dp[0].getMinDegrees(pos));
        tvMaxSense.setText(dp[0].getMaxThermSense(pos));
        tvMinSense.setText(dp[0].getMinThermSense(pos));
        tvMaxHumidity.setText(dp[0].getMaxHumidity(pos));
        tvMinHumidity.setText(dp[0].getMinHumidity(pos));
        tvRain.setText(dp[0].getRainProb(pos));
        tvWind.setText(dp[0].getWind(pos));
        tvSnow.setText(dp[0].getSnow(pos));
        tvGusts.setText(dp[0].getGusts(pos));
        tvUV.setText(dp[0].getUV(pos));
    }
}
