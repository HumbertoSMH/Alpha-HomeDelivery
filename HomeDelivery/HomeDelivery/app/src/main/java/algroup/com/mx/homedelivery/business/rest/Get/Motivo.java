package algroup.com.mx.homedelivery.business.rest.Get;

/**
 * Created by devmac03 on 26/05/15.
 */
public class Motivo {
    private int idDetalle;
    private String descripcion;

    public Motivo() {
        this.idDetalle = -1;
        this.descripcion = "";
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Motivo motivo = (Motivo) o;

        if (idDetalle != motivo.idDetalle) return false;
        if (!descripcion.equals(motivo.descripcion)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDetalle;
        result = 31 * result + descripcion.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Motivo{" +
                "idDetalle=" + idDetalle +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
