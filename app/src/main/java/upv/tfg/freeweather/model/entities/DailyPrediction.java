package upv.tfg.freeweather.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.serializations.Origin;
import upv.tfg.freeweather.serializations.predictions.*;


public class DailyPrediction  implements Serializable {
    @SerializedName("origen")
    private Origin origin;
    private String elaborado;
    private String nombre;
    private String provincia;
    private PD prediccion;

    public DailyPrediction(){ }

    public Origin getOrigin() { return origin; }
    public String getElaborado() {return elaborado;}
    public String getNombre() {return nombre;}
    public String getProvincia() {return provincia; }
    public PD getPrediccion() { return prediccion; }

    public void setOrigin(Origin origin) { this.origin = origin; }
    public void setElaborado(String elaborado) {this.elaborado = elaborado;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setProvincia(String provincia) {this.provincia = provincia;}
    public void setPredicion(PD prediccion) { this.prediccion = prediccion; }


    //TODAY FRAGMENT
    public Integer getStateImage(){
        String value;
        value = getPrediccion().getDiaria().get(0).getEstadoCielo().get(2).getValue();
        return getIconByCode(value);
    }
    public String getEstadoCielo(){
        return prediccion.getDiaria().get(0).getEstadoCielo().get(2).getDescripcion();
    }
    public String getTemperatura(){
        return prediccion.getDiaria().get(0).getTemperatura().getDato().get(1).getValue().concat("º");
    }
    public String getTemperaturaMaxima(){
        return prediccion.getDiaria().get(0).getTemperatura().getMaxima().concat("º");
    }
    public String getTemperaturaMinima(){
        return prediccion.getDiaria().get(0).getTemperatura().getMinima().concat("º");
    }
    public String getSensTermicaMaxima(){
        return prediccion.getDiaria().get(0).getSensTermica().getMaxima().concat("º");
    }
    public String getSensTermicaMinima(){
        return prediccion.getDiaria().get(0).getSensTermica().getMinima().concat("º");
    }
    public String getRachaMax(){
        return prediccion.getDiaria().get(0).getRachaMax().get(2).getValue().concat("km/h");
    }
    public String getViento(){
        String res;
        res = prediccion.getDiaria().get(0).getViento().get(2).getVelocidad().toString();
        return res.concat("km/h ("+prediccion.getDiaria().get(0).getViento().get(2).getDireccion()+")");
    }
    public String getHumRelativaMaxima(){
        return prediccion.getDiaria().get(0).getHumedadRelativa().getMaxima().concat("º");
    }
    public String getHumRelativaMinima(){
        return prediccion.getDiaria().get(0).getHumedadRelativa().getMinima().concat("º");
    }
    public String getProbPrecipitacion(){
        Integer res;
        res = getProbabilityByHour(prediccion.getDiaria().get(0).getProbPrecipitacion().get(0).getValue().toString());
        return res.toString().concat("%");    }
    public String getProbNieve(){
        Integer res;
        res = getProbabilityByHour(prediccion.getDiaria().get(0).getProbNieve().get(0).getValue());
        return res.toString().concat("%");
    }

    //DAILY FRAGMENT


    //AUXILIAR METHODS
    private Integer getIconByCode(String code) {
        int res;
        switch (code){
            case "11":
                res = R.drawable.sol;
                break;
            case "11n":
                res = R.drawable.luna;
                break;
            case "12":
                res = R.drawable.sol_nube;
                break;
            case "12n":
                res = R.drawable.nube_noche;
                break;
            case "13":
                res = R.drawable.nube_en_movimiento;
                break;
            case "13n":
                res = R.drawable.nube_en_movimiento;
                break;
            case "14":
                res = R.drawable.nubes;
                break;
            case "14n":
                res = R.drawable.nubes;
                break;
            case "15":
                res = R.drawable.nubes;
                break;
            case "15n":
                res = R.drawable.nubes;
                break;
            case "16":
                res = R.drawable.nubes;
                break;
            case "16n":
                res = R.drawable.nubes;
                break;
            case "17":
                res = R.drawable.sol_nube;
                break;
            case "17n":
                res = R.drawable.nube_noche;
                break;
            case "25":
                res = R.drawable.nube_lluvia;
                break;
            case "25n":
                res = R.drawable.nube_lluvia;
                break;
            case "26":
                res = R.drawable.nube_lluvia;
                break;
            case "26n":
                res = R.drawable.nube_lluvia;
                break;
            case "43":
                res = R.drawable.intervalos_nubosos_con_lluvia_escasa;
                break;
            case "44":
                res = R.drawable.muy_nuboso_con_lluvia_escasa;
                break;
            case "44n":
                res = R.drawable.muy_nuboso_con_lluvia_escasa;
                break;
            case "45":
                res = R.drawable.muy_nuboso_con_lluvia_escasa;
                break;
            case "45n":
                res = R.drawable.muy_nuboso_con_lluvia_escasa;
                break;
            case "46":
                res = R.drawable.cubierto_con_lluvia_escasa;
                break;
            case "46n":
                res = R.drawable.cubierto_con_lluvia_escasa;
                break;
            case "51":
                res = R.drawable.nube_trueno;
                break;
            case "51n":
                res = R.drawable.nube_trueno;
                break;
            case "52":
                res = R.drawable.nube_lluvia_trueno;
                break;
            case "52n":
                res = R.drawable.nube_lluvia_trueno;
                break;
            case "64":
                res = R.drawable.nube_lluvia_trueno;
                break;
            case "64n":
                res = R.drawable.nube_lluvia_trueno;
                break;
            default:
                res = R.drawable.copo_nieve;
        }
        return res;
    }
    private int getProbabilityByHour(String hour){
        int res = 0;
        if(!hour.equals("")){
            int h = Integer.parseInt(hour);
            if (2 <= h && h < 8){
                res = 0;
            }else if(8 <= h && h < 14){
                res = 1;
            }else if(14 <= h && h < 20){
                res = 2;
            }else{
                res = 3;
            }
        }
        return res;
    }
}