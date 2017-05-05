package algroup.com.mx.homedelivery.business.rest.Get;

import algroup.com.mx.homedelivery.business.rest.Response;

/**
 * Created by devmac03 on 14/07/15.
 */
public class RutaOperadorResponse extends Response {

    private RutaOperador rutaOperador;

    public RutaOperadorResponse( ) {
        this.rutaOperador = rutaOperador;
    }

    public RutaOperador getRutaOperador() {
        return rutaOperador;
    }

    public void setRutaOperador(RutaOperador rutaOperador) {
        this.rutaOperador = rutaOperador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RutaOperadorResponse that = (RutaOperadorResponse) o;

        if (!rutaOperador.equals(that.rutaOperador)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + rutaOperador.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RutaOperadorResponse{" +
                "rutaOperador=" + rutaOperador +
                '}';
    }
}
