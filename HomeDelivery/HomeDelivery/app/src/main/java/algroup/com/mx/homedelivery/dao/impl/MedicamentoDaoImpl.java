package algroup.com.mx.homedelivery.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Medicamento;
import algroup.com.mx.homedelivery.business.persistence.Table;
import algroup.com.mx.homedelivery.business.persistence.TablaCatalogos;
import algroup.com.mx.homedelivery.dao.MedicamentoDao;
import algroup.com.mx.homedelivery.utils.LogUtil;

/**
 * Created by devmac02 on 23/07/15.
 */
public class MedicamentoDaoImpl implements MedicamentoDao {
    private static String CLASSNAME = MedicamentoDaoImpl.class.getSimpleName();

    private static MedicamentoDao medicamentoDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public MedicamentoDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static MedicamentoDao getSingleton( ) {
        if (medicamentoDao == null) {
            medicamentoDao = new MedicamentoDaoImpl(HomeDeliveryApp.getCurrentApplication() );
        }
        return medicamentoDao;
    }


    public long insertMedicamento( Medicamento medicamento, String codigoPaquete ){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = this.rellenarDatosAInsertar( medicamento , codigoPaquete );

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TablaCatalogos.Medicamentos.TABLE_NAME,
                null,
                values);
        LogUtil.printLog(CLASSNAME, "insertMedicamento: medicamento:" + medicamento);
        return newRowId; //Noi es necesario recuperar un id de la tabla
    }

    public List<Medicamento> getMedicamentoPorCodigoPaquete( String codigoPaquete ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Medicamento> medicamentosList = new ArrayList<Medicamento>();
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TablaCatalogos.Medicamentos.TABLE_NAME,  // The table to query
                    TablaCatalogos.Medicamentos.getColumns(),                                // The columns to return
                    TablaCatalogos.Medicamentos.COLUMN_NAME_CODIGOPAQUETE.column + " = '" + codigoPaquete + "' ",                                    // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if (size > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    medicamentosList.add(this.cargarObjetoMedicamento(cursor));
                    cursor.moveToNext();
                }
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return medicamentosList;
    }

    public List<String> getTodosLosCodigoPaquete( ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<String> codigoPaqueteList = new ArrayList<String>();

        String[] nombrecolumnaPaqueteArray = {TablaCatalogos.Medicamentos.COLUMN_NAME_CODIGOPAQUETE.column };
        Cursor cursor = null;
        try {
             cursor = db.query(
                    TablaCatalogos.Medicamentos.TABLE_NAME,  // The table to query
                    nombrecolumnaPaqueteArray,                                // The columns to return
                    null,                                    // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    TablaCatalogos.Medicamentos.COLUMN_NAME_CODIGOPAQUETE.column  ,     // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );


            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    String nombrePaquete = cursor.getString( 0 );
                    codigoPaqueteList.add( nombrePaquete );
                    cursor.moveToNext();
                }
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return codigoPaqueteList;
    }


    public long deleteMedicamento(){

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        return db.delete(TablaCatalogos.Medicamentos.TABLE_NAME , null , null);

    }

    private Medicamento cargarObjetoMedicamento( Cursor cursor ){
        Medicamento medicamento = new Medicamento();
        medicamento.setIdMedicamento(cursor.getString(TablaCatalogos.Medicamentos.COLUMN_NAME_IDMEDICAMENTO.index));
        medicamento.setNombreMedicamento(cursor.getString(TablaCatalogos.Medicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.index));
        medicamento.setCantidad(cursor.getInt(TablaCatalogos.Medicamentos.COLUMN_NAME_CANTIDAD.index));
        medicamento.setLote(cursor.getString(TablaCatalogos.Medicamentos.COLUMN_NAME_LOTE.index));
        medicamento.setFechaCaducidad(cursor.getString(TablaCatalogos.Medicamentos.COLUMN_NAME_FECHACADUCIDAD.index));

        return medicamento;
    }

    private ContentValues rellenarDatosAInsertar( Medicamento medicamento , String codigoPaquete ) {
        ContentValues values = new ContentValues();
        values.put( TablaCatalogos.Medicamentos.COLUMN_NAME_IDMEDICAMENTO.column, medicamento.getIdMedicamento() );
        values.put( TablaCatalogos.Medicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.column, medicamento.getNombreMedicamento() );
        values.put( TablaCatalogos.Medicamentos.COLUMN_NAME_CANTIDAD.column, medicamento.getCantidad() );
        values.put( TablaCatalogos.Medicamentos.COLUMN_NAME_LOTE.column, medicamento.getLote() );
        values.put( TablaCatalogos.Medicamentos.COLUMN_NAME_FECHACADUCIDAD.column, medicamento.getFechaCaducidad() );

        values.put(TablaCatalogos.Medicamentos.COLUMN_NAME_CODIGOPAQUETE.column, codigoPaquete);
        return values;
    }
}
