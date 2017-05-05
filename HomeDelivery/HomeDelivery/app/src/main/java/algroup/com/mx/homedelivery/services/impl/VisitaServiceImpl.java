package algroup.com.mx.homedelivery.services.impl;

import android.content.Context;

import java.util.Date;
import java.util.List;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Medicamento;
import algroup.com.mx.homedelivery.business.Promotor;
import algroup.com.mx.homedelivery.business.Ruta;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoDescarteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoUbicadoResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoPaqueteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.RutaOperador;
import algroup.com.mx.homedelivery.business.rest.Get.RutaOperadorResponse;
import algroup.com.mx.homedelivery.business.rest.Get.VisitaRest;
import algroup.com.mx.homedelivery.business.rest.Post.CerrarVisitaResponse;
import algroup.com.mx.homedelivery.business.rest.Post.EntregaMedico;
import algroup.com.mx.homedelivery.business.rest.Response;
import algroup.com.mx.homedelivery.business.utils.EstatusVisita;
import algroup.com.mx.homedelivery.business.utils.Json;
import algroup.com.mx.homedelivery.business.utils.RespuestaBinaria;
import algroup.com.mx.homedelivery.dao.MedicamentoDao;
import algroup.com.mx.homedelivery.dao.MotivoDescartadoDao;
import algroup.com.mx.homedelivery.dao.MotivoUbicadoDao;
import algroup.com.mx.homedelivery.dao.PaquetesEntregadosDao;
import algroup.com.mx.homedelivery.dao.RutaDao;
import algroup.com.mx.homedelivery.dao.VisitaDao;
import algroup.com.mx.homedelivery.dao.impl.MedicamentoDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.MotivoDescartadoDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.MotivoUbicadoDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.PaquetesEntregadosDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.RutaDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.VisitaDaoImpl;
import algroup.com.mx.homedelivery.services.CatalogosServices;
import algroup.com.mx.homedelivery.services.JsonService;
import algroup.com.mx.homedelivery.services.LocationService;
import algroup.com.mx.homedelivery.services.RutaService;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.MotivoDescartado;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;
import algroup.com.mx.homedelivery.utils.Util;
import algroup.com.mx.homedelivery.utils.ViewUtil;


/**
 * Created by MAMM on 19/04/15.
 */
public class VisitaServiceImpl implements VisitaService{
    private static final String CLASSNAME = VisitaServiceImpl.class.getSimpleName();

    private static VisitaService visitaService;
    private CatalogosServices catalogosServices;
    private LocationService locationService;
    private RutaService rutaService;
    private JsonService jsonService;

    private RutaDao rutaDao;
    private VisitaDao visitaDao;


    private Context context;
    private Ruta rutaActual;
    private Visita visitaActual;

    // Campos para probar Catalogos
    private MedicamentoDao medicamentoDao;
    private MotivoDescartadoDao motivoDescartadoDao;
    private MotivoUbicadoDao motivoUbicadoDao;
    private PaquetesEntregadosDao paquetesEntregadosDao;




    public VisitaServiceImpl(Context context){
        this.context = context;
        this.locationService = LocationServiceImpl.getSingleton();
        this.catalogosServices = CatalogosServicesImpl.getSingleton();
        this.jsonService = JsonServiceImpl.getSingleton();
        this.rutaService = RutaServiceImpl.getSingleton();
        this.rutaActual = new Ruta();


        this.rutaDao = RutaDaoImpl.getSingleton();
        this.visitaDao = VisitaDaoImpl.getSingleton();

        // Campos para probar Catalogos
        this.medicamentoDao = MedicamentoDaoImpl.getSingleton();
        this.motivoDescartadoDao = MotivoDescartadoDaoImpl.getSingleton();
        this.motivoUbicadoDao = MotivoUbicadoDaoImpl.getSingleton();
        this.paquetesEntregadosDao = PaquetesEntregadosDaoImpl.getSingleton();
    }

    public static VisitaService getSingleton(){
        if( visitaService == null ){
            visitaService = new VisitaServiceImpl( HomeDeliveryApp.getCurrentApplication() );
        }
        return visitaService;
    }



    public void recuperarRuta( Promotor promotor ) {
        LogUtil.printLog(CLASSNAME, "recuperarRuta promotor:" + promotor);
        Ruta ruta = null;

        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            ruta = this.prepararRutaMock( promotor );
            this.rutaActual = ruta;
            this.testDaoMock( ruta);
        }else{
            RutaOperadorResponse rutaOperadorResponse = jsonService.realizarPeticionObtenerRutaOperador(promotor.getUsuario());
            if ( !rutaOperadorResponse.isSeEjecutoConExito() ){
                Json.solicitarMsgError( rutaOperadorResponse, Json.ERROR_JSON.LOGIN );
            } else {
                ruta = parsearRuta( rutaOperadorResponse.getRutaOperador() , promotor );
                //INI MAMM siempre que se registre un usuario se actualizan las rutas en base.
                this.rutaActual = this.rutaService.cargarRuta( ruta );
                this.cargarCatalogos( promotor );
                //END MAMM
            }
        }
    }
