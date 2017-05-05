package algroup.com.mx.homedelivery.business.rest.Get;

import java.util.Collections;
import java.util.List;

import algroup.com.mx.homedelivery.business.rest.Response;


/**
 * Created by devmac03 on 26/05/15.
 */
public class CatalogoMotivoDescarteResponse extends Response {

    private List<Motivo> catalogoMotivoDescarte;

    public CatalogoMotivoDescarteResponse( ) {
        this.catalogoMotivoDescarte = Collections.EMPTY_LIST;
    }

    public List<Motivo> getCatalogoMotivoDescarte() {
        return catalogoMotivoDescarte;
    }

    public void setCatalogoMotivoDescarte(List<Motivo> catalogoMotivoDescarte) {
        this.catalogoMotivoDescarte = catalogoMotivoDescarte;
    }
}
