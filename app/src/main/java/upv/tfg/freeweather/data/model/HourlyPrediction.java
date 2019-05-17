package upv.tfg.freeweather.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.data.model.serializations.Origin;
import upv.tfg.freeweather.data.model.serializations.predictions.PH;

public class HourlyPrediction implements Serializable {
    @SerializedName("origen")
    private Origin origin;
    private String elaborado;
    private String nombre;
    private String provincia;
    private PH prediccion;

    public HourlyPrediction(){ }

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

    ///////////////////////////////////////////////////////////////////////////////
    ///      METHODS TO OBTAIN PREDICTION INFO FOR GRAPHICS, CARDS, ETC         ///
    ///////////////////////////////////////////////////////////////////////////////

    //HOURLY FRAGMENT (Adapter item elements)
    public ArrayList<Integer> getImages() {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i= 0; i < getPrediccion().getHoraria().size(); i++) {
            for (int j = 0; j < getPrediccion().getHoraria().get(i).getEstadoCielo().size(); j++) {
                String code = getPrediccion().getHoraria().get(i).getEstadoCielo().get(j).getValue();
                Integer icon = getIconByCode(code);
                res.add(icon);
            }
        }
        return res;
    }
    public ArrayList<String> getTemperatures() {
        ArrayList<String> res = new ArrayList<>();
        for (int i= 0; i < 2; i++) {
            for (int j = 0; j < getPrediccion().getHoraria().get(i).getTemperatura().size(); j++) {
                String code = getPrediccion().getHoraria().get(i).getTemperatura().get(j).getValue();
                res.add(code.concat("º"));
            }
        }
        return res;
    }
    public ArrayList<String> getHours() {
        ArrayList<String> res = new ArrayList<>();
        for (int i= 0; i < 2; i++) {
            for (int j = 0; j < getPrediccion().getHoraria().get(i).getTemperatura().size(); j++) {
                String code = getPrediccion().getHoraria().get(i).getTemperatura().get(j).getPeriodo();
                res.add(code.concat(":00"));
            }
        }
        return res;
    }
    public ArrayList<String> getDays() {
        ArrayList<String> res = new ArrayList<>();
        for (int i= 0; i < getPrediccion().getHoraria().get(0).getEstadoCielo().size(); i++) {
            res.add("Tod.");
        }
        for (int i= 0; i < getPrediccion().getHoraria().get(1).getEstadoCielo().size(); i++) {
            res.add("Tom.");
        }
        return res;
    }
    public ArrayList<Integer> getTempImages() {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i= 0; i < 2; i++) {
            for (int j = 0; j < getPrediccion().getHoraria().get(i).getTemperatura().size(); j++) {
                if(Integer.parseInt(getPrediccion().getHoraria().get(i).getTemperatura().get(j).getValue()) < 10){
                    res.add(R.drawable.term_frio);
                }else{
                    res.add(R.drawable.term_calor);
                }
            }
        }
        return res;
    }

    //DETAILED HOUR INFO
    public String getTime(int pos){
        String  res;
        int count = getPrediccion().getHoraria().get(0).getTemperatura().size();
        if(pos < count){
            res = getPrediccion().getHoraria().get(0).getTemperatura().get(pos).getPeriodo();
        }else{
            res = getPrediccion().getHoraria().get(1).getTemperatura().get(pos-count).getPeriodo();
        }
        return res.concat(":00");
    }
    public String getDegrees(int pos){
        String  res;
        int count = getPrediccion().getHoraria().get(0).getTemperatura().size();
        if(pos < count){
            res = getPrediccion().getHoraria().get(0).getTemperatura().get(pos).getValue();
        }else{
            res = getPrediccion().getHoraria().get(1).getTemperatura().get(pos-count).getValue();
        }
        return res.concat("º");
    }
    public Integer getStateImage(int pos){
        Integer res;
        int count = getPrediccion().getHoraria().get(0).getEstadoCielo().size();
        if(pos < count){
            res = getIconByCode(getPrediccion().getHoraria().get(0).getEstadoCielo().get(pos).getValue());
        }else{
            res = getIconByCode(getPrediccion().getHoraria().get(1).getEstadoCielo().get(pos-count).getValue());
        }
        return res;
    }
    public String getStateDescription(int pos){
        String res;
        int count = getPrediccion().getHoraria().get(0).getEstadoCielo().size();
        if(pos < count){
            res = getPrediccion().getHoraria().get(0).getEstadoCielo().get(pos).getDescripcion();
        }else{
            res = getPrediccion().getHoraria().get(1).getEstadoCielo().get(pos-count).getDescripcion();
        }
        return res;
    }
    public String getThermSense(int pos){
        String res;
        int count = getPrediccion().getHoraria().get(0).getSensTermica().size();
        if(pos < count){
            res = getPrediccion().getHoraria().get(0).getSensTermica().get(pos).getValue();
        }else{
            res = getPrediccion().getHoraria().get(1).getSensTermica().get(pos-count).getValue();
        }
        return res.concat("º");
    }
    public String getRain(int pos){
        String res;
        int count = getPrediccion().getHoraria().get(0).getPrecipitacion().size();
        if(pos < count){
            res = getPrediccion().getHoraria().get(0).getPrecipitacion().get(pos).getValue();
        }else{
            res = getPrediccion().getHoraria().get(1).getPrecipitacion().get(pos-count).getValue();
        }
        return res.concat("MM");
    }
    public String getSnow(int pos){
        String res;
        int count = getPrediccion().getHoraria().get(0).getNieve().size();
        if(pos < count){
            res = getPrediccion().getHoraria().get(0).getNieve().get(pos).getValue();
        }else{
            res = getPrediccion().getHoraria().get(1).getNieve().get(pos-count).getValue();
        }
        return res.concat("cm");
    }
    public String getHumidity(int pos){
        String res;
        int count = getPrediccion().getHoraria().get(0).getHumedadRelativa().size();
        if(pos < count){
            res = getPrediccion().getHoraria().get(0).getHumedadRelativa().get(pos).getValue();
        }else{
            res = getPrediccion().getHoraria().get(1).getHumedadRelativa().get(pos-count).getValue();
        }
        return res.concat("%");
    }
    public String getProbPrecipitation(int pos){
        String res;
        String hour;
        int count = getPrediccion().getHoraria().get(0).getHumedadRelativa().size();
        if(pos < count){
            hour = getPrediccion().getHoraria().get(0).getEstadoCielo().get(pos).getPeriodo();
            res = getPrediccion().getHoraria().get(0).getProbPrecipitacion().get(getProbabilityByHour(hour)).getValue();
        }else{
            hour = getPrediccion().getHoraria().get(1).getEstadoCielo().get(pos-count).getPeriodo();
            res = getPrediccion().getHoraria().get(1).getProbPrecipitacion().get(getProbabilityByHour(hour)).getValue();
        }
        if(res == ""){ res = "0";}
        return res.concat(".0%");
    }
    public String getProbSnow(int pos){
        String res;
        String hour;
        int count = getPrediccion().getHoraria().get(0).getHumedadRelativa().size();
        if(pos < count){
            hour = getPrediccion().getHoraria().get(0).getEstadoCielo().get(pos).getPeriodo();
            res = getPrediccion().getHoraria().get(0).getProbNieve().get(getProbabilityByHour(hour)).getValue();
        }else{
            hour = getPrediccion().getHoraria().get(1).getEstadoCielo().get(pos-count).getPeriodo();
            res = getPrediccion().getHoraria().get(1).getProbNieve().get(getProbabilityByHour(hour)).getValue();
        }
        if(res == ""){ res = "0";}
        return res.concat(".0%");
    }
    public String getProbStorm(int pos){
        String res;
        String hour;
        int count = getPrediccion().getHoraria().get(0).getHumedadRelativa().size();
        if(pos < count){
            hour = getPrediccion().getHoraria().get(0).getEstadoCielo().get(pos).getPeriodo();
            res = getPrediccion().getHoraria().get(0).getProbTormenta().get(getProbabilityByHour(hour)).getValue();
        }else{
            hour = getPrediccion().getHoraria().get(1).getEstadoCielo().get(pos-count).getPeriodo();
            res = getPrediccion().getHoraria().get(1).getProbTormenta().get(getProbabilityByHour(hour)).getValue();
        }
        if(res == ""){ res = "0";}
        return res.concat(".0%");
    }
    /*
    public String getWind(Integer pos) {
        String  res;
        if(pos < 16){
            res = getPrediccion().getHoraria().get(0).getViento().get(pos).getValue();
        }else{
            res = getPrediccion().getHoraria().get(1).getViento().get(pos-16).getValue();
        }
        return res;    }

    public String getGusts(Integer pos) {
        String  res;
        if(pos < 16){
            res = getPrediccion().getHoraria().get(0).getViento().get(pos).getValue();
        }else{
            res = getPrediccion().getHoraria().get(1).getViento().get(pos-16).getValue();
        }
        return res;
    }
    */

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
        int res;
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
        return res;
    }
}