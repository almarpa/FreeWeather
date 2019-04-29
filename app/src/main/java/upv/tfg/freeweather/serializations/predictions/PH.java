package upv.tfg.freeweather.serializations.predictions;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*PREDICCION */
public class PH implements Serializable {

    @SerializedName("dia")
    private List<Horaria> horaria;

    public PH(){

    }

    /*
    Devuelve el elemento "i" del array
    Array de 3 elementos: es una prediccion horaria[0..2]
    */
    public List<Horaria> getHoraria() { return horaria; }
    public void setHoraria(List<Horaria> horaria) { this.horaria = horaria; }

}
