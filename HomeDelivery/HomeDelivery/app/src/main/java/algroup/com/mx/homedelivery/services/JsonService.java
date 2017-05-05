package algroup.com.mx.homedelivery.services;

import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoDescarteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoUbicadoResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoPaqueteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.LoginResponse;
import algroup.com.mx.homedelivery.business.rest.Get.RutaOperadorResponse;
import algroup.com.mx.homedelivery.business.rest.Post.CerrarVisitaResponse;
import algroup.com.mx.homedelivery.business.rest.Post.EntregaMedico;
import algroup.com.mx.homedelivery.business.rest.Response;


/**
 * Created by MAMM on 28/04/2015.
 */
public interface JsonService {

    public LoginResponse realizarPeticionLogin(String usuario, String password);

    public RutaOperadorResponse realizarPeticionObtenerRutaOperador(String usuario);
    public CatalogoMotivoDescarteResponse realizarPeticionObtenerMotivoDescarte( );
    public CatalogoMotivoUbicadoResponse realizarPeticionObtenerMotivoUbicado( );
    public CerrarVisitaResponse realizarPostCerrarEntrega(EntregaMedico entregaMedico );

    public CatalogoPaqueteResponse realizarPeticionObtenerCatalogoPaquetes(String usuario);
}
