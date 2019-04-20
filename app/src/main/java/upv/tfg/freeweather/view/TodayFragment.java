package upv.tfg.freeweather.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.model.entities.DailyPrediction;
import upv.tfg.freeweather.model.entities.HourlyPrediction;

public class TodayFragment extends Fragment {

    private DailyPrediction[] dp;

    private View view;
    private TextView tvState;
    private TextView tvHumidityMax;
    private TextView tvHumidityMin;
    private TextView tvRainfall;
    private TextView tvWind;
    private TextView tvSnow;
    private TextView tvDegree;
    private TextView tvDegreeMax;
    private TextView tvDegreeMin;
    private TextView tvTermalSenseMin;
    private TextView tvTermalSenseMax;


    public TodayFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_tab_today, container, false);

        dp = (DailyPrediction[]) getArguments().getSerializable("TODAY");

        //Find view elements
        tvState = view.findViewById(R.id.tvState);
        tvHumidityMax =  view.findViewById(R.id.tvHumidityMax);
        tvHumidityMin =  view.findViewById(R.id.tvHumidityMin);
        tvTermalSenseMax = view.findViewById(R.id.tvTermalSense);
        tvTermalSenseMin = view.findViewById(R.id.tvTermalSense2);
        tvRainfall =  view.findViewById(R.id.tvPrecipitation);
        tvWind =  view.findViewById(R.id.tvWind);
        tvSnow = view.findViewById(R.id.tvSnow);
        tvDegree = view.findViewById(R.id.tvDegree);
        tvDegreeMax =  view.findViewById(R.id.tvMaxDegree);
        tvDegreeMin =  view.findViewById(R.id.tvMinDegree);

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
        //PRUEBA
        tvDegree.setText(dp[0].getTemperatura());
        tvDegreeMax.setText(dp[0].getTemperaturaMaxima());
        tvDegreeMin.setText(dp[0].getTemperaturaMinima());

        tvRainfall.setText(dp[0].getProbPrecipitacion());
        tvState.setText(dp[0].getEstadoCielo());
        tvTermalSenseMax.setText(dp[0].getSensTermicaMaxima());
        tvTermalSenseMin.setText(dp[0].getSensTermicaMinima());
        tvHumidityMax.setText(dp[0].getHumRelativaMaxima());
        tvHumidityMin.setText(dp[0].getHumRelativaMinima());
        tvWind.setText(dp[0].getViento());
        tvSnow.setText(dp[0].getProbNieve());
    }

}
