package algroup.com.mx.homedelivery.business.rest.Get;

/**
 * Created by devmac03 on 14/07/15.
 */
public class VisitaRest {
    private int idVisitaOperador;
    private int idEstatus;
    private MedicoVisita medicoVisita;

    public VisitaRest( ) {
        this.idVisitaOperador = 0;
        this.idEstatus = 0;
        this.medicoVisita = new MedicoVisita();
    }

    public int getIdVisitaOperador() {
        return idVisitaOperador;
    }

    public void setIdVisitaOperador(int idVisitaOperador) {
        this.idVisitaOperador = idVisitaOperador;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public MedicoVisita getMedicoVisita() {
        return medicoVisita;
    }

    public void setMedicoVisita(MedicoVisita medicoVisita) {
        this.medicoVisita = medicoVisita;
    }



    @Override
    public String toString() {
        return "VisitaRest{" +
                "idVisitaOperador=" + idVisitaOperador +
                ", idEstatus=" + idEstatus +
                ", medicoVisita=" + medicoVisita +
                '}';
    }


}
