package upv.tfg.freeweather.data.model.serializations.predictions.data_prediction;

import java.io.Serializable;

public class ProbNieve implements Serializable {

    private String value;
    private String periodo;

    public ProbNieve(){
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
