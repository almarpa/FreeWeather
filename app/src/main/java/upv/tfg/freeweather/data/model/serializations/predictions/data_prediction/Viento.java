package upv.tfg.freeweather.data.model.serializations.predictions.data_prediction;

import java.io.Serializable;

public class Viento implements Serializable {

    String direccion;
    Integer velocidad;
    String periodo;

    public Viento() {

    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Integer velocidad) {
        this.velocidad = velocidad;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
