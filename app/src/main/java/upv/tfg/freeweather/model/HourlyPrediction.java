package upv.tfg.freeweather.model;

import com.google.gson.annotations.SerializedName;

import upv.tfg.freeweather.serializations.Origin;
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


    //
    //  MÉTODOS PARA OBTENER LA INFORMACIÓN, GRÁFICAS, ETC. DE LA PREDICCIÓN
    //
    public String getTemperatura(){
        String res;
        res = prediccion.getHoraria().get(0).getTemperatura().get(0).getValue();
        return res;
    }
    /*
        hp = sp[0].getPrediccion();
        TextView tvDatos =  getView().findViewById(R.id.tvDatos);
        String text = "Horaria\n";
        for (int i = 0; i < hp.getHoraria().size(); i ++){
            text += "Dia " + hp.getHoraria().get(i).getFecha() +": \n";
            for (int j = 0; j < hp.getHoraria().get(i).getProbPrecipitacion().size(); j++) {
                text += hp.getHoraria().get(i).getProbPrecipitacion().get(j).getPeriodo() + " " + hp.getHoraria().get(i).getProbPrecipitacion().get(j).getValue()+",\n" ;
            }
        }
        tvDatos.setText(text);
        */
}