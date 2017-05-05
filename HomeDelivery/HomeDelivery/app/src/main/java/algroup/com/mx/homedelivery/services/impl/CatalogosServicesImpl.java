package algroup.com.mx.homedelivery.services.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Medicamento;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoDescarteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoUbicadoResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoPaqueteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.MedicamentoRest;
import algroup.com.mx.homedelivery.business.rest.Get.Motivo;
import algroup.com.mx.homedelivery.business.rest.Get.PaqueteRuta;
import algroup.com.mx.homedelivery.dao.MedicamentoDao;
import algroup.com.mx.homedelivery.dao.MotivoDescartadoDao;
import algroup.com.mx.homedelivery.dao.MotivoUbicadoDao;
import algroup.com.mx.homedelivery.dao.PaquetesEntregadosDao;
import algroup.com.mx.homedelivery.dao.impl.MedicamentoDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.MotivoDescartadoDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.MotivoUbicadoDaoImpl;
import algroup.com.mx.homedelivery.dao.impl.PaquetesEntregadosDaoImpl;
import algroup.com.mx.homedelivery.services.CatalogosServices;
import algroup.com.mx.homedelivery.services.JsonService;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.MotivoDescartado;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;
import algroup.com.mx.homedelivery.utils.StringUtils;

/**
 * Created by devmac03 on 08/07/15.
 */
public class CatalogosServicesImpl implements CatalogosServices {
    private static final String CLASSNAME = VisitaServiceImpl.class.getSimpleName();

    private static CatalogosServices catalogosServices;
    private MotivoDescartadoDao motivoDescartadoDao;
    private MotivoUbicadoDao motivoUbicadoDao;
    private MedicamentoDao medicamentoDao;
    private PaquetesEntregadosDao paquetesEntregadosDao;
    private JsonService jsonService;

    private List<MotivoUbicado> motivosUbicados;
    private List<MotivoDescartado> motivosDescartado;
    private Map< String , List<Medicamento> > mapaPaqueteMedicamentos;

    private Context context;

    public CatalogosServicesImpl(Context context){
        this.context = context;
        this.jsonService = JsonServiceImpl.getSingleton();
        this.motivoDescartadoDao = MotivoDescartadoDaoImpl.getSingleton();
        this.motivoUbicadoDao = MotivoUbicadoDaoImpl.getSingleton();
        this.medicamentoDao = MedicamentoDaoImpl.getSingleton();
        this.paquetesEntregadosDao = PaquetesEntregadosDaoImpl.getSingleton();

    }

    public static CatalogosServices getSingleton(){
        if( catalogosServices == null ){
            catalogosServices = new CatalogosServicesImpl( HomeDeliveryApp.getCurrentApplication() );
        }
        return catalogosServices;
    }


    @Override
    public CatalogoMotivoUbicadoResponse recuperarMotivosUbicadosDesdeWeb() {

            CatalogoMotivoUbicadoResponse response = this.jsonService.realizarPeticionObtenerMotivoUbicado();
            if( response.getClaveError().equals( "" ) &&
                    response.getMensaje().equals( "" ) ){
                this.cargarCatalogoMotivosUbicadosDesdeResponse( response.getListadoMotivoUbicado() );
            }
        return response;
    }

    public CatalogoMotivoDescarteResponse recuperarMotivosDescartadosDesdeWeb() {
        CatalogoMotivoDescarteResponse response = this.jsonService.realizarPeticionObtenerMotivoDescarte();
        if( response.getClaveError().equals( "" ) &&
                response.getMensaje().equals( "" ) ){
            this.cargarCatalogoMotivosDescartadosDesdeResponse(response.getCatalogoMotivoDescarte());
        }
        return response;
    }

    @Override
    public CatalogoPaqueteResponse recuperarCatalogoPaquetePorRutaDesdeWeb( String usuario ) {
        CatalogoPaqueteResponse response = this.jsonService.realizarPeticionObtenerCatalogoPaquetes(usuario);
        if( response.getClaveError().equals( "" ) &&
                response.getMensaje().equals( "" ) ){
            this.cargarCatalogoPedidosDesdeResponse(response.getListaPaquetesRuta());
        }
        return response;
    }

