package upv.tfg.freeweather.data.model.serializations.predictions.data_prediction;

import java.io.Serializable;
import java.util.List;

public class SensTermicaDiaria implements Serializable {
    private String maxima;
    private String minima;
    private List<Dato> dato;

    public SensTermicaDiaria() {
    }

    public String getMaxima() {
        return maxima;
    }

    public void setMaxima(String maxima) {
        this.maxima = maxima;
    }

    public String getMinima() {
        return minima;
    }

    public void setMinima(String minima) {
        this.minima = minima;
    }

    public List<Dato> getDato() {
        return dato;
    }

    public void setDato(List<Dato> dato) {
        this.dato = dato;
    }
}
