package upv.tfg.freeweather.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.data.model.DailyPrediction;

public class TodayFragment extends Fragment {

    private DailyPrediction[] dp;

    private View view;
    private TextView tvState;
    private TextView tvDegree;
    private TextView tvDegreeMax;
    private TextView tvDegreeMin;
    private ImageView ivState;
    private TextView tvHumidityMax;
    private TextView tvHumidityMin;
    private TextView tvRain;
    private TextView tvWind;
    private TextView tvGusts;
    private TextView tvSnow;
    private TextView tvThermalSenseMin;
    private TextView tvThermalSenseMax;


    public TodayFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_tab_today, container, false);

        dp = (DailyPrediction[]) getArguments().getSerializable("TODAY");

        tvState = view.findViewById(R.id.tvDescrip);
        tvHumidityMax =  view.findViewById(R.id.tvHumidityMax);
        tvHumidityMin =  view.findViewById(R.id.tvHumidityMin);
        tvThermalSenseMax = view.findViewById(R.id.tvThermalSenseMax);
        tvThermalSenseMin = view.findViewById(R.id.tvThermalSenseMin);
        tvRain =  view.findViewById(R.id.tvRain);
        tvWind =  view.findViewById(R.id.tvWind);
        tvGusts =  view.findViewById(R.id.tvGusts);
        tvSnow = view.findViewById(R.id.tvSnowProb);
        tvDegree = view.findViewById(R.id.tvDegrees);
        tvDegreeMax =  view.findViewById(R.id.tvMaxDegree);
        tvDegreeMin =  view.findViewById(R.id.tvMinDegree);
        ivState = view.findViewById(R.id.ivState);

        displayData(dp);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void displayData(DailyPrediction[] dp) {
        if (dp != null) {
            tvDegree.setText(dp[0].getTemperatura());
            tvDegreeMax.setText(dp[0].getTemperaturaMaxima());
            tvDegreeMin.setText(dp[0].getTemperaturaMinima());
            Glide.with(getContext())
                    .load(dp[0].getStateImage())
                    //.override(275, 275)
                    .into(ivState);
            tvRain.setText(dp[0].getProbPrecipitacion());
            tvState.setText(dp[0].getEstadoCielo());
            tvThermalSenseMax.setText(dp[0].getSensTermicaMaxima());
            tvThermalSenseMin.setText(dp[0].getSensTermicaMinima());
            tvHumidityMax.setText(dp[0].getHumRelativaMaxima());
            tvHumidityMin.setText(dp[0].getHumRelativaMinima());
            tvWind.setText(dp[0].getViento());
            tvGusts.setText(dp[0].getRachaMax());
            tvSnow.setText(dp[0].getSnowLevels());
        }
    }
}
