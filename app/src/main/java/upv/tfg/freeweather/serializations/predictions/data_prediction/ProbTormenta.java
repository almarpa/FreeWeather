package upv.tfg.freeweather.serializations.predictions.data_prediction;

public class ProbTormenta {

    private String value;
    private String periodo;

    public ProbTormenta(){

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
