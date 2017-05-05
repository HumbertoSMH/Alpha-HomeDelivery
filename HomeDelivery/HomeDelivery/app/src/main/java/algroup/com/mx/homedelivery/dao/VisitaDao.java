package algroup.com.mx.homedelivery.dao;

import algroup.com.mx.homedelivery.business.Visita;

/**
 * Created by devmac02 on 09/07/15.
 */
public interface VisitaDao {

    public void insertVisita( Visita visita , int idRuta);
    public Visita getVisitaById(  Integer idVisita);
    public Visita[] getVisitasByIdRuta(  Integer idRuta );
    public Integer[] getIdVisitasQueNoSonDeRuta(  Integer idRuta);
    public long updateVisita( Visita visita , int idRuta);
    public long updateIdRutaEnVisita( Visita visita , int idRuta);
    public long deleteVisitaById(int idVisita);
}
