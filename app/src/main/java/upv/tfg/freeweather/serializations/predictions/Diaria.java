package upv.tfg.freeweather.serializations.predictions;

import java.util.List;

import upv.tfg.freeweather.serializations.predictions.data_prediction.CotaNieveProv;
import upv.tfg.freeweather.serializations.predictions.data_prediction.EstadoCielo;
import upv.tfg.freeweather.serializations.predictions.data_prediction.HumedadRelativaDiaria;
import upv.tfg.freeweather.serializations.predictions.data_prediction.ProbPrecipitacionDiaria;
import upv.tfg.freeweather.serializations.predictions.data_prediction.RachaMax;
import upv.tfg.freeweather.serializations.predictions.data_prediction.SensTermicaDiaria;
import upv.tfg.freeweather.serializations.predictions.data_prediction.TemperaturaDiaria;
import upv.tfg.freeweather.serializations.predictions.data_prediction.VientoDiario;

//Prediccion diaria
public class Diaria {

    private List<ProbPrecipitacionDiaria> probPrecipitacionDiaria;
    private List<CotaNieveProv> probNieve;
    private List<EstadoCielo> estadoCielo;
    private List<VientoDiario> vientoDiario;
    private List<RachaMax> rachaMax;
    //private List<TemperaturaDiaria> temperaturaDiaria;
    //private List<SensTermicaDiaria> sensTermicaDiaria;
    //private List<HumedadRelativaDiaria> humedadRelativaDiaria;
    private String fecha;
    private String uvMax;

    public Diaria(){

    }

        public List<EstadoCielo> getEstadoCielo() {
            return estadoCielo;
        }

        public void setEstadoCielo(List<EstadoCielo> estadoCielo) {
            this.estadoCielo = estadoCielo;
        }

        public List<ProbPrecipitacionDiaria> getProbPrecipitacionDiaria() {
            return probPrecipitacionDiaria;
        }

        public void setProbPrecipitacionDiaria(List<ProbPrecipitacionDiaria> probPrecipitacionDiaria) {
            this.probPrecipitacionDiaria = probPrecipitacionDiaria;
        }

        public void setProbNieve(List<CotaNieveProv> probNieve) {
            this.probNieve = probNieve;
        }

        public List<VientoDiario> getVientoDiario() {
            return vientoDiario;
        }

        public void setVientoDiario(List<VientoDiario> vientoDiario) {
            this.vientoDiario = vientoDiario;
        }

        public List<CotaNieveProv> getProbNieve() {
                return probNieve;
        }

        public List<RachaMax> getRachaMax() {
            return rachaMax;
        }

        public void setRachaMax(List<RachaMax> rachaMax) {
            this.rachaMax = rachaMax;
        }
/*
        public void setProbNieve(List<CotaNieveProv> probNieve) {
            this.probNieve = probNieve;
        }

        public List<TemperaturaDiaria> getTemperaturaDiaria() {
            return temperaturaDiaria;
        }

        public void setTemperaturaDiaria(List<TemperaturaDiaria> temperaturaDiaria) {
            this.temperaturaDiaria = temperaturaDiaria;
        }

        public List<SensTermicaDiaria> getSensTermicaDiaria() {
            return sensTermicaDiaria;
        }

        public void setSensTermicaDiaria(List<SensTermicaDiaria> sensTermicaDiaria) {
            this.sensTermicaDiaria = sensTermicaDiaria;
        }

        public List<HumedadRelativaDiaria> getHumedadRelativaDiaria() {
            return humedadRelativaDiaria;
        }

        public void setHumedadRelativaDiaria(List<HumedadRelativaDiaria> humedadRelativaDiaria) {
            this.humedadRelativaDiaria = humedadRelativaDiaria;
        }
    */
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUvMax() {
        return uvMax;
    }

    public void setUvMax(String uvMax) {
        this.uvMax = uvMax;
    }
}
