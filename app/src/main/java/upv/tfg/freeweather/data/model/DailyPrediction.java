package upv.tfg.freeweather.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.data.model.serializations.Origin;
import upv.tfg.freeweather.data.model.serializations.predictions.PD;


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

    ///////////////////////////////////////////////////////////////////////////////
    ///      METHODS TO OBTAIN PREDICTION INFO FOR GRAPHICS, CARDS, ETC         ///
    ///////////////////////////////////////////////////////////////////////////////

    //TODAY_FRAGMENT
    public Integer getStateImage(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        String value;
        value = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(0).getValue();
        //If AEMET has deleted some info
        if(value.equals("")){
            value = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(2).getValue();
        }
        if(value.equals("")){
            value = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(6).getValue();
        }
        return getIconByCode(value);
    }
    public String getEstadoCielo(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        String value;
        value = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(0).getDescripcion();
        //If AEMET has deleted some info
        if(value.equals("")){
            value = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(2).getDescripcion();
        }
        if(value.equals("")){
            value = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(6).getDescripcion();
        }
        return value;
    }
    public String getProbPrecipitacion(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        Integer res;
        res = prediccion.getDiaria().get(pos).getProbPrecipitacion().get(0).getValue();
        if(res.equals(0)){
            res = getPrediccion().getDiaria().get(pos).getProbPrecipitacion().get(2).getValue();
        }
        if(res.equals(0)){
            res = getPrediccion().getDiaria().get(pos).getProbPrecipitacion().get(6).getValue();
        }
        return res.toString().concat(" %");    }
    public String getViento(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        String res;
        res = prediccion.getDiaria().get(pos).getViento().get(0).getVelocidad().toString();
        //If AEMET has deleted some info
        if(res.equals("0")){
            res = getPrediccion().getDiaria().get(pos).getViento().get(2).getVelocidad().toString();
            if(res.equals("0")){
                res = getPrediccion().getDiaria().get(pos).getViento().get(6).getVelocidad().toString();
                return res.concat(" km/h ("+prediccion.getDiaria().get(pos).getViento().get(6).getDireccion()+")");
            }
            return res.concat(" km/h ("+prediccion.getDiaria().get(pos).getViento().get(2).getDireccion()+")");
        }
        return res.concat(" km/h ("+prediccion.getDiaria().get(pos).getViento().get(0).getDireccion()+")");
    }
    public String getSnowLevels(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        String res;
        res = prediccion.getDiaria().get(pos).getProbNieve().get(0).getValue();
        if(res.equals("")){
            res = getPrediccion().getDiaria().get(pos).getProbNieve().get(2).getValue();
        }
        if(res.equals("")){
            res = getPrediccion().getDiaria().get(pos).getProbNieve().get(6).getValue();
        }
        if(res.equals("")) {
            return "--";
        }
        return res.concat(" m");
    }
    public String getRachaMax(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        String res = prediccion.getDiaria().get(pos).getRachaMax().get(0).getValue();
        //If AEMET has deleted some info
        if(res.equals("")){
            res = getPrediccion().getDiaria().get(pos).getRachaMax().get(2).getValue();
        }
        if(res.equals("")){
            res = getPrediccion().getDiaria().get(pos).getRachaMax().get(6).getValue();
        }
        if(res.equals("")) {
            return "--";
        }
        return res.concat(" km/h");
    }

    public String getTemperatura(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        //Select the temperature of the correct interval of time (0-6,6-12,12-18,18-24)
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int actHour = 0;
        for (int i = 0; i < 4; i++) {
            if(0 < hour && hour <= 6) {actHour = 0;}
            if(7 < hour && hour <= 12) {actHour = 1;}
            if(13 < hour && hour <= 18) {actHour = 2;}
            if(19 < hour && hour <= 24) {actHour = 3;}
        }
        return prediccion.getDiaria().get(pos).getTemperatura().getDato().get(actHour).getValue().concat("º");
    }
    public String getTemperaturaMaxima(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        return prediccion.getDiaria().get(pos).getTemperatura().getMaxima().concat("º");
    }
    public String getTemperaturaMinima(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        return prediccion.getDiaria().get(pos).getTemperatura().getMinima().concat("º");
    }
    public String getSensTermicaMaxima(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        return prediccion.getDiaria().get(pos).getSensTermica().getMaxima().concat("º");
    }
    public String getSensTermicaMinima(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        return prediccion.getDiaria().get(pos).getSensTermica().getMinima().concat("º");
    }
    public String getHumRelativaMaxima(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        return prediccion.getDiaria().get(pos).getHumedadRelativa().getMaxima().concat("º");
    }
    public String getHumRelativaMinima(){
        int pos = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String actDay = df.format(c.getTime());
        for (int i = 0; i < 2; i++){
            if(actDay.equals(getPrediccion().getDiaria().get(i).getFecha().substring(8,10))){
                pos = i;
            }
        }
        return prediccion.getDiaria().get(pos).getHumedadRelativa().getMinima().concat("º");
    }

    //DAILY_FRAGMENT (Adapter item elements)
    public ArrayList<String> getDays() {
        ArrayList<String> res = new ArrayList<>();
        for (int i= 0; i < getPrediccion().getDiaria().get(0).getEstadoCielo().size(); i++) {
            String date =getPrediccion().getDiaria().get(i).getFecha();
            String x = getDayOfTheWeek(date);
            res.add(x);
        }
        return res;
    }
    public ArrayList<Integer> getStateImages() {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i= 0; i < getPrediccion().getDiaria().size(); i++) {
            String code = getPrediccion().getDiaria().get(i).getEstadoCielo().get(0).getValue();
            if(code.equals("") && i == 0) {
                code = getPrediccion().getDiaria().get(i).getEstadoCielo().get(2).getValue();
                if(code.equals("")){
                    code = getPrediccion().getDiaria().get(i).getEstadoCielo().get(6).getValue();
                }
            }
            Integer icon = getIconByCode(code);
            res.add(icon);
        }
        return res;
    }
    public ArrayList<String> getMaxTemperatures() {
        ArrayList<String> res = new ArrayList<>();
        for (int i= 0; i < getPrediccion().getDiaria().size(); i++) {
            String code = getPrediccion().getDiaria().get(i).getTemperatura().getMaxima();
            res.add(code.concat("º"));
        }
        return res;
    }
    public ArrayList<String> getMinTemperatures() {
        ArrayList<String> res = new ArrayList<>();
        for (int i= 0; i < getPrediccion().getDiaria().size(); i++) {
            String code = getPrediccion().getDiaria().get(i).getTemperatura().getMinima();
            res.add(code.concat("º"));
        }
        return res;
    }
    public ArrayList<Integer> getPrecipitations() {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i= 0; i < getPrediccion().getDiaria().size(); i++) {
            Integer code = getPrediccion().getDiaria().get(i).getProbPrecipitacion().get(0).getValue();
            if(i == 0 && code == 0) {
                Integer x = getPrediccion().getDiaria().get(i).getProbPrecipitacion().get(2).getValue();
                if(x == 0) {
                    res.add(getPrediccion().getDiaria().get(i).getProbPrecipitacion().get(6).getValue());
                }else{
                    res.add(x);
                }
            } else{
                res.add(code);}
        }
        return res;
    }

    //DETAILED_HOUR_INFO_ACTIVITY
    public Integer getStateImage(int pos){
        String code;
        code = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(0).getValue();
        if(code.equals("") && pos == 0){
            code = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(2).getValue();
            if(code.equals("")){
                code = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(6).getValue();
            }
        }
        return getIconByCode(code);
    }
    public String getStateDescription(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(0).getDescripcion();
        if(res.equals("") && pos == 0){
            res = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(2).getDescripcion();
            if(res.equals("")){
                res = getPrediccion().getDiaria().get(pos).getEstadoCielo().get(6).getDescripcion();
            }
        }
        return res;
    }
    public String getRainProb(int pos){
        Integer res;
        res = getPrediccion().getDiaria().get(pos).getProbPrecipitacion().get(0).getValue();
        if(res.equals(0) && pos == 0){
            res = getPrediccion().getDiaria().get(pos).getProbPrecipitacion().get(2).getValue();
            if(res.equals(0)){
                res = getPrediccion().getDiaria().get(pos).getProbPrecipitacion().get(6).getValue();
            }
        }
        return res.toString().concat(" %");
    }
    public String getSnow(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getProbNieve().get(0).getValue();
        if(res.equals("") && pos == 0){
            res = getPrediccion().getDiaria().get(pos).getProbNieve().get(2).getValue();
            if(res.equals("")){
                res = getPrediccion().getDiaria().get(pos).getProbNieve().get(6).getValue();
            }
        }
        if(res.equals("")) return "--";
        return res.concat(" m");
    }
    public String getWind(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getViento().get(0).getDireccion();
        if(res.equals("") && pos == 0){
            if(getPrediccion().getDiaria().get(pos).getViento().get(2).getDireccion().equals("")){
                res = getPrediccion().getDiaria().get(pos).getViento().get(6).getVelocidad().toString().concat(" km/h");
                res.concat(" (" + getPrediccion().getDiaria().get(pos).getViento().get(6).getDireccion().concat(")"));
            }else {
                res = getPrediccion().getDiaria().get(pos).getViento().get(2).getVelocidad().toString().concat(" km/h");
                res.concat(" (" + getPrediccion().getDiaria().get(pos).getViento().get(2).getDireccion().concat(")"));
            }
        }else{
            res = getPrediccion().getDiaria().get(pos).getViento().get(0).getVelocidad().toString().concat(" km/h");
            res.concat(" (" + getPrediccion().getDiaria().get(pos).getViento().get(0).getDireccion().concat(")"));
        }
        return res;
    }
    public String getGusts(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getRachaMax().get(0).getValue();
        if(res.equals("") && pos == 0){
            res = getPrediccion().getDiaria().get(pos).getRachaMax().get(2).getValue();
            if(res.equals("")){
                res = getPrediccion().getDiaria().get(pos).getRachaMax().get(6).getValue();
            }
        }
        if(res.equals("")){
            return "--";
        }
        return res.concat(" km/h");
    }

    public String getDayOfTheWeek(int pos) {
        String date = getPrediccion().getDiaria().get(pos).getFecha();
        return getDayOfTheWeek(date);
    }
    public String getMaxDegrees(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getTemperatura().getMaxima();
        return res.concat("º");
    }
    public String getMinDegrees(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getTemperatura().getMinima();
        return res.concat("º");
    }
    public String getMaxThermSense(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getSensTermica().getMaxima();
        return res.concat("º");
    }
    public String getMinThermSense(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getSensTermica().getMinima();
        return res.concat("º");
    }
    public String getMaxHumidity(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getHumedadRelativa().getMaxima();
        return res.concat(" %");
    }
    public String getMinHumidity(int pos){
        String res;
        res = getPrediccion().getDiaria().get(pos).getHumedadRelativa().getMinima();
        return res.concat(" %");
    }
    public String getUV(int pos) {
        String uv;
        if (pos < 5) {
            uv = getPrediccion().getDiaria().get(pos).getUV();
        } else {
            uv = "--";
        }
        return uv;
    }

    //AUXILIAR METHODS
    private Integer getIconByCode(String code) {
        int res;
        switch (code){
            case "" :
                res = R.drawable.icon_not_exists;
                break;
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
            case "23":
                res = R.drawable.intervalos_nubosos_con_lluvia_escasa_dia;
                break;
            case "23n":
                res = R.drawable.intervalos_nubosos_con_lluvia_escasa_noche;
                break;
            case "24":
                res = R.drawable.nube_lluvia;
                break;
            case "24n":
                res = R.drawable.nube_lluvia;
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
                res = R.drawable.intervalos_nubosos_con_lluvia_escasa_dia;
                break;
            case "43n":
                res = R.drawable.intervalos_nubosos_con_lluvia_escasa_noche;
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
            case "53":
                res = R.drawable.nube_lluvia_trueno;
                break;
            case "53n":
                res = R.drawable.nube_lluvia_trueno;
                break;
            case "54":
                res = R.drawable.nube_trueno;
                break;
            case "54n":
                res = R.drawable.nube_trueno;
                break;
            case "63":
                res = R.drawable.nube_lluvia_trueno;
                break;
            case "63n":
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
    private String getDayOfTheWeek(String date) {
        String res = null;

        int month = Integer.parseInt(date.substring(5,7));
        int days = Integer.parseInt(date.substring(8,10));
        int year = Integer.parseInt(date.substring(0,4));
        Calendar c = Calendar.getInstance();
        c.set(year, month, days);
        int day = c.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                res = "fri";
                break;
            case Calendar.MONDAY:
                res = "sat.";
                break;
            case Calendar.TUESDAY:
                res = "sun.";
                break;
            case Calendar.WEDNESDAY:
                res = "mon.";
                break;
            case Calendar.THURSDAY:
                res = "tue.";
                break;
            case Calendar.FRIDAY:
                res = "wed.";
                break;
            case Calendar.SATURDAY:
                res = "thu.";
                break;
        }
        return res.concat("" + days);
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