package algroup.com.mx.homedelivery.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.persistence.TablaCatalogos;
import algroup.com.mx.homedelivery.dao.MotivoDescartadoDao;
import algroup.com.mx.homedelivery.dao.MotivoUbicadoDao;
import algroup.com.mx.homedelivery.utils.MotivoDescartado;

/**
 * Created by devmac02 on 23/07/15.
 */
public class MotivoDescartadoDaoImpl implements MotivoDescartadoDao {
    private static String CLASSNAME = MotivoDescartadoDaoImpl.class.getSimpleName();

    private static MotivoDescartadoDao motivoDescartadoDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public MotivoDescartadoDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static MotivoDescartadoDao getSingleton( ) {
        if (motivoDescartadoDao == null) {
            motivoDescartadoDao = new MotivoDescartadoDaoImpl(HomeDeliveryApp.getCurrentApplication() );
        }
        return motivoDescartadoDao;
    }

    public long insertMotivoDescartado( MotivoDescartado motivoDescartado ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = this.rellenarDatosAInsertar( motivoDescartado );

        //Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TablaCatalogos.Descartado.TABLE_NAME,
                null,
                values);
        return newRowId; //Noi es necesario recuperar un id de la tabla
    }

    private ContentValues rellenarDatosAInsertar( MotivoDescartado motivoDescartado ){
        ContentValues values = new ContentValues();

        values.put( TablaCatalogos.Descartado.COLUMN_NAME_IDDESCARTADO.column, motivoDescartado.getIdMotivoDescartado());
        values.put( TablaCatalogos.Descartado.COLUMN_NAME_DESCRIPCIONDESCARTADO.column, motivoDescartado.getDescripcionMotivoDescartado());

        return values;
    }

    public List<MotivoDescartado> getMotivoDescartado(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<MotivoDescartado> motivoDescartadoList = new ArrayList<MotivoDescartado>();

        Cursor cursor = null;
        try {
             cursor = db.query(
                    TablaCatalogos.Descartado.TABLE_NAME,  // The table to query
                    TablaCatalogos.Descartado.getColumns(),                                // The columns to return
                    null,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                    // don't group the rows
                    null,                                                    // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++){
                    motivoDescartadoList.add( this.cargarObjetosDescartado(cursor));
                    cursor.moveToNext();
                }
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return motivoDescartadoList;

    }

    private MotivoDescartado cargarObjetosDescartado( Cursor cursor){
        MotivoDescartado motivoDescartado = new MotivoDescartado();

        motivoDescartado.setIdMotivoDescartado(cursor.getInt(TablaCatalogos.Descartado.COLUMN_NAME_IDDESCARTADO.index));
        motivoDescartado.setDescripcionMotivoDescartado(cursor.getString(TablaCatalogos.Descartado.COLUMN_NAME_DESCRIPCIONDESCARTADO.index));

        return  motivoDescartado;
    }
    public long deleteMotivoDescartado(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return  db.delete(TablaCatalogos.Descartado.TABLE_NAME , null , null);
    }
}
