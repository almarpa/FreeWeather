package upv.tfg.freeweather.serializations.predictions.data_prediction;

public class HumedadRelativaDiaria {
    private String maxima;
    private String minima;
    private Dato[] dato;

    public HumedadRelativaDiaria() {
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

    public Dato[] getDato() {
        return dato;
    }

    public void setDato(Dato[] dato) {
        this.dato = dato;
    }
}