package upv.tfg.freeweather.serializations;

import com.google.gson.annotations.SerializedName;

import upv.tfg.freeweather.serializations.predictions.*;

public class DailyPrediction {
    @SerializedName("origen")
    private Origin origin;
    private String elaborado;
    private String nombre;
    private String provincia;
    private PD predicion;

    public DailyPrediction(){

    }

    public Origin getOrigin() { return origin; }
    public String getElaborado() {return elaborado;}
    public String getNombre() {return nombre;}
    public String getProvincia() {return provincia; }
    public PD getPrediccion() { return predicion; }

    public void setOrigin(Origin origin) { this.origin = origin; }
    public void setElaborado(String elaborado) {this.elaborado = elaborado;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setProvincia(String provincia) {this.provincia = provincia;}
    public void setPredicion(PD predicion) { this.predicion = predicion; }



}