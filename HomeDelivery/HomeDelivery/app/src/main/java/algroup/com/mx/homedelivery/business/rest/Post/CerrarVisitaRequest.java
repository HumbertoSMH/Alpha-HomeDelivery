package algroup.com.mx.homedelivery.business.rest.Post;

/**
 * Created by devmac03 on 14/07/15.
 */
public class CerrarVisitaRequest {

    private EntregaMedico entregaMedico;

    public CerrarVisitaRequest( ) {
        this.entregaMedico = new EntregaMedico();
    }

    public EntregaMedico getEntregaMedico() {
        return entregaMedico;
    }

    public void setEntregaMedico(EntregaMedico entregaMedico) {
        this.entregaMedico = entregaMedico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CerrarVisitaRequest that = (CerrarVisitaRequest) o;

        if (!entregaMedico.equals(that.entregaMedico)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return entregaMedico.hashCode();
    }

    @Override
    public String toString() {
        return "CerrarVisitaRequest{" +
                "entregaMedico=" + entregaMedico +
                '}';
    }

}
