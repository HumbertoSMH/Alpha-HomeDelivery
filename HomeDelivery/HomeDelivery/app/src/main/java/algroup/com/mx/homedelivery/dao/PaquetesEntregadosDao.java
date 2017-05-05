package algroup.com.mx.homedelivery.dao;

import java.util.List;

import algroup.com.mx.homedelivery.business.rest.Get.PaqueteRuta;

/**
 * Created by DEVMAC04 on 11/12/15.
 */
public interface PaquetesEntregadosDao {


    public void insertarPaquetesEnRuta( String codigoPaquete );
    public void eliminarPaquetesEnRuta( );
    public void actualizarPaqueteEntregadoAEntregado( String codigoPaquete  );
    public boolean getEstatusEntregadoDePaquete( String codigoPaquete );

}
