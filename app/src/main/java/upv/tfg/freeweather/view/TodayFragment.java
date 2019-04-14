package upv.tfg.freeweather.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.model.entities.HourlyPrediction;

public class TodayFragment extends Fragment {

    private HourlyPrediction[] dp;

    private View view;
    private TextView tvState;
    private TextView tvHumidity;
    private TextView tvRainfall;
    private TextView tvThermalSens;

    public TodayFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_tab_today, container, false);

        dp = (HourlyPrediction[]) getArguments().getSerializable(
                "TODAY");

        //Find view elements
        tvState = view.findViewById(R.id.tvState);
        tvHumidity =  view.findViewById(R.id.tvHumedad);
        tvRainfall =  view.findViewById(R.id.tvPrecipitacion);
        tvThermalSens =  view.findViewById(R.id.tvSensTermica);

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

    private void displayData(HourlyPrediction[] dp) {
        tvState.setText(dp[0].getPrediccion().getHoraria().get(0).getEstadoCielo().get(0).getDescripcion());
        tvHumidity.setText(dp[0].getPrediccion().getHoraria().get(0).getHumedadRelativa().get(0).getValue());
        tvRainfall.setText(dp[0].getPrediccion().getHoraria().get(0).getPrecipitacion().get(0).getValue());
        tvThermalSens.setText(dp[0].getPrediccion().getHoraria().get(0).getSensTermica().get(0).getValue());
    }

}
