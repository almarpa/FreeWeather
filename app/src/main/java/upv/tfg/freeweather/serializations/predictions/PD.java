package upv.tfg.freeweather.serializations.predictions;

import com.google.gson.annotations.SerializedName;

/*PREDICCION*/
public class PD {

    @SerializedName("dia")
    private Diaria[] diaria;

    public PD(){

    }

    /*
    Devuelve el elemento "i" del array
    Array de 5 elementos: es una prediccion diaria[0..4]
    */
    public Diaria getElementDiario(int j){
        return diaria[j];
    }

    public Diaria[] getHoraria() { return diaria; }
    public void setHoraria(Diaria[] diaria) { this.diaria = diaria; }

}