package algroup.com.mx.homedelivery.services.impl;

import android.content.Context;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Promotor;
import algroup.com.mx.homedelivery.business.rest.Get.LoginResponse;
import algroup.com.mx.homedelivery.business.utils.Json;
import algroup.com.mx.homedelivery.services.CatalogosServices;
import algroup.com.mx.homedelivery.services.JsonService;
import algroup.com.mx.homedelivery.services.PromotorService;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.LogUtil;

/**
 * Created by MAMM on 16/04/15.
 */
public class PromotorServiceImpl implements PromotorService {
    private static final String CLASSNAME = PromotorServiceImpl.class.getSimpleName();

    private static PromotorService promotorService;
    private VisitaService visitaService;
    private CatalogosServices catalogoService;
    //private ProductoService productoService;
    //private EncuestaService encuestaService;
    private Context context;
    private Promotor promotorActual;
    private JsonService jsonService;

    private PromotorServiceImpl(Context context) {
        LogUtil.logInfo(CLASSNAME, " Se crea el singleton");
        this.context = context;
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.catalogoService = CatalogosServicesImpl.getSingleton();
        this.jsonService = JsonServiceImpl.getSingleton();
    }

    public static PromotorService getSingleton(  ) {
        if (promotorService == null) {
            promotorService = new PromotorServiceImpl( HomeDeliveryApp.getCurrentApplication() );
        }
        return promotorService;
    }


    @Override
    public void realizarLoggin(String usuario, String password) {
        Promotor promotor = null;
        String responseDoLogin = null;
        LoginResponse loginResponse = null;
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            promotor = this.crearPromotorMock( usuario , password );
            this.visitaService.recuperarRuta( promotor );
            //this.visitaService.recuperarMotivosDeRetiro();
        }else{
            loginResponse = jsonService.realizarPeticionLogin(usuario, password);
            if ( !loginResponse.isSeEjecutoConExito() ){
                Json.solicitarMsgError(loginResponse, Json.ERROR_JSON.LOGIN);

                promotor = null;
            } else {
                promotor = this.crearPromotor(usuario, password);
                loginResponse = this.cargaDatosIniciales( promotor );
                if( !loginResponse.isSeEjecutoConExito() ){
                    promotor = null;
                }
            }
        }
        this.promotorActual = promotor;
        LogUtil.printLog( CLASSNAME , ".realizarLoggin:" + promotor);
        LogUtil.printLog( CLASSNAME , ".responseDoLogin:" + responseDoLogin);
        //return promotor;
    }

    private LoginResponse cargaDatosIniciales(Promotor promotor) {
        LoginResponse response = LoginResponse.generarResponseExitoso();
        String mensajeError = null;
        this.visitaService.recuperarRuta( promotor );
        mensajeError = Json.getMsgError(Json.ERROR_JSON.LOGIN);
        if( mensajeError == null ){
//            this.productoService.actualizarCatalogoProductos(this.visitaService.getCadenasEnRuta());
//            mensajeError = Json.getMsgError(Json.ERROR_JSON.LOGIN);
//            if( mensajeError == null){
//                this.encuestaService.actualizarMapaEncuesta( this.visitaService.getRutaActual().getIdRuta() );
//                mensajeError = Json.getMsgError(Json.ERROR_JSON.LOGIN);
//            }if( mensajeError == null ){
//                this.visitaService.recuperarMotivosDeRetiro();
//                mensajeError = Json.getMsgError(Json.ERROR_JSON.LOGIN);
//            }
        }
        if( mensajeError != null ){
            response.setMensaje( mensajeError );
            response.setClaveError( "1999");
            response.setSeEjecutoConExito( false );
            Json.solicitarMsgError( response, Json.ERROR_JSON.LOGIN );
        }
        return response;
    }

    private Promotor crearPromotor(String usuario , String password) {
        Promotor p = new Promotor();
        p.setUsuario( usuario );
        p.setPassword(password);
        p.setClavePromotor(usuario);
        return p;
    }

    private Promotor crearPromotorMock( String usuario , String password ) {
        Promotor promotor = new Promotor();
        promotor.getPersona().setNombre( usuario );
        promotor.getPersona().setApellidoPaterno("");
        promotor.getPersona().setApellidoMaterno("");
        promotor.setClavePromotor( usuario );
        promotor.setUsuario( usuario );
        promotor.setPassword( password );
        return promotor;
    }

    public Promotor getPromotorActual() {
        return promotorActual;
    }


    public void prepararDatosPromotorMovilDesdeInformacionEnBase( String user , String pass ){

        this.promotorActual = this.crearPromotor(user, pass);
        this.visitaService.recuperarRutaDesdeBase( user, pass );
        this.catalogoService.recuperarCatalogosDesdeBase();

    }
}