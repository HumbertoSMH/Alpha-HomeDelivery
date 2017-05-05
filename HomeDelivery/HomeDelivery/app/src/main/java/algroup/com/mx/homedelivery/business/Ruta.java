package algroup.com.mx.homedelivery.business;

import java.util.Arrays;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Ruta {
    private String idRuta;
    private Visita[] visitas;
    private Promotor promotor;
    private String fechaProgramada;
    private String fechaCreacion;
    private String fechaUltimaModificacion;

    public Ruta() {
        this.idRuta = "";
        this.visitas = new Visita[0];
        this.promotor = new Promotor();
        this.fechaProgramada = "";
        this.fechaCreacion = "";
        this.fechaUltimaModificacion = "";
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public Visita[] getVisitas() {
        return visitas;
    }

    public void setVisitas(Visita[] visitas) {
        this.visitas = visitas;
    }

    public Promotor getPromotor() {
        return promotor;
    }

    public void setPromotor(Promotor promotor) {
        this.promotor = promotor;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ruta ruta = (Ruta) o;

        if (!fechaCreacion.equals(ruta.fechaCreacion)) return false;
        if (!fechaProgramada.equals(ruta.fechaProgramada)) return false;
        if (!fechaUltimaModificacion.equals(ruta.fechaUltimaModificacion)) return false;
        if (!idRuta.equals(ruta.idRuta)) return false;
        if (!promotor.equals(ruta.promotor)) return false;
        if (!Arrays.equals(visitas, ruta.visitas)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRuta.hashCode();
        result = 31 * result + Arrays.hashCode(visitas);
        result = 31 * result + promotor.hashCode();
        result = 31 * result + fechaProgramada.hashCode();
        result = 31 * result + fechaCreacion.hashCode();
        result = 31 * result + fechaUltimaModificacion.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "idRuta='" + idRuta + '\'' +
                ", visitas=" + Arrays.toString(visitas) +
                ", promotor=" + promotor +
                ", fechaProgramada='" + fechaProgramada + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaUltimaModificacion='" + fechaUltimaModificacion + '\'' +
                '}';
    }
}

