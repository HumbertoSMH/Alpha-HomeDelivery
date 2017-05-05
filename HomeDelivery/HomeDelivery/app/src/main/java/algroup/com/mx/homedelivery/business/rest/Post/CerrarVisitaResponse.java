package algroup.com.mx.homedelivery.business.rest.Post;

import algroup.com.mx.homedelivery.business.rest.Response;

/**
 * Created by devmac03 on 15/07/15.
 */
public class CerrarVisitaResponse  {
    private Response cerrarEntregaResult;

    public CerrarVisitaResponse() {
        this.cerrarEntregaResult = new Response();
    }

    public Response getCerrarEntregaResult() {
        return cerrarEntregaResult;
    }

    public void setCerrarEntregaResult(Response cerrarEntregaResult) {
        this.cerrarEntregaResult = cerrarEntregaResult;
    }

    @Override
    public String toString() {
        return "CerrarVisitaResponse{" +
                "cerrarEntregaResult=" + cerrarEntregaResult +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CerrarVisitaResponse that = (CerrarVisitaResponse) o;

        if (!cerrarEntregaResult.equals(that.cerrarEntregaResult)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cerrarEntregaResult.hashCode();
    }
}
