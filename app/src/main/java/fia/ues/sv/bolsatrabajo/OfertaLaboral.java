package fia.ues.sv.bolsatrabajo;

/**
 * Created by Edgardito on 22/05/2015.
 */
public class OfertaLaboral {
    private int idOL,idEmp,idCar;
    private String fechaP,fechaX;

    public OfertaLaboral() {
    }
    public OfertaLaboral(int idOL, int idEmp, int idCar, String fechaP, String fechaX) {
        this.idOL = idOL;
        this.idEmp = idEmp;
        this.idCar = idCar;
        this.fechaP = fechaP;
        this.fechaX = fechaX;
    }

    public int getIdOL() {
        return idOL;
    }

    public void setIdOL(int idOL) {
        this.idOL = idOL;
    }

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public String getFechaP() {
        return fechaP;
    }

    public void setFechaP(String fechaP) {
        this.fechaP = fechaP;
    }

    public String getFechaX() {
        return fechaX;
    }

    public void setFechaX(String fechaX) {
        this.fechaX = fechaX;
    }
}