    public List<MotivoUbicado> getCatalogoMotivoUbicado(){
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            return armarMotivosUbicadosMock();
        }
        return this.motivosUbicados;
    }


    public List<MotivoDescartado> getCatalogoMotivoDescartado(){
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            return armarMotivosDescartadoMock();
        }
        return this.motivosDescartado;
    }


    public List<Medicamento> getPaquetePorCodigo( String codigoPaquete) {
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            return armarMedicamentosDelPaqueteMock();
        }
        return mapaPaqueteMedicamentos.get(codigoPaquete);
    }

    public Set<String> getTodosLosCodigosDePaquetesRegistrados(  ) {
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            Set<String> claves = new HashSet<String>();
//            claves.add( "clave1" );
//            claves.add( "clave2" );
//            claves.add( "clave3" );
            return claves;

        }
        if(mapaPaqueteMedicamentos != null ){
            return mapaPaqueteMedicamentos.keySet();
        }else{
            return new HashSet<String>();
        }

    }

    public boolean validarExisteKeyPaqueteEnCatalogoCompleto(String keyPaqueteEnCatalogo){
        boolean existePequete = true;
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            if( keyPaqueteEnCatalogo.contains( "abc") ){
                existePequete = false;
            }
        }else{
            existePequete = this.mapaPaqueteMedicamentos.containsKey( keyPaqueteEnCatalogo );
        }
        return existePequete;
    }

    public boolean validarQueEnClavePaqueteExisteElIdentificadorDelPaquete(String nombrePaquete ){
        boolean existePaquete = false;
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            if( nombrePaquete.contains( "abc") ){
                existePaquete = true;
            }
        }else{
            Set<String> keysCatalogoPaquetes =  this.mapaPaqueteMedicamentos.keySet();
            for( String itemKey : keysCatalogoPaquetes ){
                if( itemKey.contains( nombrePaquete) ){
                    existePaquete = true;
                    break;
                }
            }
        }
        return existePaquete;
    }

    private List<Medicamento> armarMedicamentosDelPaqueteMock() {
        int tamañoArreglo = 8;
        List<Medicamento> medicamentosDePaquete = new ArrayList<Medicamento>();
        for( int j = 0 ; j < tamañoArreglo ; j++ ){
            Medicamento med = new Medicamento();
            med.setIdMedicamento("1000" + j);
            med.setNombreMedicamento("Medicamento Generico " + j);
            med.setCantidad(4);
            med.setLote( "AAABBBCCC0000"+ j );
            med.setFechaCaducidad("2016-09-20" );
            medicamentosDePaquete.add( med );
        }
        return medicamentosDePaquete;
    }


    private void cargarCatalogoMotivosUbicadosDesdeResponse( List<Motivo> motivos ){
        this.motivosUbicados = new ArrayList<MotivoUbicado>();
        for( Motivo item : motivos ){
            MotivoUbicado motUbicado = new MotivoUbicado();
            motUbicado.setIdMotivoRetiro( item.getIdDetalle() );
            motUbicado.setDescripcionMotivoRetiro(item.getDescripcion());
            this.motivosUbicados.add( motUbicado );
        }
    }

    private void cargarCatalogoMotivosDescartadosDesdeResponse( List<Motivo> motivos ){
        this.motivosDescartado = new ArrayList<MotivoDescartado>();
        for( Motivo item : motivos ){
            MotivoDescartado motivoDescartado = new MotivoDescartado();
            motivoDescartado.setIdMotivoDescartado(item.getIdDetalle());
            motivoDescartado.setDescripcionMotivoDescartado(item.getDescripcion());
            this.motivosDescartado.add( motivoDescartado );
        }
    }

    private void cargarCatalogoPedidosDesdeResponse( List<PaqueteRuta> paqueteRuta ){

        this.mapaPaqueteMedicamentos = new HashMap< String, List<Medicamento> >();


        for( PaqueteRuta item : paqueteRuta ){
            List<Medicamento> medicamentoList = new ArrayList<Medicamento>();
            String codigoPaquete = item.getCodigoPaquete();
            int idEspecialidad = item.getIdEspecialidad();
//           int idParrilla = item.getIdParrrilla();
            String descripcionParrillaLimpia = item.getDescripcionParrillaLimpia();

            List<MedicamentoRest> detalles = item.getListaDetalles();

            for(MedicamentoRest itemMedRest : detalles){
                Medicamento medicamento = new Medicamento();

                medicamento.setCantidad(itemMedRest.getCantidad());
                medicamento.setLote(itemMedRest.getLote());
                medicamento.setFechaCaducidad(itemMedRest.getFechaCaducidadTexto());
                medicamento.setNombreMedicamento(itemMedRest.getDescripcion());

                medicamentoList.add(medicamento);

            }
            String keyPaquete = StringUtils.armarKeyPaqueteParaMapa( codigoPaquete , descripcionParrillaLimpia  );
//            String keyPaquete = StringUtils.armarKeyPaqueteParaMapa( codigoPaquete , idParrilla , idEspecialidad );
//            String keyPaquete = StringUtils.armarKeyPaqueteParaMapa( codigoPaquete  , idEspecialidad );
            this.mapaPaqueteMedicamentos.put( keyPaquete  , medicamentoList);

        }
    }

