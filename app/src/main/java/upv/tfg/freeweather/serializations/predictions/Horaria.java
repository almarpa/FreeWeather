package upv.tfg.freeweather.serializations.predictions;

import java.util.List;

import upv.tfg.freeweather.serializations.predictions.data_prediction.EstadoCielo;
import upv.tfg.freeweather.serializations.predictions.data_prediction.HumedadRelativa;
import upv.tfg.freeweather.serializations.predictions.data_prediction.Nieve;
import upv.tfg.freeweather.serializations.predictions.data_prediction.Precipitacion;
import upv.tfg.freeweather.serializations.predictions.data_prediction.ProbNieve;
import upv.tfg.freeweather.serializations.predictions.data_prediction.ProbPrecipitacion;
import upv.tfg.freeweather.serializations.predictions.data_prediction.ProbTormenta;
import upv.tfg.freeweather.serializations.predictions.data_prediction.SensTermica;
import upv.tfg.freeweather.serializations.predictions.data_prediction.Temperatura;

/*Prediccion horaria*/
public class Horaria {

    private List<EstadoCielo> estadoCielo;
    private List<Precipitacion> precipitacion;
    private List<ProbPrecipitacion> probPrecipitacion;
    private List<ProbTormenta> probTormenta;
    private List<Nieve> nieve;
    private List<ProbNieve> probNieve;
    private List<Temperatura> temperatura;
    private List<SensTermica> sensTermica;
    private List<HumedadRelativa> humedadRelativa;
    private String fecha;

    public Horaria() {

    }

    public List<EstadoCielo> getEstadoCielo() {
        return estadoCielo;
    }

    public void setEstadoCielo(List<EstadoCielo> estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public List<Precipitacion> getPrecipitacion() {
        return precipitacion;
    }

    public void setPrecipitacion(List<Precipitacion> precipitacion) {
        this.precipitacion = precipitacion;
    }

    public List<ProbPrecipitacion> getProbPrecipitacion() {
        return probPrecipitacion;
    }

    public void setProbPrecipitacion(List<ProbPrecipitacion> probPrecipitacion) {
        this.probPrecipitacion = probPrecipitacion;
    }

    public List<ProbTormenta> getProbTormenta() {
        return probTormenta;
    }

    public void setProbTormenta(List<ProbTormenta> probTormenta) {
        this.probTormenta = probTormenta;
    }

    public List<Nieve> getNieve() {
        return nieve;
    }

    public void setNieve(List<Nieve> nieve) {
        this.nieve = nieve;
    }

    public List<ProbNieve> getProbNieve() {
        return probNieve;
    }

    public void setProbNieve(List<ProbNieve> probNieve) {
        this.probNieve = probNieve;
    }

    public List<Temperatura> getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(List<Temperatura> temperatura) {
        this.temperatura = temperatura;
    }

    public List<SensTermica> getSensTermica() {
        return sensTermica;
    }

    public void setSensTermica(List<SensTermica> sensTermica) {
        this.sensTermica = sensTermica;
    }

    public List<HumedadRelativa> getHumedadRelativa() {
        return humedadRelativa;
    }

    public void setHumedadRelativa(List<HumedadRelativa> humedadRelativa) {
        this.humedadRelativa = humedadRelativa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}