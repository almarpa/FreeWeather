package upv.tfg.freeweather.view.interfaces;

import upv.tfg.freeweather.serializations.predictions.PD;
import upv.tfg.freeweather.serializations.predictions.PH;

public interface ShowPredictions{
    void showTodayPrediction(PH ph);
    PH showHourlyPrediction();
    PD showDailyPrediction();
}
