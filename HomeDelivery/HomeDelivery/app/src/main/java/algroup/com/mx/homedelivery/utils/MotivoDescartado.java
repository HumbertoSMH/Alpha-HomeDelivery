package algroup.com.mx.homedelivery.utils;

/**
 * Created by devmac03 on 06/07/15.
 */
public class MotivoDescartado {
    private int idMotivoDescartado;
    private String descripcionMotivoDescartado;

    public MotivoDescartado() {
        this.idMotivoDescartado = -1;
        this.descripcionMotivoDescartado = "";
    }

    public int getIdMotivoDescartado() {
        return idMotivoDescartado;
    }

    public void setIdMotivoDescartado(int idMotivoDescartado) {
        this.idMotivoDescartado = idMotivoDescartado;
    }

    @Override
    public String toString() {
        return "MotivoDescartado{" +
                "idMotivoDescartado=" + idMotivoDescartado +
                ", descripcionMotivoDescartado='" + descripcionMotivoDescartado + '\'' +
                '}';
    }

    public String getDescripcionMotivoDescartado() {
        return descripcionMotivoDescartado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotivoDescartado that = (MotivoDescartado) o;

        if (idMotivoDescartado != that.idMotivoDescartado) return false;
        if (descripcionMotivoDescartado != null ? !descripcionMotivoDescartado.equals(that.descripcionMotivoDescartado) : that.descripcionMotivoDescartado != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMotivoDescartado;
        result = 31 * result + (descripcionMotivoDescartado != null ? descripcionMotivoDescartado.hashCode() : 0);
        return result;
    }

    public void setDescripcionMotivoDescartado(String descripcionMotivoDescartado) {
        this.descripcionMotivoDescartado = descripcionMotivoDescartado;
    }

}
