package upv.tfg.freeweather.Serializaciones.Objetos;

import com.google.gson.annotations.SerializedName;

/*PREDICCION*/
public class PrediccionD {

    @SerializedName("dia")
    private Diaria[] diaria;

    public PrediccionD(){

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