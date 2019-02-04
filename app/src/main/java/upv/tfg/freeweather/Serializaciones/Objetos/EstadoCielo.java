package upv.tfg.freeweather.Serializaciones.Objetos;

public class EstadoCielo {

    private String value;
    private int periodo;
    private String descripcion;

    public EstadoCielo(){

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