//
//
//
//
    public void cargarCatalogos( Promotor promotor ) {
        LogUtil.printLog(CLASSNAME, "cargarCatalogos:");
        Ruta ruta = null;

        if (Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK) {

        } else {
            CatalogoMotivoDescarteResponse catalogoMotivoDescarteResponse = this.catalogosServices.recuperarMotivosDescartadosDesdeWeb();
            if (!catalogoMotivoDescarteResponse.isSeEjecutoConExito()) {
                Json.solicitarMsgError(catalogoMotivoDescarteResponse, Json.ERROR_JSON.LOGIN);
            } else {
                CatalogoMotivoUbicadoResponse catalogoMotivoUbicadoResponse = this.catalogosServices.recuperarMotivosUbicadosDesdeWeb();
                if (!catalogoMotivoUbicadoResponse.isSeEjecutoConExito()) {
                    Json.solicitarMsgError(catalogoMotivoUbicadoResponse, Json.ERROR_JSON.LOGIN);
                } else {
                    //TODO MAMM CargarCatalogoDePaquetes
                    //Si llega a esta línea y no hay errores cargar los catalogos en base de datos
                    CatalogoPaqueteResponse catalogoPaqueteResponse = this.catalogosServices.recuperarCatalogoPaquetePorRutaDesdeWeb(promotor.getUsuario());
                    if (!catalogoPaqueteResponse.isSeEjecutoConExito()) {
                        Json.solicitarMsgError(catalogoPaqueteResponse, Json.ERROR_JSON.LOGIN);
                    }else{
                        //Actualizar los catalogos
                        this.catalogosServices.actualizarCatalogosEnBase();
                    }
                }
            }
        }
    }

//    private List<MotivoRetiro> prepararCatalogoMotivoRetiroMock() {
//        List<MotivoRetiro> catMotivoRetiro = new ArrayList<MotivoRetiro>();
//        for( int j = 0; j < 5 ; j++ ){
//            MotivoRetiro motivo = new MotivoRetiro();
//            motivo.setIdMotivoRetiro( j + 1 );
//            motivo.setDescripcionMotivoDescartado("Motivo de retiro " + (j + 1));
//            catMotivoRetiro.add( motivo );
//        }
//        MotivoRetiro motivoOtro = new MotivoRetiro();
//        motivoOtro.setIdMotivoRetiro(Const.ID_MOTIVO_RETIRO_OTRO);
//        motivoOtro.setDescripcionMotivoDescartado( "Otro" );
//        catMotivoRetiro.add( motivoOtro );
//        return catMotivoRetiro;
//    }
//
//    private boolean actualizarRutaSiExisteCambio(Ruta ruta , Promotor promotorLogueado) {
//        boolean actualizarRuta = false;
//        Promotor promotorActual = (PromotorServiceImpl.getSingleton()).getPromotorActual();
//        if( promotorActual == null  ){
//            LogUtil.printLog( CLASSNAME , "No se tiene registros previos de promotor, actualizarRuta = true" );
//            actualizarRuta = true;
//        }else
//        if( promotorActual.getClavePromotor().equals( promotorLogueado.getClavePromotor() ) == false ){
//            LogUtil.printLog( CLASSNAME , "Cambia de promotor registrado, actualizarRuta = true" );
//            actualizarRuta = true;
//        }else{
//            LogUtil.printLog( CLASSNAME , "Se vuelve a loggear el promotor, se valida para actualizar la ruta" );
//            if( this.rutaActual.getIdRuta().equals( ruta.getIdRuta()) == true ){
//                if( this.rutaActual.getFechaUltimaModificacion().equals( ruta.getFechaUltimaModificacion()) == false){
//                    LogUtil.printLog( CLASSNAME , "La ruta del día sufrio un cambio, actualizarRuta = true" );
//                    actualizarRuta = true;
//                }else{  /* SI NO HAY CAMBIO EN LA RUTA NO SE REALIZA LA ACTUALIZACION*/
//                    LogUtil.printLog( CLASSNAME , "La ruta del día no ha sufrido cambio, actualizarRuta = false" );
//                }
//            }else{
//                    LogUtil.printLog( CLASSNAME , "Cambia el idRuta, actualizarRuta = true" );
//                    actualizarRuta = true;
//            }
//        }
//        return actualizarRuta;
//    }
//
//    private void armarMapaDeCadenasEnRuta() {
//        Visita[] visitas = rutaActual.getVisitas();
//        this.cadenasAplicadasEnRuta = new HashSet< Cadena>();
//        for( Visita itemVisita : visitas ){
//            this.cadenasAplicadasEnRuta.add( itemVisita.getCadena() );
//        }
//    }

    private Ruta parsearRuta( RutaOperador rutaOperador , Promotor promotor){

        Ruta ruta = new Ruta();
        ruta.setIdRuta( "" + rutaOperador.getIdRuta() );
        ruta.setFechaCreacion(rutaOperador.getFechaCreacion());
        ruta.setFechaUltimaModificacion(rutaOperador.getFechaUltimaModificacion());
        ruta.setFechaProgramada(rutaOperador.getFechaProgramada());

        ruta.setPromotor( promotor );
        if( rutaOperador.getVisitas().size() > 0 ){
            ruta.setVisitas( this.parsearVisitasDesdeResponse( rutaOperador.getVisitas() ) );
        }
        return ruta;
    }

    private Visita[] parsearVisitasDesdeResponse(List<VisitaRest> visitas) {
        int size = visitas.size();
        Visita[] arrayVisitas = new Visita[ size ];
        for( int j = 0; j<size;j++){
            Visita v = new Visita();
            VisitaRest vResponse = visitas.get( j );
            v.setIdVisita( "" + vResponse.getIdVisitaOperador() );
            v.setCodigoMedico(vResponse.getMedicoVisita().getCodigoMedico() ); ;
            v.setNombreMedico(vResponse.getMedicoVisita().getNombreCompleto() ) ;
            v.setEspecialidadMedico(vResponse.getMedicoVisita().getEspecialidadDescripcion() ) ;
            v.setIdEspecialidadMedico(vResponse.getMedicoVisita().getIdEspecialidad() );
            v.setDireccionMedico( vResponse.getMedicoVisita().getDireccion() );
            v.setEstatusVisita( this.obtenerEstatusEnMovil(vResponse.getIdEstatus()) );

            //nuevos atributos
            v.setCoordenadasMedico( vResponse.getMedicoVisita().getCoordenadas());
            v.setEmailMedico( vResponse.getMedicoVisita().getCorreo() );
            v.setIdParrilla(vResponse.getMedicoVisita().getIdParrilla());
            v.setDesParrilla(vResponse.getMedicoVisita().getDescripcionParrillaLimpia());
            v.setPortafolio( vResponse.getMedicoVisita().getPortafolioDescripcion() );

            //persona autorizada
            v.setNombreAutorizado(vResponse.getMedicoVisita().getNombreAutorizado() ); ;

            arrayVisitas[j] = v;
        }
        return arrayVisitas;
    }

    private EstatusVisita obtenerEstatusEnMovil(int idEstatus) {
        EstatusVisita estatusVisita = null;
        if( idEstatus > 0 ){
            switch ( idEstatus ) {
                case EstatusVisita.EN_RUTA_ID_WEB:
                    estatusVisita = EstatusVisita.EN_RUTA;
                    break;
                case EstatusVisita.ENTREGADO_ID_WEB:
                    estatusVisita = EstatusVisita.ENTREGADA;
                    break;
                case EstatusVisita.UBICADO_ID_WEB:
                    estatusVisita = EstatusVisita.UBICADO;
                    break;
                case EstatusVisita.DESCARTADO_ID_WEB:
                    estatusVisita = EstatusVisita.DESCARTADO;
                    break;
                case EstatusVisita.CANCELADA_ID_WEB:
                    estatusVisita = EstatusVisita.CANCELADA;
                    break;
                case EstatusVisita.NO_VISITADA_ID_WEB:
                    estatusVisita = EstatusVisita.NO_VISITADA;
                    break;
                default:
                    break;
            }
        }
        return estatusVisita;
    }
