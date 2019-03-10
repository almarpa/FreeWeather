package upv.tfg.freeweather.serializations;

import com.google.gson.annotations.SerializedName;

import upv.tfg.freeweather.serializations.Predicciones.*;

public class PrediccionHoraria {

    private Origen origen;
    private String elaborado;
    private String nombre;
    private String provincia;
    @SerializedName("prediccion")
    private PrediccionH predic;

    public PrediccionHoraria(){

    }

    public Origen getOrigen() { return origen; }
    public String getElaborado() {return elaborado;}
    public String getNombre() {return nombre;}
    public String getProvincia() {return provincia; }
    public PrediccionH getPredic() { return predic; }

    public void setOrigen(Origen origen) { this.origen = origen; }
    public void setElaborado(String elaborado) {this.elaborado = elaborado;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setProvincia(String provincia) {this.provincia = provincia;}
    public void setPredic(PrediccionH predic) { this.predic = predic; }
}