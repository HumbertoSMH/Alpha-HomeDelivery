package algroup.com.mx.homedelivery.business;

import java.util.Arrays;

import algroup.com.mx.homedelivery.business.utils.EstatusVisita;
import algroup.com.mx.homedelivery.business.utils.RespuestaBinaria;
import algroup.com.mx.homedelivery.business.utils.UtilLocation;
import algroup.com.mx.homedelivery.utils.Const;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Visita {
    private String idVisita;
    private EstatusVisita estatusVisita;
    private String codigoMedico;
    private String nombreMedico;
    private int idEspecialidadMedico;
    private String especialidadMedico;
    private String direccionMedico;
    private int idParrilla;
    private String desParrilla;             //Nuevo
    private String portafolio;              //Nuevo
    private String coordenadasMedico;       //Nuevo
    private String fechaCheckIn;
    private String fechaCierre;
    private byte[] firmaMedico;
    private RespuestaBinaria noDioCorreo;
    private RespuestaBinaria actualizoInformacion;
    private String emailMedico;
    private String comentarios;
  //Ya no se despliegan en la app
    private int idDescarte;
    private String descripcionDesacrteOtro;
    private int idUbicado;
    private String descripcionUbicadoOtro;

    private String codigoPaquete;
    private RespuestaBinaria paqueteLocalizado;
    private Const.FlujoDeCierre flujoDeCierre;
    private UtilLocation location;
    private UtilLocation locationCheckIn;
    private Medicamento[] medicamentos;

    //Bloque para controlar si firma persona atutorizada  1.3.1
    private String nombreAutorizado;   //nuevo 1.3.1
    private RespuestaBinaria esAutorizado;
    private byte[] fotoFrenteBase64;
    private byte[] fotoAtrasBase64;

    public Visita( ) {
        this.idVisita = "";

        this.codigoMedico = "";
        this.nombreMedico = "";
        this.nombreAutorizado = "";
        this.especialidadMedico = "";
        this.idEspecialidadMedico = 0;
        this.direccionMedico = "";
        this.estatusVisita = EstatusVisita.EN_RUTA;
        this.fechaCheckIn = "";
        this.fechaCierre = "";
        this.firmaMedico = new byte[0];
        this.emailMedico = "";
        this.location = new UtilLocation();
        this.locationCheckIn = new UtilLocation();
        this.comentarios = "";
        this.medicamentos = new Medicamento[0];
        this.idDescarte = 0;
        this.descripcionDesacrteOtro = "";
        this.idUbicado = 0;
        this.descripcionUbicadoOtro = "";
        this.codigoPaquete = "";
        this.idParrilla = 0;
        this.desParrilla="";            //Nuevo
        this.portafolio = "";
        this.coordenadasMedico = "";
        this.paqueteLocalizado = RespuestaBinaria.NO;
        this.flujoDeCierre = Const.FlujoDeCierre.FLUJO_ENTREGADO;
        this.noDioCorreo = RespuestaBinaria.NO;
        this.actualizoInformacion = RespuestaBinaria.NO;
        this.esAutorizado = RespuestaBinaria.NO;
        this.fotoFrenteBase64 = new byte[0];
        this.fotoAtrasBase64 = new byte[0];
    }

    public String getCodigoMedico() {
        return codigoMedico;
    }

    public Const.FlujoDeCierre getFlujoDeCierre() {
        return flujoDeCierre;
    }

    public void setFlujoDeCierre(Const.FlujoDeCierre flujoDeCierre) {
        this.flujoDeCierre = flujoDeCierre;
    }

    public void setCodigoMedico(String codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getEspecialidadMedico() {
        return especialidadMedico;
    }

    public void setEspecialidadMedico(String especialidadMedico) {
        this.especialidadMedico = especialidadMedico;
    }

    public String getDireccionMedico() {
        return direccionMedico;
    }

    public void setDireccionMedico(String direccionMedico) {
        this.direccionMedico = direccionMedico;
    }

    public EstatusVisita getEstatusVisita() {
        return estatusVisita;
    }

    public void setEstatusVisita(EstatusVisita estatusVisita) {
        this.estatusVisita = estatusVisita;
    }

    public int getIdEspecialidadMedico() {
        return idEspecialidadMedico;
    }

    public void setIdEspecialidadMedico(int idEspecialidadMedico) {
        this.idEspecialidadMedico = idEspecialidadMedico;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public byte[] getFirmaMedico() {
        return firmaMedico;
    }

    public void setFirmaMedico(byte[] firmaMedico) {
        this.firmaMedico = firmaMedico;
    }

    public String getEmailMedico() {
        return emailMedico;
    }

    public void setEmailMedico(String emailMedico) {
        this.emailMedico = emailMedico;
    }

    public UtilLocation getLocation() {
        return location;
    }

    public void setLocation(UtilLocation location) {
        this.location = location;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getIdParrilla() {
        return idParrilla;
    }

    public void setIdParrilla(int idParrilla) {
        this.idParrilla = idParrilla;
    }

    public Medicamento[] getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(Medicamento[] medicamentos) {
        this.medicamentos = medicamentos;
    }

    public int getIdDescarte() {
        return idDescarte;
    }

    public void setIdDescarte(int idDescarte) {
        this.idDescarte = idDescarte;
    }

    public String getDescripcionDesacrteOtro() {
        return descripcionDesacrteOtro;
    }

    public void setDescripcionDesacrteOtro(String descripcionDesacrteOtro) {
        this.descripcionDesacrteOtro = descripcionDesacrteOtro;
    }

    public int getIdUbicado() {
        return idUbicado;
    }

    public void setIdUbicado(int idUbicado) {
        this.idUbicado = idUbicado;
    }

    public String getDescripcionUbicadoOtro() {
        return descripcionUbicadoOtro;
    }

    public void setDescripcionUbicadoOtro(String descripcionUbicadoOtro) {
        this.descripcionUbicadoOtro = descripcionUbicadoOtro;
    }

    public String getCodigoPaquete() {
        return codigoPaquete;
    }

    public void setCodigoPaquete(String codigoPaquete) {
        this.codigoPaquete = codigoPaquete;
    }

    public String getPortafolio() {
        return portafolio;
    }

    public void setPortafolio(String portafolio) {
        this.portafolio = portafolio;
    }

    public String getCoordenadasMedico() {
        return coordenadasMedico;
    }

    public void setCoordenadasMedico(String coordenadasMedico) {
        this.coordenadasMedico = coordenadasMedico;
    }

    public RespuestaBinaria getPaqueteLocalizado() {
        return paqueteLocalizado;
    }

    public void setPaqueteLocalizado(RespuestaBinaria paqueteLocalizado) {
        this.paqueteLocalizado = paqueteLocalizado;
    }


    public String getDesParrilla() {
        return desParrilla;
    }

    public void setDesParrilla(String desParrilla) {
        this.desParrilla = desParrilla;
    }

    public String getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(String fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public UtilLocation getLocationCheckIn() {
        return locationCheckIn;
    }

    public void setLocationCheckIn(UtilLocation locationCheckIn) {
        this.locationCheckIn = locationCheckIn;
    }

    public RespuestaBinaria getNoDioCorreo() {
        return noDioCorreo;
    }

    public void setNoDioCorreo(RespuestaBinaria noDioCorreo) {
        this.noDioCorreo = noDioCorreo;
    }

    public RespuestaBinaria getActualizoInformacion() {
        return actualizoInformacion;
    }

    public void setActualizoInformacion(RespuestaBinaria actualizoInformacion) {
        this.actualizoInformacion = actualizoInformacion;
    }

    public String getNombreAutorizado() {
        return nombreAutorizado;
    }

    public void setNombreAutorizado(String nombreAutorizado) {
        this.nombreAutorizado = nombreAutorizado;
    }

    public RespuestaBinaria getEsAutorizado() {
        return esAutorizado;
    }

    public void setEsAutorizado(RespuestaBinaria esAutorizado) {
        this.esAutorizado = esAutorizado;
    }

    public byte[] getFotoFrenteBase64() {
        return fotoFrenteBase64;
    }

    public void setFotoFrenteBase64(byte[] fotoFrenteBase64) {
        this.fotoFrenteBase64 = fotoFrenteBase64;
    }

    public byte[] getFotoAtrasBase64() {
        return fotoAtrasBase64;
    }

    public void setFotoAtrasBase64(byte[] fotoAtrasBase64) {
        this.fotoAtrasBase64 = fotoAtrasBase64;
    }

    @Override
    public String toString() {
        return "Visita{" +
                "idVisita='" + idVisita + '\'' +
                ", estatusVisita=" + estatusVisita +
                ", codigoMedico='" + codigoMedico + '\'' +
                ", nombreMedico='" + nombreMedico + '\'' +
                ", idEspecialidadMedico=" + idEspecialidadMedico +
                ", especialidadMedico='" + especialidadMedico + '\'' +
                ", direccionMedico='" + direccionMedico + '\'' +
                ", idParrilla=" + idParrilla +
                ", desParrilla='" + desParrilla + '\'' +
                ", portafolio='" + portafolio + '\'' +
                ", coordenadasMedico='" + coordenadasMedico + '\'' +
                ", fechaCheckIn='" + fechaCheckIn + '\'' +
                ", fechaCierre='" + fechaCierre + '\'' +
                ", firmaMedico=" + "AQUI VA LA FIRMA DE MEDICO" +
                ", noDioCorreo=" + noDioCorreo +
                ", actualizoInformacion=" + actualizoInformacion +
                ", emailMedico='" + emailMedico + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", idDescarte=" + idDescarte +
                ", descripcionDesacrteOtro='" + descripcionDesacrteOtro + '\'' +
                ", idUbicado=" + idUbicado +
                ", descripcionUbicadoOtro='" + descripcionUbicadoOtro + '\'' +
                ", codigoPaquete='" + codigoPaquete + '\'' +
                ", paqueteLocalizado=" + paqueteLocalizado +
                ", flujoDeCierre=" + flujoDeCierre +
                ", location=" + location +
                ", locationCheckIn=" + locationCheckIn +
                ", medicamentos=" + Arrays.toString(medicamentos) +
                ", nombreAutorizado='" + nombreAutorizado + '\'' +
                ", esAutorizado=" + esAutorizado +
                ", fotoFrenteBase64='" + "FOTO FRENTE BASE 64" + '\'' +
                ", fotoAtrasBase64='" + "FOTO ATRAS BASE 64" + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visita visita = (Visita) o;

        if (idEspecialidadMedico != visita.idEspecialidadMedico) return false;
        if (idParrilla != visita.idParrilla) return false;
        if (idDescarte != visita.idDescarte) return false;
        if (idUbicado != visita.idUbicado) return false;
        if (!idVisita.equals(visita.idVisita)) return false;
        if (estatusVisita != visita.estatusVisita) return false;
        if (!codigoMedico.equals(visita.codigoMedico)) return false;
        if (!nombreMedico.equals(visita.nombreMedico)) return false;
        if (!especialidadMedico.equals(visita.especialidadMedico)) return false;
        if (!direccionMedico.equals(visita.direccionMedico)) return false;
        if (!desParrilla.equals(visita.desParrilla)) return false;
        if (!portafolio.equals(visita.portafolio)) return false;
        if (!coordenadasMedico.equals(visita.coordenadasMedico)) return false;
        if (!fechaCheckIn.equals(visita.fechaCheckIn)) return false;
        if (!fechaCierre.equals(visita.fechaCierre)) return false;
        if (!Arrays.equals(firmaMedico, visita.firmaMedico)) return false;
        if (noDioCorreo != visita.noDioCorreo) return false;
        if (actualizoInformacion != visita.actualizoInformacion) return false;
        if (!emailMedico.equals(visita.emailMedico)) return false;
        if (!comentarios.equals(visita.comentarios)) return false;
        if (!descripcionDesacrteOtro.equals(visita.descripcionDesacrteOtro)) return false;
        if (!descripcionUbicadoOtro.equals(visita.descripcionUbicadoOtro)) return false;
        if (!codigoPaquete.equals(visita.codigoPaquete)) return false;
        if (paqueteLocalizado != visita.paqueteLocalizado) return false;
        if (flujoDeCierre != visita.flujoDeCierre) return false;
        if (!location.equals(visita.location)) return false;
        if (!locationCheckIn.equals(visita.locationCheckIn)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(medicamentos, visita.medicamentos)) return false;
        if (!nombreAutorizado.equals(visita.nombreAutorizado)) return false;
        if (esAutorizado != visita.esAutorizado) return false;
        if (!Arrays.equals(fotoFrenteBase64, visita.fotoFrenteBase64)) return false;
        return Arrays.equals(fotoAtrasBase64, visita.fotoAtrasBase64);

    }

    @Override
    public int hashCode() {
        int result = idVisita.hashCode();
        result = 31 * result + estatusVisita.hashCode();
        result = 31 * result + codigoMedico.hashCode();
        result = 31 * result + nombreMedico.hashCode();
        result = 31 * result + idEspecialidadMedico;
        result = 31 * result + especialidadMedico.hashCode();
        result = 31 * result + direccionMedico.hashCode();
        result = 31 * result + idParrilla;
        result = 31 * result + desParrilla.hashCode();
        result = 31 * result + portafolio.hashCode();
        result = 31 * result + coordenadasMedico.hashCode();
        result = 31 * result + fechaCheckIn.hashCode();
        result = 31 * result + fechaCierre.hashCode();
        result = 31 * result + Arrays.hashCode(firmaMedico);
        result = 31 * result + noDioCorreo.hashCode();
        result = 31 * result + actualizoInformacion.hashCode();
        result = 31 * result + emailMedico.hashCode();
        result = 31 * result + comentarios.hashCode();
        result = 31 * result + idDescarte;
        result = 31 * result + descripcionDesacrteOtro.hashCode();
        result = 31 * result + idUbicado;
        result = 31 * result + descripcionUbicadoOtro.hashCode();
        result = 31 * result + codigoPaquete.hashCode();
        result = 31 * result + paqueteLocalizado.hashCode();
        result = 31 * result + flujoDeCierre.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + locationCheckIn.hashCode();
        result = 31 * result + Arrays.hashCode(medicamentos);
        result = 31 * result + nombreAutorizado.hashCode();
        result = 31 * result + esAutorizado.hashCode();
        result = 31 * result + Arrays.hashCode(fotoFrenteBase64);
        result = 31 * result + Arrays.hashCode(fotoAtrasBase64);
        return result;
    }
}
