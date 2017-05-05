package algroup.com.mx.homedelivery.business.rest.Post;

/**
 * Created by devmac03 on 14/07/15.
 */
public class EntregaMedico {

    private int      idVisitaOperador;
    private int      idEstatusEntrega;
    private int      idMotivoDescarte;
    private int      idMotivoUbicado;
    private String   codigoPaquete;
    private boolean  encontrado;
    private String   claveOperador;
    private String   latitudCheckIn;
    private String   longitudCheckIn;
    private String   latitudCierre;
    private String   longitudCierre;

    private String   firmaMedico;
    private String   correoMedico;
    private Boolean  noDioCorreo;
    private Boolean  actualizoInformacion;
    private String   comentarios;
    private String   motivoUbicadoOtro;
    private String   motivoDescarteOtro;

    //enviar dos fechas nuevas
    private String fechaCheckIn;
    private String fechaCierre;

    //Es autorizado
    private Boolean esAutorizado;
    private String fotoFrente;
    private String fotoAtras;


    public EntregaMedico(  ) {
        this.idVisitaOperador = 0;
        this.idEstatusEntrega = 0;
        this.idMotivoDescarte = 0;
        this.idMotivoUbicado = 0;
        this.codigoPaquete = "";
        this.encontrado = false;
        this.claveOperador = "";
        this.firmaMedico = "";
        this.correoMedico = "";
        this.comentarios = "";
        this.motivoUbicadoOtro = "";
        this.motivoDescarteOtro = "";

        this.fechaCheckIn = "";
        this.fechaCierre = "";
        this.latitudCheckIn = "";
        this.longitudCheckIn = "";
        this.latitudCierre = "";
        this.longitudCierre = "";
        this.noDioCorreo = false;
        this.actualizoInformacion = false;
        //Elementos si es autorizado
        this.esAutorizado = false;
        this.fotoFrente = "";
        this.fotoAtras = "";
    }

    public int getIdVisitaOperador() {
        return idVisitaOperador;
    }

    public void setIdVisitaOperador(int idVisitaOperador) {
        this.idVisitaOperador = idVisitaOperador;
    }

    public int getIdEstatusEntrega() {
        return idEstatusEntrega;
    }

    public void setIdEstatusEntrega(int idEstatusEntrega) {
        this.idEstatusEntrega = idEstatusEntrega;
    }

    public int getIdMotivoDescarte() {
        return idMotivoDescarte;
    }

    public void setIdMotivoDescarte(int idMotivoDescarte) {
        this.idMotivoDescarte = idMotivoDescarte;
    }

    public int getIdMotivoUbicado() {
        return idMotivoUbicado;
    }

    public void setIdMotivoUbicado(int idMotivoUbicado) {
        this.idMotivoUbicado = idMotivoUbicado;
    }

    public String getCodigoPaquete() {
        return codigoPaquete;
    }

