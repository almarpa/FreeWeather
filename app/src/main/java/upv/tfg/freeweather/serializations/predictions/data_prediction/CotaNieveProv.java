package upv.tfg.freeweather.serializations.predictions.data_prediction;

import java.io.Serializable;

public class CotaNieveProv implements Serializable {

    private String value;
    private String periodo;

    public CotaNieveProv(){

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