//
//    private Cadena parsearCadenaDesdeVisitaTiendaResponse(TiendaVisita tiendaResponse) {
//        Cadena c = new Cadena();
//        CadenaTienda ctResponse = tiendaResponse.getCadenaTienda();
//        c.setIdCadena( "" + ctResponse.getIdCadena() );
//        c.setNombreCadena(ctResponse.getNombre());
//        return c;
//    }
//
//    private Tienda parsearTiendaDesdeVisitaTiendaResponse( TiendaVisita tiendaResponse ){
//        Tienda tienda = new Tienda();
//        tienda.setIdTienda( "" + tiendaResponse.getIdTienda() );
//        tienda.setNombreTienda( tiendaResponse.getNombre() );
//        tienda.setTelefono(  tiendaResponse.getTelefono() );
//
//        Direccion dir = new Direccion();
//        dir.setCalle( tiendaResponse.getCalle() );
//        dir.setNumeroExterior(tiendaResponse.getNumeroExterior());
//        dir.setNumeroInterior(tiendaResponse.getNumeroInterior());
//        dir.setCodigoPostal(tiendaResponse.getCodigoPostal());
//        dir.setColonia(tiendaResponse.getColonia());
//        dir.setDelegacion(tiendaResponse.getDelegacionMunicpio());
//        dir.setEstado(tiendaResponse.getEstado());
//        dir.setPais("México");
//        dir.setReferencia(tiendaResponse.getReferencia());
//        tienda.setDireccion( dir );
//
//        UtilLocation loc = new UtilLocation();
//        loc.setLatitud( "" + tiendaResponse.getLatitud() );
//        loc.setLongitud("" + tiendaResponse.getLongitud());
//        tienda.setLocation(loc);
//
//        return tienda;
//    }
//
//
    private Ruta prepararRutaMock(Promotor promotor) {
        LogUtil.printLog( CLASSNAME , "Inicia prepararRutaMock" );
        Ruta ruta = new Ruta();
        ruta.setPromotor( promotor );
        ruta.setFechaCreacion(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaProgramada(Util.getDateTimeFromMilis(new Date().getTime() + 1500000));
        ruta.setFechaUltimaModificacion(Util.getDateTimeFromMilis(new Date().getTime() + 3000000));
        ruta.setIdRuta("100001");
        Visita[] visitas = this.crearVisitasMock();
        ruta.setVisitas( visitas );
        LogUtil.printLog( CLASSNAME , " Ruta:"  + ruta);
        return ruta;
    }

    private Visita[] crearVisitasMock(){
        int numeroVisitas = 10;
        Visita[] visitas = new Visita[ numeroVisitas ];
        for( int j = 0 ; j < numeroVisitas ; j++ ){
            Visita visita = new Visita();
            visita.setIdVisita( "" + j );
            if(j < 7 ){
                if(j == 1 ){ visita.setEstatusVisita(EstatusVisita.EN_RUTA);}
                if(j == 2 ){ visita.setEstatusVisita(EstatusVisita.ENTREGADA);}
                if(j == 3 ){ visita.setEstatusVisita(EstatusVisita.UBICADO);}
                if(j == 4 ){ visita.setEstatusVisita(EstatusVisita.DESCARTADO);}
                if(j == 5 ){ visita.setEstatusVisita(EstatusVisita.CANCELADA);}
                if(j == 6 ){ visita.setEstatusVisita(EstatusVisita.NO_VISITADA);}
            }else{
                visita.setEstatusVisita(EstatusVisita.EN_RUTA);
            }
            Medicamento[] medicamentos = this.crearMedicamentosMock( "" + (j + 1) );
            visita.setMedicamentos(medicamentos);
            visita.setNombreMedico("Fernando Savater Hijo " + (j + 1));
            visita.setDireccionMedico("Avenida " + (j + 1) + " Colonia Campestre Delgacion Miguel Hidalgo, C.P. 095732 Mexico DF");
            visita.setEspecialidadMedico("Medicina General y Alguna Otra especialidad");
            visita.setPortafolio("Partos naturales y cesarea");
            visita.setCoordenadasMedico("Bloque 16, Cuadrante 11D");
            if( j%2==0){
                visita.setEmailMedico( "mail" + j + "@mail.com" );
                visita.setNombreAutorizado( "Hermano medico " + j );
            }
            visitas[j] = visita;
        }
        return visitas;
    }


    private Medicamento[] crearMedicamentosMock( String idVisita ){
        int size= 12;
        Medicamento[] medicamentos = new Medicamento[size];
        for( int j = 0; j < size ; j++){
            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento( "" + (j+1) );
            medicamento.setNombreMedicamento( "Medicamento de alto impacto 239grm" + (j+1) );
            medicamento.setCantidad( (j+1) + 3 );
            medicamento.setLote( "10000000000" + (j+1));
            medicamento.setFechaCaducidad( Util.getDateFromMilis( new Date().getTime() ) );
            medicamentos[j] = medicamento;
        }
        return medicamentos;
    }


//    private RevisionProducto[] crearProductosMock(String idVisita ) {
//        int numeroProductoInicial = 3;
//        RevisionProducto[] rpArray = new RevisionProducto[ numeroProductoInicial ];
//        for( int j = 1 ; j <= numeroProductoInicial ; j++ ){
//            RevisionProducto rp = new RevisionProducto();
//            Producto p = new Producto();
//            p.setCodigo("Prod 0" + j);
//            p.setDescripcion( "Producto especial básico " + j );
//            p.setPrecioBase(j * 100);
//            rp.setProducto( p );
//            rp.setExistencia( 5 );
//            rp.setPrecioEnTienda( j * 100 );
//            rp.setExhibicionAdicional( RespuestaBinaria.SI );
//            rp.setNumeroFrente( 4 );
//            rpArray[j - 1] = rp;
//        }
//
//        return rpArray;
//    }
//
//
//    private RevisionFoto[] crearRervisionFotosMock(String idVisita ) {
//        int numeroFotosIniciales = 3;
//        RevisionFoto[] rfArray = new RevisionFoto[ numeroFotosIniciales ];
//        for( int j = 1 ; j <= numeroFotosIniciales ; j++ ){
//            RevisionFoto rf = new RevisionFoto();
//            long milisec = j*5000;
//            rf.setIdFoto( Util.getDateTimeFromMilis_hastaSegundos( new Date().getTime() + milisec )  );
//            rf.setFechaCaptura( "2015/04/20 12:34" );
//            rf.setFoto(UtilsMock.getImageMock( this.context ) );
//            rfArray[j - 1] = rf;
//        }
//
//        return rfArray;
//    }
//
//    private EncuestaPersona[] crearEncuestaPersonaMock(String idEncuesta) {
//        Encuesta encuesta = this.encuestaService.recuperarEncuestaPorId(idEncuesta);
//        Pregunta[] preguntas = encuesta.getPreguntasEncuesta();
//
//        int numPersonasEncuestadas = 3;
//        EncuestaPersona[] encuestaPersonas = new EncuestaPersona[ numPersonasEncuestadas ];
//
//        for( int j = 1 ; j <= numPersonasEncuestadas ; j++ ){
//            EncuestaPersona encuestaPersona = new EncuestaPersona();
//            PreguntaRespuesta[] preguntaRespuesta = new PreguntaRespuesta[ preguntas.length ];
//
//
//            for( int i = 0 ; i < preguntas.length ; i++ ){
//                PreguntaRespuesta pr = new PreguntaRespuesta();
//                pr.setPregunta( preguntas[i] );
//                pr.setRespuestaElegida( preguntas[i].getRespuestasPregunta()[0]);
//                preguntaRespuesta[ i ] = pr;
//            }
//
//            encuestaPersona.setPreguntaRespuesta( preguntaRespuesta );
//            Persona persona = new Persona();
//            persona.setNombre( "Persona " + j );
//            encuestaPersona.setPersona(persona);
//            encuestaPersonas[j - 1 ] = encuestaPersona;
//        }
//
//        return encuestaPersonas;
//    }
//
//    private Tienda crearTiendaMock( int item ){
//        Tienda tienda = new Tienda();
//        tienda.setIdTienda( "" + item );
//        tienda.setNombreTienda( "Comercializadora Suc. 0" + item );
//        tienda.setTelefono( "5512000" + item);
//
//        Direccion dir = new Direccion();
//        dir.setCalle( "Calle " + item );
//        dir.setNumeroExterior("10" + item);
//        dir.setNumeroInterior( "Interior 1" + item );
//        dir.setCodigoPostal( "0660" + item );
//        dir.setColonia( "Colonia "+ item );
//        dir.setDelegacion( "Cuauhtemoc" );
//        dir.setEstado( "Distrito Federal" );
//        dir.setPais( "México" );
//        dir.setReferencia( "Frente al oxxo #0" + item );
//        tienda.setDireccion( dir );
//
//        UtilLocation loc = new UtilLocation();
//        loc.setLatitud( "19.43260" );
//        loc.setLongitud("-99.13320");
//        tienda.setLocation(loc);
//
//        return tienda;
//    }

    public Ruta getRutaActual() {
        return rutaActual;
    }


//    public Response realizarCheckIn( Visita visita ){
//        Response response = null;
//        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
//        LogUtil.printLog( CLASSNAME , "realizarCheckIn visita:" + visita + ", Promotor:" + promotor );
//        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            visita.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
//            visita.setEstatusVisita( EstatusVisita.CHECK_IN );
//        }else{
//            CheckInTienda checkIn = new CheckInTienda();
//            checkIn.setIdTienda( Integer.parseInt( visita.getTienda().getIdTienda() ) );
//            checkIn.setIdVisita(Integer.parseInt(visita.getIdVisita()));
//            checkIn.setClavePromotor( promotor.getClavePromotor() );
//            checkIn.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
//            checkIn.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
//
//            Location locationActual = this.locationService.getLocation(); //Se recupera solo para pintar el toast
//            checkIn.setLatitud( "" + this.locationService.getLatitude() );
//            checkIn.setLongitud("" + this.locationService.getLongitude());
//
//
//
//            CheckInTiendaResponse CheckInResponse =  this.jsonService.realizarCheckinPost( checkIn );
//            if ( !CheckInResponse.getHacerCheckInTiendaResult().isSeEjecutoConExito() ){
//                Json.solicitarMsgError( CheckInResponse.getHacerCheckInTiendaResult(), Json.ERROR_JSON.CHECK_IN );
//                response =  CheckInResponse.getHacerCheckInTiendaResult();
//            } else {
//                visita.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
//                visita.setEstatusVisita( EstatusVisita.CHECK_IN );
//                this.visitaDao.updateVisita( visita , Integer.parseInt(this.rutaActual.getIdRuta()) );
//                this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );
//            }
//        }
//
//        return response;
//    }
//
//
//    public Response realizarCheckOut( Visita visita ){
//        LogUtil.printLog( CLASSNAME , "realizarCheckOut visita:" + visita  );
//        Response response = null;
//
//        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
//        CheckOutTienda checkout = new CheckOutTienda();
//
//        checkout.setClavePromotor( promotor.getClavePromotor() );
//        checkout.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
//        checkout.setIdVisita(Integer.parseInt(visita.getIdVisita()));
//
//        Location locationActual = this.locationService.getLocation(); //Se recupera solo para pintar el toast
//        checkout.setLatitud( "" + this.locationService.getLatitude() );
//        checkout.setLongitud("" + this.locationService.getLongitude());
//
//        checkout.setVisitaTienda( this.armarVisitaTienda( visita ) );
//
//
//        CheckOutTiendaResponse checkOutResponse = this.jsonService.realizarCheckOutPost(checkout);
//        if ( !checkOutResponse.getHacerCheckOutTiendaResult().isSeEjecutoConExito() ){
//            Json.solicitarMsgError( checkOutResponse.getHacerCheckOutTiendaResult(), Json.ERROR_JSON.CHECK_OUT );
//            response =  checkOutResponse.getHacerCheckOutTiendaResult();
//            visita.setFechaCheckout( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
//            visita.setEstatusVisita( EstatusVisita.CHECK_OUT_REQUEST);
//            this.actualizarVisitaActual();
//            this.recuperarRuta( promotor );
//        } else {
//            visita.setFechaCheckout( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
//            visita.setEstatusVisita( EstatusVisita.CHECK_OUT);
//            this.actualizarVisitaActual();
//            this.limpiarDatosEnMemoriaDeLaVisita(visita);
//            this.eliminarRegistrosEnTablasDeLaVisita( );
//            this.recuperarRuta( promotor );
//        }
//        return response;
//    }


    public void registrarInicioDeVisita(){
        this.visitaActual.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) );
        this.visitaActual.getLocationCheckIn().setLatitud("" + locationService.getLatitude()) ;
        this.visitaActual.getLocationCheckIn().setLongitud("" + locationService.getLongitude());
        this.actualizarVisitaActual();
    }

    public Response realizarCierreVisita(Visita visita ){
        LogUtil.printLog( CLASSNAME , "realizarCierreVisita visita:" + visita  );
        CerrarVisitaResponse response = null;

        //AJUSTE 20150807
        //Si se trata de una visita no enviada, se recupera su dirección de la que se tenia previamente guardada
        if( visita.getEstatusVisita() != EstatusVisita.NO_GUARDADA ){
            visita.getLocation().setLatitud("" + locationService.getLatitude()) ;
            visita.getLocation().setLongitud("" + locationService.getLongitude());
            visita.setFechaCierre( Util.getDateTimeFromMilis( new Date().getTime() ) );
        }

        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        EntregaMedico entregaMedico = new EntregaMedico();
        entregaMedico.setIdVisitaOperador(Integer.parseInt(visita.getIdVisita()));
//        entregaMedico.setIdEstatusEntrega(visita.getEstatusVisita().getIdEstatus());
        entregaMedico.setIdMotivoDescarte(visita.getIdDescarte());
        entregaMedico.setIdMotivoUbicado(visita.getIdUbicado());
        entregaMedico.setCodigoPaquete(visita.getCodigoPaquete());
        entregaMedico.setEncontrado(visita.getPaqueteLocalizado().isBoolRespuesta());
        entregaMedico.setClaveOperador(promotor.getClavePromotor());
        entregaMedico.setLatitudCierre(visita.getLocation().getLatitud());
        entregaMedico.setLongitudCierre(visita.getLocation().getLongitud());

        entregaMedico.setLatitudCheckIn(visita.getLocationCheckIn().getLatitud());
        entregaMedico.setLongitudCheckIn(visita.getLocationCheckIn().getLongitud());

        entregaMedico.setFirmaMedico((ViewUtil.obtenerStringBase64(visita.getFirmaMedico())));
        entregaMedico.setNoDioCorreo(visita.getNoDioCorreo().isBoolRespuesta());
        entregaMedico.setActualizoInformacion(visita.getActualizoInformacion().isBoolRespuesta());
        entregaMedico.setCorreoMedico(visita.getEmailMedico());
        entregaMedico.setComentarios(visita.getComentarios());
        entregaMedico.setMotivoUbicadoOtro(visita.getDescripcionUbicadoOtro());
        entregaMedico.setMotivoDescarteOtro(visita.getDescripcionDesacrteOtro());
        entregaMedico.setFechaCierre(visita.getFechaCierre());
        entregaMedico.setFechaCheckIn(visita.getFechaCheckIn());


        //Se registran los datos si es autorizado
        entregaMedico.setEsAutorizado(visita.getEsAutorizado().isBoolRespuesta());
        if(RespuestaBinaria.SI == visita.getEsAutorizado() ){
            entregaMedico.setFotoFrente( ViewUtil.obtenerStringBase64(visita.getFotoFrenteBase64()));
            entregaMedico.setFotoAtras( ViewUtil.obtenerStringBase64(visita.getFotoAtrasBase64() ) );
        }


        Const.FlujoDeCierre flujoCierre = visita.getFlujoDeCierre();
        switch ( flujoCierre.idFlujo ) {
            case Const.FLUJO_ENTREGADO_ID:
                entregaMedico.setIdEstatusEntrega( EstatusVisita.ENTREGADA.getIdEstatus() );
                entregaMedico.setIdMotivoUbicado( 0 ); //Se limpia el campo por si este llegara a estar habilitado
                break;
            case Const.FLUJO_UBICADO_ID:
                entregaMedico.setIdEstatusEntrega( EstatusVisita.UBICADO.getIdEstatus() );
                break;
            case Const.FLUJO_DESCARTADO_ID:
                entregaMedico.setIdEstatusEntrega( EstatusVisita.DESCARTADO.getIdEstatus() );
                break;
            default:
                //Some
                break;
        }

        response = this.jsonService.realizarPostCerrarEntrega( entregaMedico );
        if ( !response.getCerrarEntregaResult().isSeEjecutoConExito() ) {
            Json.solicitarMsgError(response.getCerrarEntregaResult(), Json.ERROR_JSON.CERRAR_VISITA);
            this.visitaActual.setEstatusVisita(EstatusVisita.NO_GUARDADA);
            this.actualizarVisitaActual();
        }else{
            entregaMedico.getIdEstatusEntrega();
            this.visitaActual.setEstatusVisita( EstatusVisita.getEstatusVisitaFromId( entregaMedico.getIdEstatusEntrega() ) );
            this.actualizarVisitaActual();
            this.recuperarRuta( promotor );
        }
        return response.getCerrarEntregaResult();
    }







