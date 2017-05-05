package algroup.com.mx.homedelivery.business.rest.Get;

import java.util.Collections;
import java.util.List;

import algroup.com.mx.homedelivery.business.rest.Response;


/**
 * Created by devmac03 on 26/05/15.
 */
public class CatalogoMotivoUbicadoResponse extends Response {

    private List<Motivo> listadoMotivoUbicado;

    public CatalogoMotivoUbicadoResponse() {
        this.listadoMotivoUbicado = Collections.EMPTY_LIST;
    }

    public List<Motivo> getListadoMotivoUbicado() {
        return listadoMotivoUbicado;
    }

    public void setListadoMotivoUbicado(List<Motivo> listadoMotivoUbicado) {
        this.listadoMotivoUbicado = listadoMotivoUbicado;
    }
}
