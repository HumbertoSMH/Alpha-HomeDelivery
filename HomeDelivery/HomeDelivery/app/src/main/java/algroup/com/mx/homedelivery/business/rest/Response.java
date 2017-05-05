package algroup.com.mx.homedelivery.business.rest;

/**
 * Created by MAMM on 28/04/2015.
 */
public class Response {

    private boolean seEjecutoConExito;
    private String claveError;
    private String mensaje;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "seEjecutoConExito=" + seEjecutoConExito +
                ", claveError='" + claveError + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }

    public Response() {
        this.seEjecutoConExito = false;
        this.claveError = "";
        this.mensaje = "";
    }

    public boolean isSeEjecutoConExito() {
        return seEjecutoConExito;
    }

    public void setSeEjecutoConExito(boolean seEjecutoConExito) {
        this.seEjecutoConExito = seEjecutoConExito;
    }

    public String getClaveError() {
        return claveError;
    }

    public void setClaveError(String claveError) {
        this.claveError = claveError;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (seEjecutoConExito != response.seEjecutoConExito) return false;
        if (!claveError.equals(response.claveError)) return false;
        if (!mensaje.equals(response.mensaje)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (seEjecutoConExito ? 1 : 0);
        result = 31 * result + claveError.hashCode();
        result = 31 * result + mensaje.hashCode();
        return result;
    }
}
