package upv.tfg.freeweather.serializations.predictions.data_prediction;

import java.io.Serializable;

public class ProbPrecipitacion implements Serializable {

    private String periodo;
    private String value;

    public ProbPrecipitacion(){

    }
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
