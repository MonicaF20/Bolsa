package fia.ues.sv.bolsatrabajo;

/**
 * Created by Mónica on 20/05/2015.
 */
public class InstitucionEducacion {
    private int idIE;
    private String nombreIE;
    private String deptoIE;

    public InstitucionEducacion() {
        //vacio
    }
    public InstitucionEducacion(int idIE, String nombreIE, String deptoIE) {
        this.idIE = idIE;
        this.nombreIE = nombreIE;
        this.deptoIE = deptoIE;
    }
    public String getNombreIE() {
        return nombreIE;
    }
    public void setNombreIE(String nombreIE) {
        this.nombreIE = nombreIE;
    }
    public int getIdIE() {
        return idIE;
    }
    public void setIdIE(int idIE) {
        this.idIE = idIE;
    }
    public String getDeptoIE() {
        return deptoIE;
    }
    public void setDeptoIE(String deptoIE) {
        this.deptoIE = deptoIE;
    }
}
