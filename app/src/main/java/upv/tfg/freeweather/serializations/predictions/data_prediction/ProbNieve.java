package upv.tfg.freeweather.serializations.predictions.data_prediction;

public class ProbNieve {

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
