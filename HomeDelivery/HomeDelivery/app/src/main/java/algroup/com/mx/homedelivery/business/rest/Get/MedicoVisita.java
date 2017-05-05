package algroup.com.mx.homedelivery.business.rest.Get;

/**
 * Created by devmac03 on 14/07/15.
 */
public class MedicoVisita {
    private String codigoMedico;
    private String coordenadas;
    private String correo;
    private String direccion;
    private String especialidadDescripcion;
    private int idEspecialidad;
    private int idParrilla;
    private String descripcionParrillaLimpia;     //Nuevo
    private String  portafolioDescripcion;
    private String nombreCompleto;
    private String nombreAutorizado;   //Nueva implementacion 1.3.1
    private String fechaCheckIn;
    private String fechaCierre;


    public MedicoVisita(  ) {
        this.codigoMedico = "";
        this.direccion = "";
        this.especialidadDescripcion = "";
        this.idEspecialidad = 0;
        this.nombreCompleto = "";
        this.nombreAutorizado = "";
        this.coordenadas = "";
        this.correo = "";
        this.idParrilla = 0;
        this.descripcionParrillaLimpia = "";     //Nuevo
        this.portafolioDescripcion = "";
        this.fechaCheckIn = "";
        this.fechaCierre = "";

    }

    public String getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(String codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEspecialidadDescripcion() {
        return especialidadDescripcion;
    }

    public void setEspecialidadDescripcion(String especialidadDescripcion) {
        this.especialidadDescripcion = especialidadDescripcion;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdParrilla() {
        return idParrilla;
    }

    public void setIdParrilla(int idParrilla) {
        this.idParrilla = idParrilla;
    }

    public String getPortafolioDescripcion() {
        return portafolioDescripcion;
    }

    public void setPortafolioDescripcion(String portafolioDescripcion) {
        this.portafolioDescripcion = portafolioDescripcion;
    }

    public String getDescripcionParrillaLimpia() {
        return descripcionParrillaLimpia;
    }

    public void setDescripcionParrillaLimpia(String descripcionParrillaLimpia) {
        this.descripcionParrillaLimpia = descripcionParrillaLimpia;
    }

    public String getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(String fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void setNombreAutorizado(String nombreAutorizado) {
        this.nombreAutorizado = nombreAutorizado;
    }

    public String getNombreAutorizado() {
        return nombreAutorizado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicoVisita that = (MedicoVisita) o;

        if (idEspecialidad != that.idEspecialidad) return false;
        if (idParrilla != that.idParrilla) return false;
        if (!codigoMedico.equals(that.codigoMedico)) return false;
        if (!coordenadas.equals(that.coordenadas)) return false;
        if (!correo.equals(that.correo)) return false;
        if (!direccion.equals(that.direccion)) return false;
        if (!especialidadDescripcion.equals(that.especialidadDescripcion)) return false;
        if (!descripcionParrillaLimpia.equals(that.descripcionParrillaLimpia)) return false;
        if (!portafolioDescripcion.equals(that.portafolioDescripcion)) return false;
        if (!nombreCompleto.equals(that.nombreCompleto)) return false;
        if (!nombreAutorizado.equals(that.nombreAutorizado)) return false;
        if (!fechaCheckIn.equals(that.fechaCheckIn)) return false;
        return fechaCierre.equals(that.fechaCierre);

    }

    @Override
    public int hashCode() {
        int result = codigoMedico.hashCode();
        result = 31 * result + coordenadas.hashCode();
        result = 31 * result + correo.hashCode();
        result = 31 * result + direccion.hashCode();
        result = 31 * result + especialidadDescripcion.hashCode();
        result = 31 * result + idEspecialidad;
        result = 31 * result + idParrilla;
        result = 31 * result + descripcionParrillaLimpia.hashCode();
        result = 31 * result + portafolioDescripcion.hashCode();
        result = 31 * result + nombreCompleto.hashCode();
        result = 31 * result + nombreAutorizado.hashCode();
        result = 31 * result + fechaCheckIn.hashCode();
        result = 31 * result + fechaCierre.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MedicoVisita{" +
                "codigoMedico='" + codigoMedico + '\'' +
                ", coordenadas='" + coordenadas + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", especialidadDescripcion='" + especialidadDescripcion + '\'' +
                ", idEspecialidad=" + idEspecialidad +
                ", idParrilla=" + idParrilla +
                ", descripcionParrillaLimpia='" + descripcionParrillaLimpia + '\'' +
                ", portafolioDescripcion='" + portafolioDescripcion + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", nombreAutorizado='" + nombreAutorizado + '\'' +
                ", fechaCheckIn='" + fechaCheckIn + '\'' +
                ", fechaCierre='" + fechaCierre + '\'' +
                '}';
    }
}
