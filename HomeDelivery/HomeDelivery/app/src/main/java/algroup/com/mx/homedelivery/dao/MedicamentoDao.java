package algroup.com.mx.homedelivery.dao;

import java.util.List;

import algroup.com.mx.homedelivery.business.Medicamento;

/**
 * Created by devmac02 on 23/07/15.
 */
public interface MedicamentoDao {

    public long insertMedicamento( Medicamento medicamento, String codigoPaquete);
    public List<String> getTodosLosCodigoPaquete( );
    public List<Medicamento> getMedicamentoPorCodigoPaquete(String codigoPaquete);
    public long deleteMedicamento();
}
