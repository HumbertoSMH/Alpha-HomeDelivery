package algroup.com.mx.homedelivery.dao;

import java.util.List;


import algroup.com.mx.homedelivery.utils.MotivoUbicado;

/**
 * Created by devmac02 on 23/07/15.
 */
public interface MotivoUbicadoDao {

    public long insertMotivoUbicado( MotivoUbicado motivoUbicado );
    public List<MotivoUbicado> getMotivoUbicado();
    public long deleteMotivoUbicado();
}
