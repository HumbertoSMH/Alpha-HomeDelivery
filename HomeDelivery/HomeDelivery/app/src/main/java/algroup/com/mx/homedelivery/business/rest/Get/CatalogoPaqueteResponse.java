package algroup.com.mx.homedelivery.business.rest.Get;

import java.util.Collections;
import java.util.List;

import algroup.com.mx.homedelivery.business.rest.Response;


/**
 * Created by devmac03 on 26/05/15.
 */
public class CatalogoPaqueteResponse extends Response {

    private List<PaqueteRuta> listaPaquetesRuta;

    public CatalogoPaqueteResponse(){
        this.listaPaquetesRuta = Collections.EMPTY_LIST;
    }

    public List<PaqueteRuta> getListaPaquetesRuta() {
        return listaPaquetesRuta;
    }

    public void setListaPaquetesRuta(List<PaqueteRuta> listaPaquetesRuta) {
        this.listaPaquetesRuta = listaPaquetesRuta;
    }
}
