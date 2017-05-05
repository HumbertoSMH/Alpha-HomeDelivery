package algroup.com.mx.homedelivery.business.rest.Get;

/**
 * Created by devmac02 on 27/07/15.
 */
public class MedicamentoRest {

    private int cantidad;
    private String descripcion;
    private String fechaCaducidadTexto;
    private  String lote;

    public MedicamentoRest() {
        this.cantidad = 0;
        this.descripcion = "";
        this.fechaCaducidadTexto = "";
        this.lote = "";
    }

    public int getCantidad() {return cantidad;}

    public void setCantidad(int cantidad) { this.cantidad = cantidad;}

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getFechaCaducidadTexto() {return fechaCaducidadTexto;}

    public void setFechaCaducidadTexto(String fechaCaducidadTexto) {this.fechaCaducidadTexto = fechaCaducidadTexto;}

    public String getLote() {return lote;}

    public void setLote(String lote) {this.lote = lote;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicamentoRest that = (MedicamentoRest) o;

        if (cantidad != that.cantidad) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null)
            return false;
        if (fechaCaducidadTexto != null ? !fechaCaducidadTexto.equals(that.fechaCaducidadTexto) : that.fechaCaducidadTexto != null)
            return false;
        return !(lote != null ? !lote.equals(that.lote) : that.lote != null);

    }

    @Override
    public int hashCode() {
        int result = cantidad;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (fechaCaducidadTexto != null ? fechaCaducidadTexto.hashCode() : 0);
        result = 31 * result + (lote != null ? lote.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MedicamentoRest{" +
                "cantidad=" + cantidad +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCaducidadTexto='" + fechaCaducidadTexto + '\'' +
                ", lote='" + lote + '\'' +
                '}';
    }
}
