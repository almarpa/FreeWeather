package upv.tfg.freeweather.Serializaciones;

import com.google.gson.annotations.SerializedName;

import upv.tfg.freeweather.Serializaciones.Objetos.*;

public class PrediccionHorariaNEW {

    private Origen origen;
    private String elaborado;
    private String nombre;
    private String provincia;
    @SerializedName("prediccion")
    private PrediccionHoraria predic;

    public PrediccionHorariaNEW(){

    }

    public Origen getOrigen() { return origen; }
    public String getElaborado() {return elaborado;}
    public String getNombre() {return nombre;}
    public String getProvincia() {return provincia; }
    public PrediccionHoraria getPredic() { return predic; }

    public void setOrigen(Origen origen) { this.origen = origen; }
    public void setElaborado(String elaborado) {this.elaborado = elaborado;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setProvincia(String provincia) {this.provincia = provincia;}
    public void setPredic(PrediccionHoraria predic) { this.predic = predic; }
}