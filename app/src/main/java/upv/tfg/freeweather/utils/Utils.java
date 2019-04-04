package upv.tfg.freeweather.utils;


/**
 * Class that contain auxiliar methods for the treatment of the serialized data.
 */
public class Utils {

    // Method to obtain a substring from character '/'
    public String cutStringBy(String variable){
        String substr= variable.substring(variable.indexOf('a'));
        return substr;
    }

    //...........
}
