package algroup.com.mx.homedelivery.services;

import java.util.List;

import algroup.com.mx.homedelivery.business.Promotor;
import algroup.com.mx.homedelivery.business.Ruta;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.rest.Response;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;


/**
 * Created by MAMM on 19/04/15.
 */
public interface VisitaService {

    public void recuperarRuta(Promotor promotorLogged);
//    public void recuperarMotivosDeRetiro();
    public Ruta getRutaActual();


//    public Response realizarCheckIn(Visita visita);
//    public Response realizarCheckOut(Visita visita);
    public void registrarInicioDeVisita();
    public Response realizarCierreVisita( Visita visita );
//
//    public Visita recuperarVisitaPorIdVisita(String idVisita);
//    public List<MotivoUbicado> getCatalogoMotivoRetiro();
//
    public Visita getVisitaPorPosicionEnListaCompleta(int posicion);
    public Visita getVisitaPorIdVisita(String idVisita);
    public Visita getVisitaActual();

    public void actualizarVisitaActual();
    public void actualizarEstatusPaqueteEntregadoEnVisitaActual( );
    public boolean validarSiCodigoPaqueteNoFueAsignadoPreviamente( String codigoPaquete );

    public void recuperarRutaDesdeBase( String user , String pass );
}
