package upv.tfg.freeweather.serializations.predictions;


import java.io.Serializable;
import java.util.List;

import upv.tfg.freeweather.serializations.predictions.data_prediction.CotaNieveProv;
import upv.tfg.freeweather.serializations.predictions.data_prediction.EstadoCielo;
import upv.tfg.freeweather.serializations.predictions.data_prediction.HumedadRelativaDiaria;
import upv.tfg.freeweather.serializations.predictions.data_prediction.ProbPrecipitacionDiaria;
import upv.tfg.freeweather.serializations.predictions.data_prediction.RachaMax;
import upv.tfg.freeweather.serializations.predictions.data_prediction.SensTermicaDiaria;
import upv.tfg.freeweather.serializations.predictions.data_prediction.TemperaturaDiaria;
import upv.tfg.freeweather.serializations.predictions.data_prediction.Viento;

//Daily prediction
public class Diaria implements Serializable {

    private List<ProbPrecipitacionDiaria> probPrecipitacion;
    private List<CotaNieveProv> cotaNieveProv;
    private List<EstadoCielo> estadoCielo;
    private List<Viento> viento;
    private List<RachaMax> rachaMax;
    private TemperaturaDiaria temperatura;
    private SensTermicaDiaria sensTermica;
    private HumedadRelativaDiaria humedadRelativa;
    private String fecha;

    public Diaria(){

    }

    public List<EstadoCielo> getEstadoCielo() {
        return estadoCielo;
    }

    public void setEstadoCielo(List<EstadoCielo> estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public List<ProbPrecipitacionDiaria> getProbPrecipitacion() {
        return probPrecipitacion;
    }

    public void setProbPrecipitacion(List<ProbPrecipitacionDiaria> probPrecipitacionDiaria) {
        this.probPrecipitacion = probPrecipitacionDiaria;
    }

    public List<Viento> getViento() {
        return viento;
    }

    public void setViento(List<Viento> viento) {
        this.viento = viento;
    }

    public List<CotaNieveProv> getProbNieve() {
            return cotaNieveProv;
    }

    public void setProbNieve(List<CotaNieveProv> probNieve) { this.cotaNieveProv = probNieve; }

    public List<RachaMax> getRachaMax() {
        return rachaMax;
    }

    public void setRachaMax(List<RachaMax> rachaMax) {
        this.rachaMax = rachaMax;
    }

    public TemperaturaDiaria getTemperatura() {return temperatura;}

    public void setTemperaturaDiaria(TemperaturaDiaria temperaturaDiaria) { this.temperatura = temperaturaDiaria; }

    public SensTermicaDiaria getSensTermica() { return sensTermica; }

    public void setSensTermicaDiaria(SensTermicaDiaria sensTermicaDiaria) { this.sensTermica = sensTermicaDiaria; }

    public HumedadRelativaDiaria getHumedadRelativa() {return humedadRelativa;}

    public void setHumedadRelativaDiaria(HumedadRelativaDiaria humedadRelativaDiaria) {this.humedadRelativa = humedadRelativaDiaria;}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}