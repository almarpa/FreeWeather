package upv.tfg.freeweather.data.model.serializations.predictions.data_prediction;

import java.io.Serializable;

public class ProbPrecipitacionDiaria implements Serializable {

    private String periodo;
    private Integer value;

    public ProbPrecipitacionDiaria() {

    }
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


}
