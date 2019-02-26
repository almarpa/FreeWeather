package upv.tfg.freeweather.Serializaciones.Predicciones;

import java.util.List;

/*Prediccion horaria*/
public class Horaria {

    private EstadoCielo[] estadoCielo;
    private List precipitacion;
    private List probPrecipitacion;
    private List probTormenta;
    private List nieve;
    private List probNieve;
    private List temperatura;
    private List sensTermica;
    private List humedadRelativa;
    private List vientoAndRachaMax;
    private String fecha;

    public Horaria(){

    }

    public EstadoCielo getElement(int j){
        return estadoCielo[j];
    }

    public EstadoCielo[] getEstadoCielo() {
        return estadoCielo;
    }

    public void setEstadoCielo(EstadoCielo[] estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public List getPrecipitacion() {
        return precipitacion;
    }

    public void setPrecipitacion(List precipitacion) {
        this.precipitacion = precipitacion;
    }

    public List getProbPrecipitacion() {
        return probPrecipitacion;
    }

    public void setProbPrecipitacion(List probPrecipitacion) { this.probPrecipitacion = probPrecipitacion; }

    public List getProbTormenta() {
        return probTormenta;
    }

    public void setProbTormenta(List probTormenta) {
        this.probTormenta = probTormenta;
    }

    public List getNieve() {
        return nieve;
    }

    public void setNieve(List nieve) {
        this.nieve = nieve;
    }

    public List getProbNieve() {
        return probNieve;
    }

    public void setProbNieve(List probNieve) {
        this.probNieve = probNieve;
    }

    public List getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(List temperatura) {
        this.temperatura = temperatura;
    }

    public List getSensTermica() {
        return sensTermica;
    }

    public void setSensTermica(List sensTermica) {
        this.sensTermica = sensTermica;
    }

    public List getHumedadRelativa() {
        return humedadRelativa;
    }

    public void setHumedadRelativa(List humedadRelativa) {
        this.humedadRelativa = humedadRelativa;
    }

    public List getVientoAndRachaMax() {
        return vientoAndRachaMax;
    }

    public void setVientoAndRachaMax(List vientoAndRachaMax) { this.vientoAndRachaMax = vientoAndRachaMax; }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
