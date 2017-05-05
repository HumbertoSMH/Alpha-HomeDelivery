package algroup.com.mx.homedelivery.dao;

import java.util.List;

import algroup.com.mx.homedelivery.utils.MotivoDescartado;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;

/**
 * Created by devmac02 on 23/07/15.
 */
public interface MotivoDescartadoDao {

    public long insertMotivoDescartado( MotivoDescartado motivoDescartado );
    public List<MotivoDescartado> getMotivoDescartado();
    public long deleteMotivoDescartado();
}