//    private VisitaTienda armarVisitaTienda(Visita visita) {
//        VisitaTienda vt = new VisitaTienda();
//        vt.setComentarios( visita.getComentarios() );
//        vt.setIdTienda(Integer.parseInt(visita.getTienda().getIdTienda()));
//        vt.setIdVisita( Integer.parseInt(visita.getIdVisita()) );
//        vt.setNombreJefeDepartamento( visita.getGerente().getNombre() );
//        vt.setFirmaJefeDepartamento(ViewUtil.obtenerStringBase64 (visita.getFirmaGerente() ) );
//        List<EntrevistaEncuesta> entrevista = this.armaListaEntrevistaEncuesta( visita );
//        vt.setEntrevistasEncuesta( entrevista );
//
//        List<String> fotos = this.armarListFotosCapturadas( visita );
//        vt.setFotosTienda( fotos );
//
//        List<ProductoTienda> listaProductos = this.armarListaProductos( visita );
//        vt.setProductosTienda( listaProductos );
//
//        //INI MAMM motivo retiro
//        vt.setIdMotivoRetiro( visita.getIdMotivoRetiro() );
//        vt.setDescripcionMotivoDescartado( visita.getDescripcionMotivoDescartado() );
//        //END MAMM motivo retiro
//
//        return vt;
//    }
//
//    private List<ProductoTienda> armarListaProductos(Visita visita) {
//        List<ProductoTienda> listaProductos = new ArrayList<ProductoTienda>();
//        RevisionProducto[] revProductoArray = visita.getRevisionProductos();
//        for( RevisionProducto itemRevProd : revProductoArray ){
//            ProductoTienda pt = new ProductoTienda();
//            pt.setCodigo( itemRevProd.getProducto().getCodigo() );
//            pt.setDescripcion( itemRevProd.getProducto().getDescripcion() );
//            pt.setPrecioBase( itemRevProd.getProducto().getPrecioBase() );
//            pt.setExhibicionAdicional(itemRevProd.getExhibicionAdicional().isBoolRespuesta() );
//            pt.setExistencia( itemRevProd.getExistencia() );
//            pt.setNumeroFrente( itemRevProd.getNumeroFrente() );
//            pt.setPrecioTienda( itemRevProd.getPrecioEnTienda());
//            listaProductos.add( pt );
//        }
//        return listaProductos;
//    }
//
//    private List<String> armarListFotosCapturadas(Visita visita) {
//        List<String> fotos = new ArrayList<String>();
//        RevisionFoto[] revfotosArray = visita.getRevisionFoto();
//        for( RevisionFoto itemFoto : revfotosArray ){
//            fotos.add( ViewUtil.obtenerStringBase64( itemFoto.getFoto() ) );
//        }
//        return fotos;
//    }
//
//
//
//    private List<EntrevistaEncuesta> armaListaEntrevistaEncuesta(Visita visita ) {
//        EncuestaPersona[] encuestaPersona = visita.getEncuestaPersonas();
//        List<EntrevistaEncuesta> eeList = new ArrayList<EntrevistaEncuesta>();
//        for(EncuestaPersona itemEncuestaPersona : encuestaPersona ){
//            EntrevistaEncuesta ee = new EntrevistaEncuesta();
//            ee.setIdEncuesta( Integer.parseInt( visita.getIdEncuesta() )  );
//            PreguntaRespuesta[] pregRespArray = itemEncuestaPersona.getPreguntaRespuesta();
//            List<DetalleRespuesta> detRespList = new ArrayList<DetalleRespuesta>();
//            for( PreguntaRespuesta itemPregResp : pregRespArray){
//                DetalleRespuesta detResp = new DetalleRespuesta();
//                detResp.setIdPregunta( Integer.parseInt( itemPregResp.getPregunta().getIdPregunta() ) );
//                detResp.setIdRespuestaSeleccionada(Integer.parseInt(itemPregResp.getRespuestaElegida().getIdRespuesta()));
//                detRespList.add( detResp );
//            }
//            ee.setDetalleRespuestas( detRespList );
//            eeList.add( ee );
//        }
//        return eeList;
//    }
//
//
//    public Visita recuperarVisitaPorIdVisita(String idVisita){
//        Visita[] visitas = this.rutaActual.getVisitas();
//        Visita visitaBuscada = null;
//        for( Visita item : visitas ){
//            if(item.getIdVisita().equals( idVisita )  ){
//                visitaBuscada = item;
//            }
//        }
//        LogUtil.printLog( CLASSNAME , "recuperarVisitaPorIdVisita: idVisita:" + idVisita + ", visita:" +  visitaBuscada );
//        return visitaBuscada;
//    }
//
//    public Set<Cadena> getCadenasEnRuta(){
//        return this.cadenasAplicadasEnRuta;
//    }
//
//    public List<MotivoRetiro> getCatalogoMotivoRetiro(){
//        return this.catalogoMotivoRetiro;
//    }
//
//
//    public void agregarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual){
//        int  idVisita = Integer.parseInt(visitaActual.getIdVisita()) ;
//        this.productoDao.insertProducto( revisionProductoActual , idVisita );
//        visitaActual.setRevisionProductos( this.productoDao.getProductosByIdVisita( idVisita ) );
//    }
//
    @Override
    public Visita getVisitaPorPosicionEnListaCompleta(int posicion) {
        Visita visitaMemoria = this.visitaService.getRutaActual().getVisitas()[ posicion ];
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            //No se define nada para el mock
            this.visitaActual = visitaMemoria;
        }else{
            visitaActual =  this.visitaDao.getVisitaById( Integer.parseInt( visitaMemoria.getIdVisita() ) );
        }

        return visitaActual;
    }

    public Visita getVisitaPorIdVisita(String idVisita){
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            Visita[] visitas = this.visitaService.getRutaActual().getVisitas();
            for(  Visita itemVisita : visitas ){
                if( itemVisita.getIdVisita().equals( idVisita ) ) {
                    visitaActual =  itemVisita;
                    break;
                }
            }
        }else{
            visitaActual =  this.visitaDao.getVisitaById( Integer.parseInt( idVisita ) );
        }
        return visitaActual;
    }


    public Visita getVisitaActual() {
        return visitaActual;
    }


    @Override
    public void actualizarVisitaActual( ){
        int idRutaActual = Integer.parseInt( rutaActual.getIdRuta() );
        this.visitaDao.updateVisita( visitaActual , idRutaActual );
    }


    @Override
    public void actualizarEstatusPaqueteEntregadoEnVisitaActual( ){
        String codigoPaquete = visitaActual.getCodigoPaquete();
        this.paquetesEntregadosDao.actualizarPaqueteEntregadoAEntregado(codigoPaquete);
    }


    public boolean validarSiCodigoPaqueteNoFueAsignadoPreviamente( String codigoPaquete ){
        boolean asignadoPreviamente = this.paquetesEntregadosDao.getEstatusEntregadoDePaquete( codigoPaquete );
        return asignadoPreviamente;
    }

