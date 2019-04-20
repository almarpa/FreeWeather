package upv.tfg.freeweather.serializations;

import java.io.Serializable;

public class Origin implements Serializable {

    private String productor;
    private String web;
    private String enlace;
    private String language;
    private String copyright;
    private String notaLegal;

    public Origin(){

    }

    public String getProductor() { return productor; }
    public String getWeb() { return web; }
    public String getEnlace() { return enlace; }
    public String getLanguage() { return language; }
    public String getCopyright() { return copyright; }
    public String getNotaLegal() { return notaLegal; }

    public void setProductor(String productor) { this.productor = productor; }
    public void setWeb(String web) { this.web = web; }
    public void setEnlace(String enlace) { this.enlace = enlace; }
    public void setLanguage(String language) { this.language = language; }
    public void setCopyright(String copyright) { this.copyright = copyright; }
    public void setNotaLegal(String notaLegal) { this.notaLegal = notaLegal; }

}
