package upv.tfg.freeweather.serializations;

import upv.tfg.freeweather.serializations.predictions.*;

public class DailyPrediction {

    private Origin origin;
    private String elaborado;
    private String nombre;
    private String provincia;
    private PH predicion;

    public DailyPrediction(){

    }

    public Origin getOrigin() { return origin; }
    public String getElaborado() {return elaborado;}
    public String getNombre() {return nombre;}
    public String getProvincia() {return provincia; }
    public PH getPrediccion() { return predicion; }

    public void setOrigin(Origin origin) { this.origin = origin; }
    public void setElaborado(String elaborado) {this.elaborado = elaborado;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setProvincia(String provincia) {this.provincia = provincia;}
    public void setPredicion(PH predicion) { this.predicion = predicion; }
}