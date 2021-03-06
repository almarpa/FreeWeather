package upv.tfg.freeweather.data.model.serializations;

public class Init {

    public String descrip;
    public String estado;
    public String datos;
    public String metadatos;

    public Init(String descrip, String estado, String datos, String metadatos) {
        this.descrip = descrip;
        this.estado = estado;
        this.datos = datos;
        this.metadatos = metadatos;
    }

    public String getDescrip() {return descrip;}
    public String getEstado() {return estado;}
    public String getDatos() {return datos;}
    public String getMetadatos() {return metadatos;}

    public void setDescrip(String descrip) {this.descrip = descrip;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setDatos(String datos) {this.datos = datos;}
    public void setMetadatos(String metadatos) {this.metadatos = metadatos;}
}