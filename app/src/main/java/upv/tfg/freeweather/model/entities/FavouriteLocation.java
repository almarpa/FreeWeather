package upv.tfg.freeweather.model.entities;

public class FavouriteLocation {

    private String nombre;
    private String codigo;

    public FavouriteLocation(String nombre, String codigo) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