//
//
//    @Override
//    public void eliminarRevisionProductoDeVisita(RevisionProducto revisionProductoActual) {
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.productoDao.deleteProductoById( revisionProductoActual.getProducto().getCodigo() , idVisita );
//        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
//    }
//
//    public void actualizarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual ){
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.productoDao.updateProducto( revisionProductoActual , idVisita );
//        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
//    }
//
//    public void guardarRevisionFoto(RevisionFoto revFoto){
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.fotografiaDao.insertFotografia( revFoto , idVisita );
//        visitaActual.setRevisionFoto(this.fotografiaDao.getRevisionFotoByIdVisita( idVisita));
//    }
//
//    public void guardarEncuesta( EncuestaPersona encuestaPersona){
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.encuestaDao.insertEncuesta( encuestaPersona , idVisita );
//        this.visitaActual.setEncuestaPersonas( this.encuestaDao.getEncuestasByIdVisita( idVisita ) );
//    }
//
//
//    @Override
//    public void eliminarRevisionFotografia(  String idRevisionFoto  ) {
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.fotografiaDao.deleteFotoById( idRevisionFoto  , idVisita );
//        visitaActual.setRevisionFoto( this.fotografiaDao.getRevisionFotoByIdVisita(idVisita) ); ;
//    }
//
    private void testDaoMock(Ruta ruta) {
        this.rutaDao.insertRuta( ruta );

        Ruta rutaTemp = this.rutaDao.getRutaById(Integer.parseInt(ruta.getIdRuta()));
        LogUtil.printLog( CLASSNAME , "Se recupera ruta de base:" + rutaTemp );

        if( rutaTemp.equals(ruta) ){
            LogUtil.printLog( CLASSNAME , "Se inserta y se recupera la ruta correctamente");
        }else{
            LogUtil.printLog( CLASSNAME , "No se obtiene la misma ruta de la base de datos");
        }

        Visita[] visitas = ruta.getVisitas();
        for(Visita itemVisita : visitas ){
            this.visitaDao.insertVisita( itemVisita , Integer.parseInt( ruta.getIdRuta() ) );
        }
        Visita[] visitasArray = this.visitaDao.getVisitasByIdRuta( Integer.parseInt( ruta.getIdRuta() ) );
        LogUtil.printLog(CLASSNAME, "Se recupera visitasArray de base:" + visitasArray);

        //Prueba Catálogos
        String codigoPaquete = "ID_MOCK_PAQUETE";
        List<Medicamento> codigoPaqueteTest = this.catalogosServices.getPaquetePorCodigo( codigoPaquete );

        for( Medicamento item : codigoPaqueteTest ){
            this.medicamentoDao.insertMedicamento( item , codigoPaquete );
        }
        // Medicamentos
        List<Medicamento> medicamentosGetTest = this.medicamentoDao.getMedicamentoPorCodigoPaquete(codigoPaquete);
        LogUtil.printLog( CLASSNAME , "Se recupera Medicamentos de Catálogo:" + medicamentosGetTest );

        long medicamentosResultTest = this.medicamentoDao.deleteMedicamento();
        LogUtil.printLog( CLASSNAME , "Se eliminan Medicamentos de Catálogo:" + medicamentosResultTest );

        List<Medicamento> medicamentosDeleteTest = this.medicamentoDao.getMedicamentoPorCodigoPaquete(codigoPaquete);
        LogUtil.printLog( CLASSNAME , "Se verifican exitencia de Medicamentos de Catálogo:" + medicamentosDeleteTest );

        //MotivoDescartado
        List<MotivoDescartado> motivoDescartadoTest = this.catalogosServices.getCatalogoMotivoDescartado();

        for(MotivoDescartado itemMotDes : motivoDescartadoTest ){
            this.motivoDescartadoDao.insertMotivoDescartado(itemMotDes);
        }

        List<MotivoDescartado> motivoDescartadosGetTest = this.motivoDescartadoDao.getMotivoDescartado();
        LogUtil.printLog( CLASSNAME , "Se recupera Motivo Descartado de Catálogo:" + motivoDescartadosGetTest );

        long motivoDescartadoResultTest = this.motivoDescartadoDao.deleteMotivoDescartado();
        LogUtil.printLog( CLASSNAME , "Se eliminan Motivo Descartado de Catálogo:" + motivoDescartadoResultTest );


        List<MotivoDescartado> motivoDescartadoDeleteTest = this.motivoDescartadoDao.getMotivoDescartado();
        LogUtil.printLog( CLASSNAME , "Se recupera Motivo Descartado de Catálogo:" + motivoDescartadoDeleteTest );

        //MotivoUbicado
        List<MotivoUbicado> motivoUbicadoTest = this.catalogosServices.getCatalogoMotivoUbicado();

        for(MotivoUbicado itemMotUbi : motivoUbicadoTest ){
            this.motivoUbicadoDao.insertMotivoUbicado(itemMotUbi);
        }

        List<MotivoUbicado> motivoUbicadoGetTest = this.motivoUbicadoDao.getMotivoUbicado();
        LogUtil.printLog( CLASSNAME , "Se recupera Motivo Descartado de Catálogo:" + motivoUbicadoGetTest );

        long motivoUbicadoResultTest = this.motivoUbicadoDao.deleteMotivoUbicado();
        LogUtil.printLog( CLASSNAME , "Se eliminan Motivo Descartado de Catálogo:" + motivoUbicadoResultTest );


        List<MotivoUbicado> motivoUbicadoDeleteTest = this.motivoUbicadoDao.getMotivoUbicado();
        LogUtil.printLog( CLASSNAME , "Se recupera Motivo Descartado de Catálogo:" + motivoUbicadoDeleteTest );

    }


    public void recuperarRutaDesdeBase( String user , String pass ){
        Ruta rutaEnBase = this.rutaDao.getRutaPorClaveYPasswordDePromotor( user, pass );
        this.rutaActual = this.rutaService.refrescarRutaDesdeBase( rutaEnBase );
    }



}