    public void setCodigoPaquete(String codigoPaquete) {
        this.codigoPaquete = codigoPaquete;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public String getClaveOperador() {
        return claveOperador;
    }

    public void setClaveOperador(String claveOperador) {
        this.claveOperador = claveOperador;
    }

    public String getLatitudCheckIn() {
        return latitudCheckIn;
    }

    public void setLatitudCheckIn(String latitudCheckIn) {
        this.latitudCheckIn = latitudCheckIn;
    }

    public String getLongitudCheckIn() {
        return longitudCheckIn;
    }

    public void setLongitudCheckIn(String longitudCheckIn) {
        this.longitudCheckIn = longitudCheckIn;
    }

    public String getLatitudCierre() {
        return latitudCierre;
    }

    public void setLatitudCierre(String latitudCierre) {
        this.latitudCierre = latitudCierre;
    }

    public String getLongitudCierre() {
        return longitudCierre;
    }

    public void setLongitudCierre(String longitudCierre) {
        this.longitudCierre = longitudCierre;
    }

    public String getFirmaMedico() {
        return firmaMedico;
    }

    public void setFirmaMedico(String firmaMedico) {
        this.firmaMedico = firmaMedico;
    }

    public String getCorreoMedico() {
        return correoMedico;
    }

    public void setCorreoMedico(String correoMedico) {
        this.correoMedico = correoMedico;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getMotivoUbicadoOtro() {
        return motivoUbicadoOtro;
    }

    public void setMotivoUbicadoOtro(String motivoUbicadoOtro) {
        this.motivoUbicadoOtro = motivoUbicadoOtro;
    }

    public String getMotivoDescarteOtro() {
        return motivoDescarteOtro;
    }

    public void setMotivoDescarteOtro(String motivoDescarteOtro) {
        this.motivoDescarteOtro = motivoDescarteOtro;
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

    public Boolean getNoDioCorreo() {
        return noDioCorreo;
    }

    public void setNoDioCorreo(Boolean noDioCorreo) {
        this.noDioCorreo = noDioCorreo;
    }

    public Boolean getActualizoInformacion() {
        return actualizoInformacion;
    }

    public void setActualizoInformacion(Boolean actualizoInformacion) {
        this.actualizoInformacion = actualizoInformacion;
    }

    public Boolean getEsAutorizado() {
        return esAutorizado;
    }

    public void setEsAutorizado(Boolean esAutorizado) {
        this.esAutorizado = esAutorizado;
    }

    public String getFotoFrente() {
        return fotoFrente;
    }

    public void setFotoFrente(String fotoFrente) {
        this.fotoFrente = fotoFrente;
    }

    public String getFotoAtras() {
        return fotoAtras;
    }

    public void setFotoAtras(String fotoAtras) {
        this.fotoAtras = fotoAtras;
    }

    @Override
    public String toString() {
        return "EntregaMedico{" +
                "idVisitaOperador=" + idVisitaOperador +
                ", idEstatusEntrega=" + idEstatusEntrega +
                ", idMotivoDescarte=" + idMotivoDescarte +
                ", idMotivoUbicado=" + idMotivoUbicado +
                ", codigoPaquete='" + codigoPaquete + '\'' +
                ", encontrado=" + encontrado +
                ", claveOperador='" + claveOperador + '\'' +
                ", latitudCheckIn='" + latitudCheckIn + '\'' +
                ", longitudCheckIn='" + longitudCheckIn + '\'' +
                ", latitudCierre='" + latitudCierre + '\'' +
                ", longitudCierre='" + longitudCierre + '\'' +
                ", firmaMedico='" + "LA FIRMA DE MEDICO AQUI" + '\'' +
                ", correoMedico='" + correoMedico + '\'' +
                ", noDioCorreo=" + noDioCorreo +
                ", actualizoInformacion=" + actualizoInformacion +
                ", comentarios='" + comentarios + '\'' +
                ", motivoUbicadoOtro='" + motivoUbicadoOtro + '\'' +
                ", motivoDescarteOtro='" + motivoDescarteOtro + '\'' +
                ", fechaCheckIn='" + fechaCheckIn + '\'' +
                ", fechaCierre='" + fechaCierre + '\'' +
                ", esAutorizado=" + esAutorizado +
                ", fotoFrente='" + "LA FOTO FRENTE AQUI" + '\'' +
                ", fotoAtras='" + "LA FOTO ATRAS AQUI" + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntregaMedico that = (EntregaMedico) o;

        if (idVisitaOperador != that.idVisitaOperador) return false;
        if (idEstatusEntrega != that.idEstatusEntrega) return false;
        if (idMotivoDescarte != that.idMotivoDescarte) return false;
        if (idMotivoUbicado != that.idMotivoUbicado) return false;
        if (encontrado != that.encontrado) return false;
        if (!codigoPaquete.equals(that.codigoPaquete)) return false;
        if (!claveOperador.equals(that.claveOperador)) return false;
        if (!latitudCheckIn.equals(that.latitudCheckIn)) return false;
        if (!longitudCheckIn.equals(that.longitudCheckIn)) return false;
        if (!latitudCierre.equals(that.latitudCierre)) return false;
        if (!longitudCierre.equals(that.longitudCierre)) return false;
        if (!firmaMedico.equals(that.firmaMedico)) return false;
        if (!correoMedico.equals(that.correoMedico)) return false;
        if (!noDioCorreo.equals(that.noDioCorreo)) return false;
        if (!actualizoInformacion.equals(that.actualizoInformacion)) return false;
        if (!comentarios.equals(that.comentarios)) return false;
        if (!motivoUbicadoOtro.equals(that.motivoUbicadoOtro)) return false;
        if (!motivoDescarteOtro.equals(that.motivoDescarteOtro)) return false;
        if (!fechaCheckIn.equals(that.fechaCheckIn)) return false;
        if (!fechaCierre.equals(that.fechaCierre)) return false;
        if (!esAutorizado.equals(that.esAutorizado)) return false;
        if (!fotoFrente.equals(that.fotoFrente)) return false;
        return fotoAtras.equals(that.fotoAtras);

    }

    @Override
    public int hashCode() {
        int result = idVisitaOperador;
        result = 31 * result + idEstatusEntrega;
        result = 31 * result + idMotivoDescarte;
        result = 31 * result + idMotivoUbicado;
        result = 31 * result + codigoPaquete.hashCode();
        result = 31 * result + (encontrado ? 1 : 0);
        result = 31 * result + claveOperador.hashCode();
        result = 31 * result + latitudCheckIn.hashCode();
        result = 31 * result + longitudCheckIn.hashCode();
        result = 31 * result + latitudCierre.hashCode();
        result = 31 * result + longitudCierre.hashCode();
        result = 31 * result + firmaMedico.hashCode();
        result = 31 * result + correoMedico.hashCode();
        result = 31 * result + noDioCorreo.hashCode();
        result = 31 * result + actualizoInformacion.hashCode();
        result = 31 * result + comentarios.hashCode();
        result = 31 * result + motivoUbicadoOtro.hashCode();
        result = 31 * result + motivoDescarteOtro.hashCode();
        result = 31 * result + fechaCheckIn.hashCode();
        result = 31 * result + fechaCierre.hashCode();
        result = 31 * result + esAutorizado.hashCode();
        result = 31 * result + fotoFrente.hashCode();
        result = 31 * result + fotoAtras.hashCode();
        return result;
    }
}
