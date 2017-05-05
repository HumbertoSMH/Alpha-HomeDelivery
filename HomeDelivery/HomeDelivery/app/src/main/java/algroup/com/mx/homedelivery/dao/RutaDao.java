package algroup.com.mx.homedelivery.dao;

import algroup.com.mx.homedelivery.business.Ruta;

/**
 * Created by devmac02 on 09/07/15.
 */
public interface RutaDao {

    public void insertRuta( Ruta ruta );
    public Ruta getRutaById( int idRuta);
    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor );
    public long updateRuta( Ruta ruta);
    public long deleteRutaById( int idRuta);
    public long deleteRutaAnterior( int idRutaActual );

}
