package algroup.com.mx.homedelivery.business;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Medicamento {
    private String idMedicamento;
    private String nombreMedicamento;
    private int cantidad;
    private String lote;
    private String fechaCaducidad;

    public Medicamento( ) {
        this.idMedicamento = "";
        this.nombreMedicamento = "";
        this.cantidad = 0;
        this.lote = "";
        this.fechaCaducidad = "";
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "idMedicamento='" + idMedicamento + '\'' +
                ", nombreMedicamento='" + nombreMedicamento + '\'' +
                ", cantidad=" + cantidad +
                ", lote='" + lote + '\'' +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                '}';
    }
}