//    @Override
//    public List<MotivoDescartado> recuperarMotivosDescartado() {
//
//        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            motivosDescartado = this.armarMotivosDescartadoMock();
//        }else{
//            throw new RuntimeException( "Pendiente de implementar recuperarMotivosDescartado" );
//        }
//        return motivosDescartado;
//    }



    private List<MotivoUbicado> armarMotivosUbicadosMock() {
       List<MotivoUbicado> motivos = new ArrayList<MotivoUbicado>();

        for( int j = 0 ; j < 10; j++ ){
            MotivoUbicado motivo = new MotivoUbicado();
            motivo.setIdMotivoRetiro( j + 1 );
            motivo.setDescripcionMotivoRetiro("Motivo Ubicado " + (j + 1));
            motivos.add( motivo );
        }
        MotivoUbicado motivoOtro = new MotivoUbicado();
        motivoOtro.setIdMotivoRetiro(Const.UBICADO_OTRO);
        motivoOtro.setDescripcionMotivoRetiro( "Otro" );
        motivos.add( motivoOtro );
        return  motivos;
    }

    private List<MotivoDescartado>  armarMotivosDescartadoMock() {
        List<MotivoDescartado> motivos = new ArrayList<MotivoDescartado>();
        for( int j = 0 ; j < 10; j++ ){
            MotivoDescartado motivo = new MotivoDescartado();
            motivo.setIdMotivoDescartado(j + 1);
            motivo.setDescripcionMotivoDescartado("Motivo Descartado " + (j + 1));
            motivos.add( motivo );
        }
        MotivoDescartado motivoOtro = new MotivoDescartado();
        motivoOtro.setIdMotivoDescartado(Const.DESCARTADO_OTRO);
        motivoOtro.setDescripcionMotivoDescartado("Otro");
        motivos.add( motivoOtro );
        return  motivos;
    }


    public void recuperarCatalogosDesdeBase(){
        this.recuperarCatalogoMotivoUbicadoDesdeBase();
        this.recuperarCatalogoMotivoDescartadoDesdeBase();
        this.recuperarCatalogoPaqueteDesdeBase();
    }

    private void recuperarCatalogoMotivoUbicadoDesdeBase(){
        //Recuperar  el catalogo
        //actualizarElListado
        this.motivosUbicados = this.motivoUbicadoDao.getMotivoUbicado();
    }

    private void recuperarCatalogoMotivoDescartadoDesdeBase(){
        //REcuperar  el catalogo
        //actualizarElListado
        this.motivosDescartado = this.motivoDescartadoDao.getMotivoDescartado();
    }

    private void recuperarCatalogoPaqueteDesdeBase(){
        this.mapaPaqueteMedicamentos = new HashMap< String , List<Medicamento> >();
        //Rcuperar  el catalogo
        List<String> codigosPaquete =  this.medicamentoDao.getTodosLosCodigoPaquete();
        if( codigosPaquete.isEmpty() == false ){
            //actualizarLosMapas
            for( String itemPaquete : codigosPaquete){
                List<Medicamento> medicamentosEnPaquete = this.medicamentoDao.getMedicamentoPorCodigoPaquete( itemPaquete) ;
                this.mapaPaqueteMedicamentos.put( itemPaquete , medicamentosEnPaquete );
            }
        }
    }

    public void actualizarCatalogosEnBase(){
        this.actualizarCatalogoMotivoUbicadoEnBase();
        this.actualizarCatalogoMotivoDescartadoEnBase();
        this.actualizarCatalogoPaquetesEnBase();
    }

    private void actualizarCatalogoMotivoUbicadoEnBase(){
        //EliminarContenidoDelCatalogo
        this.motivoUbicadoDao.deleteMotivoUbicado();
        //InsertarNuevoCatalogo
        for( MotivoUbicado itemMotivo : motivosUbicados ){
            this.motivoUbicadoDao.insertMotivoUbicado( itemMotivo );
        }
    }

    private void actualizarCatalogoMotivoDescartadoEnBase(){
        //EliminarContenidoDelCatalogo
        this.motivoDescartadoDao.deleteMotivoDescartado();
        //InsertarNuevoCatalogo
        for( MotivoDescartado itemMotivo : motivosDescartado ){
            this.motivoDescartadoDao.insertMotivoDescartado( itemMotivo );
        }
    }

    private void actualizarCatalogoPaquetesEnBase(){
        //EliminarContenidoDelCatalogo:
        this.medicamentoDao.deleteMedicamento();
        this.paquetesEntregadosDao.eliminarPaquetesEnRuta();
        //InsertarNuevoCatalogo
        Set<String> codigosPaquetes = this.mapaPaqueteMedicamentos.keySet();
        for( String itemCodigoPaquete : codigosPaquetes){
            List<Medicamento> paqueteMedicamentos = this.mapaPaqueteMedicamentos.get(itemCodigoPaquete);
            for( Medicamento itemMedicamento : paqueteMedicamentos ){
                this.medicamentoDao.insertMedicamento( itemMedicamento , itemCodigoPaquete );
            }

            try{//Se registra la tabla de paquetes existentes, para conocer si previamente ya fue asociado a un medico
                if( itemCodigoPaquete.contains( "||") ){
                    String[] keyPaquete = itemCodigoPaquete.replace("||","#").split( "#") ;
                    this.paquetesEntregadosDao.insertarPaquetesEnRuta( keyPaquete[0] );
                }
            }catch ( Exception exc){
                throw  new RuntimeException( "No fue posible recuperar el paquete itemCodigoPaquete:" + itemCodigoPaquete );
            }
            //Se insertan los paquetes asociados a esta ruta

        }
    }


}
