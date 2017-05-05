package algroup.com.mx.homedelivery.utils;

public class ValidadorUIMensajes {

    private String mensaje;
    private boolean correcto;
    private boolean elementoModificado;

    public ValidadorUIMensajes() {
        super();
        this.armarCamposValidatorSinErrores();
    }

    private void armarCamposValidatorSinErrores() {
        this.mensaje = "";
        this.correcto = true;
        this.elementoModificado = false;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public boolean isElementoModificado() {
        return elementoModificado;
    }

    public void setElementoModificado(boolean elementoModificado) {
        this.elementoModificado = elementoModificado;
    }

    @Override
    public String toString() {
        return "ValidadorUIMensajes{" +
                "mensaje='" + mensaje + '\'' +
                ", correcto=" + correcto +
                ", elementoModificado=" + elementoModificado +
                '}';
    }
}
