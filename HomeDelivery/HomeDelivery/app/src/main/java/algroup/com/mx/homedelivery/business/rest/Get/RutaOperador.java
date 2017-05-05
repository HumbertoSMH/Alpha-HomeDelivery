package algroup.com.mx.homedelivery.business.rest.Get;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by devmac03 on 14/07/15.
 */
public class RutaOperador {
    private String fechaCreacion;
    private String fechaProgramada;
    private String fechaUltimaModificacion;
    private int idRuta;
    private List<VisitaRest> visitas;

    public RutaOperador( ) {
        this.fechaCreacion = "";
        this.fechaProgramada = "";
        this.fechaUltimaModificacion = "";
        this.idRuta = 0;
        this.visitas = Collections.EMPTY_LIST;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public List<VisitaRest> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<VisitaRest> visitas) {
        this.visitas = visitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RutaOperador that = (RutaOperador) o;

        if (idRuta != that.idRuta) return false;
        if (!fechaCreacion.equals(that.fechaCreacion)) return false;
        if (!fechaProgramada.equals(that.fechaProgramada)) return false;
        if (!fechaUltimaModificacion.equals(that.fechaUltimaModificacion)) return false;
        if (!visitas.equals(that.visitas)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fechaCreacion.hashCode();
        result = 31 * result + fechaProgramada.hashCode();
        result = 31 * result + fechaUltimaModificacion.hashCode();
        result = 31 * result + idRuta;
        result = 31 * result + visitas.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RutaOperador{" +
                "fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaProgramada='" + fechaProgramada + '\'' +
                ", fechaUltimaModificacion='" + fechaUltimaModificacion + '\'' +
                ", idRuta=" + idRuta +
                ", visitas=" + visitas +
                '}';
    }
}
