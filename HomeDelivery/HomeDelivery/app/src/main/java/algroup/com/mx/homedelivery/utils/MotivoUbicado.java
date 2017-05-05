package algroup.com.mx.homedelivery.utils;

/**
 * Created by devmac03 on 26/05/15.
 */
public class MotivoUbicado {
    private int idMotivoRetiro;
    private String descripcionMotivoRetiro;

    public MotivoUbicado() {
        this.idMotivoRetiro = -1;
        this.descripcionMotivoRetiro = "";
    }

    public int getIdMotivoRetiro() {
        return idMotivoRetiro;
    }

    public void setIdMotivoRetiro(int idMotivoRetiro) {
        this.idMotivoRetiro = idMotivoRetiro;
    }

    public String getDescripcionMotivoRetiro() {
        return descripcionMotivoRetiro;
    }

    public void setDescripcionMotivoRetiro(String descripcionMotivoRetiro) {
        this.descripcionMotivoRetiro = descripcionMotivoRetiro;
    }

    @Override
    public String toString() {
        return "MotivoRetiro{" +
                "idMotivoRetiro=" + idMotivoRetiro +
                ", descripcionMotivoRetiro='" + descripcionMotivoRetiro + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotivoUbicado that = (MotivoUbicado) o;

        if (idMotivoRetiro != that.idMotivoRetiro) return false;
        if (!descripcionMotivoRetiro.equals(that.descripcionMotivoRetiro)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMotivoRetiro;
        result = 31 * result + descripcionMotivoRetiro.hashCode();
        return result;
    }
}
