package algroup.com.mx.homedelivery.services;

import algroup.com.mx.homedelivery.business.Ruta;

/**
 * Created by devmac03 on 12/06/15.
 */
public interface RutaService {
    public Ruta cargarRuta(Ruta ruta);
    public Ruta refrescarRutaDesdeBase(Ruta rutaReferencia);
    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor );
}
