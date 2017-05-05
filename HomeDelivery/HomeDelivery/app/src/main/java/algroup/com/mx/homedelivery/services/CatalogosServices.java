package algroup.com.mx.homedelivery.services;

import java.util.List;
import java.util.Set;

import algroup.com.mx.homedelivery.business.Medicamento;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoDescarteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoUbicadoResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoPaqueteResponse;
import algroup.com.mx.homedelivery.utils.MotivoDescartado;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;

/**
 * Created by devmac03 on 08/07/15.
 */
public interface CatalogosServices {
    public CatalogoMotivoUbicadoResponse recuperarMotivosUbicadosDesdeWeb();
    public CatalogoMotivoDescarteResponse recuperarMotivosDescartadosDesdeWeb();
    public CatalogoPaqueteResponse recuperarCatalogoPaquetePorRutaDesdeWeb(String Usuario);
    public Set<String> getTodosLosCodigosDePaquetesRegistrados(  );
    public List<MotivoUbicado> getCatalogoMotivoUbicado();
    public List<MotivoDescartado> getCatalogoMotivoDescartado();
    public List<Medicamento> getPaquetePorCodigo( String codigoPaquete );
    public boolean validarExisteKeyPaqueteEnCatalogoCompleto(String keyPaqueteEnCatalogo);
    public boolean validarQueEnClavePaqueteExisteElIdentificadorDelPaquete(String nombrePaquete );

    public void recuperarCatalogosDesdeBase();
    public void actualizarCatalogosEnBase();
}
