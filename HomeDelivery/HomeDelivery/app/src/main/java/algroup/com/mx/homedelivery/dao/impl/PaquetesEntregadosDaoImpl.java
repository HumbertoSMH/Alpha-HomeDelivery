package algroup.com.mx.homedelivery.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Ruta;
import algroup.com.mx.homedelivery.business.persistence.Table;
import algroup.com.mx.homedelivery.business.rest.Get.PaqueteRuta;
import algroup.com.mx.homedelivery.dao.PaquetesEntregadosDao;
import algroup.com.mx.homedelivery.dao.RutaDao;
import algroup.com.mx.homedelivery.utils.LogUtil;

/**
 * Created by DEVMAC04 on 11/12/15.
 */
public class PaquetesEntregadosDaoImpl implements PaquetesEntregadosDao {

    private static String CLASSNAME = PaquetesEntregadosDaoImpl.class.getSimpleName();

    private static PaquetesEntregadosDao paquetesEntregadosDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public  PaquetesEntregadosDaoImpl( Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static PaquetesEntregadosDao getSingleton( ) {
        if (paquetesEntregadosDao == null) {
            paquetesEntregadosDao = new PaquetesEntregadosDaoImpl(HomeDeliveryApp.getCurrentApplication() );
        }
        return paquetesEntregadosDao;
    }



    @Override
    public void insertarPaquetesEnRuta(String codigoPaquete ) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = this.rellenarDatosAInsertar( codigoPaquete );

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    Table.PaquetesEntregados.TABLE_NAME,
                    null,
                    values);
            LogUtil.printLog(CLASSNAME, "insert Tabla Paquetes a entregar: codigoPaquete:" + codigoPaquete);
    }

    public boolean getEstatusEntregadoDePaquete( String codigoPaquete ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        boolean esEntregado = false;
        //String[] columnaCodigoPaquete = {Table.PaquetesEntregados.COLUMN_NAME_CODIGO_PAQUETE.column};
        Cursor cursor = null;
        try {
            cursor = db.query(
                    Table.PaquetesEntregados.TABLE_NAME,  // The table to query
                    Table.PaquetesEntregados.getColumns(),                                // The columns to return
                    Table.PaquetesEntregados.COLUMN_NAME_CODIGO_PAQUETE.column + " = '" + codigoPaquete + "' ",  // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                int trueOrFalse = cursor.getInt( Table.PaquetesEntregados.COLUMN_NAME_ENTREGADO.index);
                esEntregado = ( trueOrFalse == 1 );
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return esEntregado;
    }



    @Override
    public void eliminarPaquetesEnRuta( ) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(Table.PaquetesEntregados.TABLE_NAME, null, null);
        LogUtil.printLog( CLASSNAME , "eliminarPaquetesEnRuta, elementos eliminados:" + rowsDeleted );
        return;
    }

    @Override
    public void actualizarPaqueteEntregadoAEntregado( String codigoPaquete ) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( codigoPaquete );

        int rowUpdate =  db.update( Table.PaquetesEntregados.TABLE_NAME , values, Table.PaquetesEntregados.COLUMN_NAME_CODIGO_PAQUETE.column + " = '"
                + codigoPaquete + "'" , null);
        LogUtil.printLog( CLASSNAME , "eliminarPaquetesEnRuta, elementos actualzado:" + rowUpdate );
        return;
    }

    private ContentValues rellenarDatosAActualizar( String codigoPaquete ) {
        ContentValues values = new ContentValues();
        values.put( Table.PaquetesEntregados.COLUMN_NAME_CODIGO_PAQUETE.column, codigoPaquete );
        values.put( Table.PaquetesEntregados.COLUMN_NAME_ENTREGADO.column, 1 );

        return values;
    }


    private ContentValues rellenarDatosAInsertar( String codigoPaquete ) {
        ContentValues values = new ContentValues();
        values.put( Table.PaquetesEntregados.COLUMN_NAME_CODIGO_PAQUETE.column, codigoPaquete );
        values.put( Table.PaquetesEntregados.COLUMN_NAME_ENTREGADO.column, 0 );

        return values;
    }


}
