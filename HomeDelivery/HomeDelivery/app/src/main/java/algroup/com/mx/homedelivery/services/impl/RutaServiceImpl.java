package algroup.com.mx.homedelivery.services.impl;

import android.content.Context;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Promotor;
import algroup.com.mx.homedelivery.business.Ruta;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.utils.EstatusVisita;
import algroup.com.mx.homedelivery.dao.RutaDao;
import algroup.com.mx.homedelivery.dao.VisitaDao;
import algroup.com.mx.homedelivery.dao.impl.RutaDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.VisitaDaoImpl;
import algroup.com.mx.homedelivery.services.RutaService;

/**
 * Created by devmac03 on 12/06/15.
 */
public class RutaServiceImpl implements RutaService{

    private static RutaService rutaService;
    private RutaDao rutaDao;
    private VisitaDao visitaDao;
    private Context context;

    public RutaServiceImpl(Context context){
        this.context = context;
        this.rutaDao = RutaDaoImpl.getSingleton();
        this.visitaDao = VisitaDaoImpl.getSingleton();
    }

    public static RutaService getSingleton(){
        if( rutaService == null ){
            rutaService = new RutaServiceImpl( HomeDeliveryApp.getCurrentApplication() );
        }
        return rutaService;
    }

    @Override
    public Ruta cargarRuta(Ruta ruta) {
        int idRutaNueva = Integer.parseInt( ruta.getIdRuta() );
        Ruta rutaEnBase = this.rutaDao.getRutaById( idRutaNueva );
        if( rutaEnBase == null ){
            //Se carga la nueva ruta
            this.rutaDao.insertRuta( ruta );
        }else{
            //se actualiza la ruta
            this.rutaDao.updateRuta( ruta );
        }
        //Se cargan las visitas de la ruta
        Visita[] visitas = ruta.getVisitas();
        if( visitas != null ){
            for( Visita itemVisita : visitas ){
                Visita visitaEnBase = this.visitaDao.getVisitaById( Integer.parseInt( itemVisita.getIdVisita() ) );
                if( visitaEnBase == null ){
                    this.visitaDao.insertVisita( itemVisita , idRutaNueva );
                }else{
                    if( itemVisita.getEstatusVisita() == EstatusVisita.EN_RUTA ){  //si esta en RUTA solo se actualiza el id de ruta de la visita
                        this.visitaDao.updateIdRutaEnVisita(itemVisita, idRutaNueva);
                    }if( visitaEnBase.getEstatusVisita() == EstatusVisita.NO_GUARDADA ){ //En caso contrario se actualiza toda la visita
                        this.visitaDao.updateIdRutaEnVisita(itemVisita, idRutaNueva);
                    }else{
                        this.visitaDao.updateVisita( itemVisita, idRutaNueva );
                    }

                }
            }
        }

        //Se eliminan las visitas que no tienen ruta asociada y todas sus dependencias
        Integer[] visitasAEliminar = this.visitaDao.getIdVisitasQueNoSonDeRuta( idRutaNueva);
        for( Integer idVisita: visitasAEliminar ){
            this.visitaDao.deleteVisitaById( idVisita );
        }
        //TODO MAMM Pendiene eliminar Ruta anterior
        this.rutaDao.deleteRutaAnterior( idRutaNueva );
        return this.armarRutaDeBase( idRutaNueva , ruta.getPromotor());
    }

    private Ruta armarRutaDeBase(int idRutaNueva , Promotor promotor) {
        Ruta ruta = this.rutaDao.getRutaById( idRutaNueva );
        Visita[] visitas = this.visitaDao.getVisitasByIdRuta(idRutaNueva);
        ruta.setVisitas( visitas );
        ruta.setPromotor( promotor );
        return ruta;
    }


    public Ruta refrescarRutaDesdeBase( Ruta rutaReferencia ){
        int idRuta = Integer.parseInt( rutaReferencia.getIdRuta() );
        Ruta ruta = this.rutaDao.getRutaById( idRuta );
        Visita[] visitas = this.visitaDao.getVisitasByIdRuta(idRuta );
        ruta.setVisitas( visitas );
        ruta.setPromotor( rutaReferencia.getPromotor()  );
        return ruta;
    }

    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor ){
        return this.rutaDao.getRutaPorClaveYPasswordDePromotor( clavePromotor , passwordPromotor );
    }

}
