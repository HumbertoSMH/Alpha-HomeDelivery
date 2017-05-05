package algroup.com.mx.homedelivery.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Ruta;
import algroup.com.mx.homedelivery.business.persistence.Table;
import algroup.com.mx.homedelivery.dao.RutaDao;
import algroup.com.mx.homedelivery.utils.LogUtil;

/**
 * Created by devmac02 on 09/07/15.
 */
public class RutaDaoImpl implements RutaDao {
    private static String CLASSNAME = RutaDaoImpl.class.getSimpleName();

    private static RutaDao rutaDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public  RutaDaoImpl( Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static RutaDao getSingleton( ) {
        if (rutaDao == null) {
            rutaDao = new RutaDaoImpl(HomeDeliveryApp.getCurrentApplication() );
        }
        return rutaDao;
    }



    @Override
    public void insertRuta(Ruta ruta) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = this.rellenarDatosAInsertar( ruta );

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                Table.Rutas.TABLE_NAME,
                null,
                values);
        LogUtil.printLog(CLASSNAME, "insertRuta: ruta:" + ruta);

    }

    public Ruta getRutaById(int idRuta){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Ruta ruta = null;

        Cursor cursor = null;
        try {
             cursor = db.query(
                    Table.Rutas.TABLE_NAME,  // The table to query
                    Table.Rutas.getColumns(),                                // The columns to return
                    Table.Rutas.COLUMN_NAME_IDRUTA.column + " = " + idRuta,  // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                ruta = this.cargarObjetoRuta( cursor );
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return ruta;
    }

    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Ruta ruta = null;

        StringBuilder seccionWhereEnQuery = new StringBuilder();
        seccionWhereEnQuery.append( Table.Rutas.COLUMN_NAME_CLAVEPROMOTOR.column + " = '" + clavePromotor + "' ");
        seccionWhereEnQuery.append( " AND " + Table.Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column + " = '" + passwordPromotor + "' ");

        Cursor cursor = null;
        try {
            cursor = db.query(
                    Table.Rutas.TABLE_NAME,  // The table to query
                    Table.Rutas.getColumns(),                                // The columns to return
                    seccionWhereEnQuery.toString(),  // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                ruta = this.cargarObjetoRuta( cursor );
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }
        return ruta;
    }


    public long updateRuta( Ruta ruta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( ruta );

        return db.update( Table.Rutas.TABLE_NAME , values, Table.Rutas.COLUMN_NAME_IDRUTA.column + " = "
                + ruta.getIdRuta() , null);

    }

    public long deleteRutaById( int idRuta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.Rutas.TABLE_NAME , Table.Rutas.COLUMN_NAME_IDRUTA.column + " = "
                + idRuta , null);
    }

    public long deleteRutaAnterior( int idRutaActual ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.Rutas.TABLE_NAME , Table.Rutas.COLUMN_NAME_IDRUTA.column + " <> "
                + idRutaActual , null);

    }

    private ContentValues rellenarDatosAInsertar( Ruta ruta ) {
        ContentValues values = new ContentValues();
        values.put( Table.Rutas.COLUMN_NAME_IDRUTA.column, ruta.getIdRuta() );
        values.put( Table.Rutas.COLUMN_NAME_FECHAPROGRAMADA.column, ruta.getFechaProgramada() );
        values.put( Table.Rutas.COLUMN_NAME_FECHACREACION.column, ruta.getFechaCreacion() );
        values.put( Table.Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.column, ruta.getFechaUltimaModificacion() );
        values.put( Table.Rutas.COLUMN_NAME_CLAVEPROMOTOR.column, ruta.getPromotor().getClavePromotor() );
        values.put( Table.Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column, ruta.getPromotor().getPassword() );

        return values;
    }

    private ContentValues rellenarDatosAActualizar( Ruta ruta ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Rutas.COLUMN_NAME_IDRUTA.column ,  ruta.getIdRuta());
        values.put( Table.Rutas.COLUMN_NAME_FECHAPROGRAMADA.column ,  ruta.getFechaProgramada());
        values.put( Table.Rutas.COLUMN_NAME_FECHACREACION.column ,  ruta.getFechaCreacion() );
        values.put( Table.Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.column ,  ruta.getFechaUltimaModificacion() );
        values.put( Table.Rutas.COLUMN_NAME_CLAVEPROMOTOR.column, ruta.getPromotor().getClavePromotor() );
        values.put( Table.Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column, ruta.getPromotor().getPassword() );
        return values;
    }

    private Ruta cargarObjetoRuta( Cursor cursor ){
        Ruta ruta = new Ruta();
        ruta.setIdRuta( "" + cursor.getInt( Table.Rutas.COLUMN_NAME_IDRUTA.index ) ) ;
        ruta.setFechaProgramada(cursor.getString(Table.Rutas.COLUMN_NAME_FECHAPROGRAMADA.index)) ;
        ruta.setFechaCreacion(cursor.getString(Table.Rutas.COLUMN_NAME_FECHACREACION.index)) ;
        ruta.setFechaUltimaModificacion(cursor.getString(Table.Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.index));
        ruta.getPromotor().setClavePromotor(cursor.getString(Table.Rutas.COLUMN_NAME_CLAVEPROMOTOR.index));
        ruta.getPromotor().setUsuario(cursor.getString(Table.Rutas.COLUMN_NAME_CLAVEPROMOTOR.index));
        ruta.getPromotor().setPassword(cursor.getString(Table.Rutas.COLUMN_NAME_PASSWORDPROMOTOR.index));

        return ruta;
    }

}
