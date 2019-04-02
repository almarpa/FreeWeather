package upv.tfg.freeweather.serializations.predictions.data_prediction;

class Dato {

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
