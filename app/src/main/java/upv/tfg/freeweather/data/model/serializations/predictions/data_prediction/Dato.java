package upv.tfg.freeweather.data.model.serializations.predictions.data_prediction;

import java.io.Serializable;

public class Dato implements Serializable {

    String value;
    Byte hora;

    public Dato(){

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Byte getHora() {
        return hora;
    }

    public void setHora(Byte hora) {
        this.hora = hora;
    }

}
