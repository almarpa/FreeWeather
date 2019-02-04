package upv.tfg.freeweather.Serializaciones.Predicciones;

import com.google.gson.annotations.SerializedName;

/*PREDICCION*/
public class PrediccionH {

    @SerializedName("dia")
    private Horaria[] horaria;

    public PrediccionH(){

    }

    /*
    Devuelve el elemento "i" del array
    Array de 3 elementos: es una prediccion horaria[0..2]
    */
    public Horaria getElementHorario(int j){
        return horaria[j];
    }

    public Horaria[] getHoraria() { return horaria; }
    public void setHoraria(Horaria[] horaria) { this.horaria = horaria; }

}
