package upv.tfg.freeweather.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import upv.tfg.freeweather.serializations.Origin;
import upv.tfg.freeweather.serializations.predictions.*;


public class DailyPrediction  implements Serializable {
    @SerializedName("origen")
    private Origin origin;
    private String elaborado;
    private String nombre;
    private String provincia;
    private PD prediccion;

    public DailyPrediction(){ }

    public Origin getOrigin() { return origin; }
    public String getElaborado() {return elaborado;}
    public String getNombre() {return nombre;}
    public String getProvincia() {return provincia; }
    public PD getPrediccion() { return prediccion; }

    public void setOrigin(Origin origin) { this.origin = origin; }
    public void setElaborado(String elaborado) {this.elaborado = elaborado;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setProvincia(String provincia) {this.provincia = provincia;}
    public void setPredicion(PD prediccion) { this.prediccion = prediccion; }


    ///////////////////////////////////////////////////////////////////////////////
    ///      METHODS TO OBTAIN PREDICTION INFO FOR GRAPHICS, CARDS, ETC         ///
    ///////////////////////////////////////////////////////////////////////////////

    //ESTADO CIELO
    public String getEstadoCielo(){
        return prediccion.getDiaria().get(1).getEstadoCielo().get(0).getDescripcion();
    }

    //PROB. PRECIPITACION
    public String getProbPrecipitacion(){
        return prediccion.getDiaria().get(1).getProbPrecipitacion().get(0).getValue().toString();
    }

    //VIENTO
    public String getViento(){
        return prediccion.getDiaria().get(1).getViento().get(0).getVelocidad().toString();
    }

    //TEMPERATURA
    public String getTemperatura(){
        return prediccion.getDiaria().get(1).getTemperatura().getDato().get(0).getValue();
    }

    public String getTemperaturaMaxima(){
        return prediccion.getDiaria().get(1).getTemperatura().getMaxima();
    }

    public String getTemperaturaMinima(){
        return prediccion.getDiaria().get(1).getTemperatura().getMinima();
    }

    //HUMEDAD RELATIVA
    public String getHumRelativaMaxima(){
        return prediccion.getDiaria().get(1).getHumedadRelativa().getMaxima();
    }

    public String getHumRelativaMinima(){
        return prediccion.getDiaria().get(1).getHumedadRelativa().getMinima();
    }

    //HUMEDAD RELATIVA
    public String getSensTermicaMaxima(){
        return prediccion.getDiaria().get(1).getSensTermica().getMaxima();
    }

    public String getSensTermicaMinima(){
        return prediccion.getDiaria().get(1).getSensTermica().getMinima();
    }

    // PROB. NIEVE
    public String getProbNieve(){
        return prediccion.getDiaria().get(1).getProbNieve().get(0).getValue();
    }

    //RACHA MAX
    public String getRachaMax(){
        return prediccion.getDiaria().get(1).getRachaMax().get(0).getValue();
    }

}