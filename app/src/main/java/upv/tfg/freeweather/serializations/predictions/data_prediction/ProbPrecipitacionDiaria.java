package upv.tfg.freeweather.serializations.predictions.data_prediction;

public class ProbPrecipitacionDiaria {

    private String periodo;
    private Integer value;

    public ProbPrecipitacionDiaria(){

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
