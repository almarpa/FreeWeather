package upv.tfg.freeweather.serializations;

import com.google.gson.annotations.SerializedName;

import upv.tfg.freeweather.serializations.predictions.*;

public class HourlyPrediction {
    @SerializedName("origen")
    private Origin origin;
    private String elaborado;
    private String nombre;
    private String provincia;
    private PH prediccion;

    public HourlyPrediction(){

    }

    public Origin getOrigin() { return origin; }
    public String getElaborado() {return elaborado;}
    public String getNombre() {return nombre;}
    public String getProvincia() {return provincia; }
    public PH getPrediccion() { return prediccion; }

    public void setOrigin(Origin origin) { this.origin = origin; }
    public void setElaborado(String elaborado) {this.elaborado = elaborado;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setProvincia(String provincia) {this.provincia = provincia;}
    public void setPrediccion(PH prediccion) { this.prediccion = prediccion; }



}