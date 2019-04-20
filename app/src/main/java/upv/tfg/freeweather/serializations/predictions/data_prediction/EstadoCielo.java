package upv.tfg.freeweather.serializations.predictions.data_prediction;

import java.io.Serializable;

public class EstadoCielo implements Serializable {

    private String value;
    private String periodo;
    private String descripcion;

    public EstadoCielo(){

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
