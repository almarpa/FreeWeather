package upv.tfg.freeweather.serializations.predictions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*PREDICCION */
public class PD {

    @SerializedName("dia")
    private List<Diaria> diaria;

    public PD(){

    }

    /*
    Devuelve el elemento "i" del array
    Array de 7 elementos: es una prediccion diaria[0..6]
    */
    public List<Diaria> getDiaria() { return diaria; }
    public void setDiaria(List<Diaria> diaria) { this.diaria = diaria; }

}