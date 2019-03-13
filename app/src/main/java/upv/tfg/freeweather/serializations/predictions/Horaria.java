package upv.tfg.freeweather.serializations.predictions;

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

    private EstadoCielo[] estadoCielo;
    private Precipitacion[] precipitacion;
    private ProbPrecipitacion[] probPrecipitacion;
    private ProbTormenta[] probTormenta;
    private Nieve[] nieve;
    private ProbNieve[] probNieve;
    private Temperatura[] temperatura;
    private SensTermica[] sensTermica;
    private HumedadRelativa[] humedadRelativa;
    private String fecha;

    public Horaria() {

    }

    public EstadoCielo[] getEstadoCielo() {
        return estadoCielo;
    }
    public EstadoCielo getEstadoCielo(int i) {
        return estadoCielo[i];
    }
    public void setEstadoCielo(EstadoCielo[] estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public Precipitacion[] getPrecipitacion() {
        return precipitacion;
    }
    public Precipitacion getPrecipitacion(int i) {
        return precipitacion[i];
    }
    public void setPrecipitacion(Precipitacion[] precipitacion) {
        this.precipitacion = precipitacion;
    }

    public ProbPrecipitacion[] getProbPrecipitacion() {
        return probPrecipitacion;
    }
    public ProbPrecipitacion getProbPrecipitacion(int i) {
        return probPrecipitacion[i];
    }
    public void setProbPrecipitacion(ProbPrecipitacion[] probPrecipitacion) {
        this.probPrecipitacion = probPrecipitacion;
    }

    public ProbTormenta[] getProbTormenta() {
        return probTormenta;
    }
    public ProbTormenta getProbTormenta(int i) {
        return probTormenta[i];
    }
    public void setProbTormenta(ProbTormenta[] probTormenta) {
        this.probTormenta = probTormenta;
    }

    public Nieve[] getNieve() {
        return nieve;
    }
    public Nieve getNieve(int i) {
        return nieve[i];
    }
    public void setNieve(Nieve[] nieve) {
        this.nieve = nieve;
    }

    public ProbNieve[] getProbNieve() {
        return probNieve;
    }
    public ProbNieve getProbNieve(int i) {
        return probNieve[i];
    }

    public void setProbNieve(ProbNieve[] probNieve) {
        this.probNieve = probNieve;
    }

    public Temperatura[] getTemperatura() {
        return temperatura;
    }
    public Temperatura getTemperatura(int i) {
        return temperatura[i];
    }

    public void setTemperatura(Temperatura[] temperatura) {
        this.temperatura = temperatura;
    }

    public SensTermica[] getSensTermica() {
        return sensTermica;
    }
    public SensTermica getSensTermica(int i) {
        return sensTermica[i];
    }
    public void setSensTermica(SensTermica[] sensTermica) {
        this.sensTermica = sensTermica;
    }

    public HumedadRelativa[] getHumedadRelativa() {
        return humedadRelativa;
    }
    public HumedadRelativa getHumedadRelativa(int i) {
        return humedadRelativa[i];
    }
    public void setHumedadRelativa(HumedadRelativa[] humedadRelativa) {
        this.humedadRelativa = humedadRelativa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}