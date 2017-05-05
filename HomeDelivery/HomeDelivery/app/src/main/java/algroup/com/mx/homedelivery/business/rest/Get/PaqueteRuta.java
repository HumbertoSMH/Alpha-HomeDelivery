package algroup.com.mx.homedelivery.business.rest.Get;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devmac02 on 27/07/15.
 */
public class PaqueteRuta {

    private String codigoPaquete;
    private boolean encontrado;
    private int idPaquete;
    private int idEspecialidad;
    private int idParrrilla;
    private String descripcionParrillaLimpia;     //Nuevo
    private String desParrilla;
    private List<MedicamentoRest>listaDetalles;

    public PaqueteRuta() {
        this.codigoPaquete = "";
        this.encontrado = false;
        this.idPaquete = 0;
        this.idEspecialidad = 0;
        this.idParrrilla = 0;
        this.descripcionParrillaLimpia = "";     //Nuevo
        this.listaDetalles = new ArrayList<>(0);
        this.desParrilla = "";
    }

    public String getCodigoPaquete() {return codigoPaquete;}

    public void setCodigoPaquete(String codigoPaquete) {this.codigoPaquete = codigoPaquete;}

    public boolean isEncontrado() {return encontrado;}

    public void setEncontrado(boolean encontrado) {this.encontrado = encontrado;}

    public int getIdPaquete() {return idPaquete;}

    public void setIdPaquete(int idPaquete) {this.idPaquete = idPaquete;}

    public List<MedicamentoRest> getListaDetalles() {return listaDetalles;}

    public void setListaDetalles(List<MedicamentoRest> listaDetalles) {this.listaDetalles = listaDetalles;}

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public int getIdParrrilla() {
        return idParrrilla;
    }

    public void setIdParrrilla(int idParrrilla) {
        this.idParrrilla = idParrrilla;
    }

    public String getDescripcionParrillaLimpia() {
        return descripcionParrillaLimpia;
    }

    public void setDescripcionParrillaLimpia(String descripcionParrillaLimpia) {
        this.descripcionParrillaLimpia = descripcionParrillaLimpia;
    }

    public String getDesParrilla() {
        return desParrilla;
    }

    public void setDesParrilla(String desParrilla) {
        this.desParrilla = desParrilla;
    }

    @Override
    public String toString() {
        return "PaqueteRuta{" +
                "codigoPaquete='" + codigoPaquete + '\'' +
                ", encontrado=" + encontrado +
                ", idPaquete=" + idPaquete +
                ", idEspecialidad=" + idEspecialidad +
                ", idParrrilla=" + idParrrilla +
                ", descripcionParrillaLimpia='" + descripcionParrillaLimpia + '\'' +
                ", desParrilla='" + desParrilla + '\'' +
                ", listaDetalles=" + listaDetalles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaqueteRuta that = (PaqueteRuta) o;

        if (encontrado != that.encontrado) return false;
        if (idPaquete != that.idPaquete) return false;
        if (idEspecialidad != that.idEspecialidad) return false;
        if (idParrrilla != that.idParrrilla) return false;
        if (!codigoPaquete.equals(that.codigoPaquete)) return false;
        if (!descripcionParrillaLimpia.equals(that.descripcionParrillaLimpia)) return false;
        if (!desParrilla.equals(that.desParrilla)) return false;
        return listaDetalles.equals(that.listaDetalles);

    }

    @Override
    public int hashCode() {
        int result = codigoPaquete.hashCode();
        result = 31 * result + (encontrado ? 1 : 0);
        result = 31 * result + idPaquete;
        result = 31 * result + idEspecialidad;
        result = 31 * result + idParrrilla;
        result = 31 * result + descripcionParrillaLimpia.hashCode();
        result = 31 * result + desParrilla.hashCode();
        result = 31 * result + listaDetalles.hashCode();
        return result;
    }
}
