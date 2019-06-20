package upv.tfg.freeweather.views.interfaces;

import android.content.Intent;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public interface I_DailyView {
    void initiateActivity(Intent intent);
    void setBarchartData(BarData data);
    void setBarchartXAxis(IndexAxisValueFormatter vFormatter);
    void setLinechartData(LineData data);
    void setLinechartXAxis(IndexAxisValueFormatter indexAxisValueFormatter);
}
