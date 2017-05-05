package algroup.com.mx.homedelivery.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Medicamento;
import algroup.com.mx.homedelivery.business.persistence.TablaCatalogos;
import algroup.com.mx.homedelivery.dao.MotivoUbicadoDao;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.MotivoDescartado;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;

/**
 * Created by devmac02 on 23/07/15.
 */
public class MotivoUbicadoDaoImpl implements MotivoUbicadoDao {
    private static String CLASSNAME = MotivoUbicadoDaoImpl.class.getSimpleName();

    private static MotivoUbicadoDao motivoUbicadoDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public MotivoUbicadoDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static MotivoUbicadoDao getSingleton( ) {
        if (motivoUbicadoDao == null) {
            motivoUbicadoDao = new MotivoUbicadoDaoImpl(HomeDeliveryApp.getCurrentApplication() );
        }
        return motivoUbicadoDao;
    }

    public long insertMotivoUbicado( MotivoUbicado motivoUbicado ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = this.rellenarDatosAInsertar( motivoUbicado );

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TablaCatalogos.Ubicado.TABLE_NAME,
                null,
                values);
        return newRowId; //Noi es necesario recuperar un id de la tabla
    }

    private ContentValues rellenarDatosAInsertar( MotivoUbicado motivoUbicado ) {
        ContentValues values = new ContentValues();

        values.put( TablaCatalogos.Ubicado.COLUMN_NAME_IDUBICADO.column, motivoUbicado.getIdMotivoRetiro() );
        values.put( TablaCatalogos.Ubicado.COLUMN_NAME_DESCRIPCIONUBICADO.column, motivoUbicado.getDescripcionMotivoRetiro() );

        return values;
    }

    public List<MotivoUbicado> getMotivoUbicado(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<MotivoUbicado> motivoUbicadoList = new ArrayList<MotivoUbicado>();

        Cursor cursor = null;
        try {
            cursor = db.query(
                    TablaCatalogos.Ubicado.TABLE_NAME,  // The table to query
                    TablaCatalogos.Ubicado.getColumns(),                                // The columns to return
                    TablaCatalogos.Ubicado.COLUMN_NAME_IDUBICADO.column,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    motivoUbicadoList.add( this.cargarObjetoUbicado(cursor) );
                    cursor.moveToNext();
                }
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return motivoUbicadoList;
    }

    private MotivoUbicado cargarObjetoUbicado( Cursor cursor ){
        MotivoUbicado motivoUbicado = new MotivoUbicado();

        motivoUbicado.setIdMotivoRetiro(cursor.getInt(TablaCatalogos.Ubicado.COLUMN_NAME_IDUBICADO.index));
        motivoUbicado.setDescripcionMotivoRetiro(cursor.getString(TablaCatalogos.Ubicado.COLUMN_NAME_DESCRIPCIONUBICADO.index));

        return motivoUbicado;
    }

    public long deleteMotivoUbicado(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(TablaCatalogos.Ubicado.TABLE_NAME , null , null);
    }
}
