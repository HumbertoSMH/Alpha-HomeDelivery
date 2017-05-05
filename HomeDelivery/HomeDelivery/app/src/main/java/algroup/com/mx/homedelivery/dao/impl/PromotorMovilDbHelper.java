package algroup.com.mx.homedelivery.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import algroup.com.mx.homedelivery.business.persistence.Table;
import algroup.com.mx.homedelivery.business.persistence.TablaCatalogos;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.LogUtil;


/**
 * Created by devmac03 on 10/06/15.
 */
public class PromotorMovilDbHelper extends SQLiteOpenHelper{
    private static final String CLASSNAME = PromotorMovilDbHelper.class.getSimpleName();
    private Context context;


    public PromotorMovilDbHelper(Context context) {
        super(context, Const.DATABASE_NAME, null, Const.DATABASE_VERSION);
        this.context = context;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table.SQL_CREATE_TABLE_RUTAS);
        db.execSQL(Table.SQL_CREATE_TABLE_VISITAS);
        db.execSQL(Table.SQL_CREATE_TABLE_PAQUETES_ENTREGADOS);
        db.execSQL(TablaCatalogos.SQL_CREATE_TABLE_MEDICAMENTOS);
        db.execSQL(TablaCatalogos.SQL_CREATE_TABLE_UBICADO);
        db.execSQL(TablaCatalogos.SQL_CREATE_TABLE_DESCARTADO);
        LogUtil.logInfo(CLASSNAME, "onCreate(SQLiteDatabase db)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL( Table.SQL_DELETE_VISISTAS );
        db.execSQL( Table.SQL_DELETE_RUTAS );
        db.execSQL( Table.SQL_DELETE_PAQUETES_ENTREGADOS );
        db.execSQL(TablaCatalogos.SQL_DELETE_MEDICAMENTOS);
        db.execSQL(TablaCatalogos.SQL_DELETE_UBICADO);
        db.execSQL(TablaCatalogos.SQL_DELETE_DESCARTADO);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /*ublic boolean checkDataBaseExists() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase( Const.PATH + Const.DATABASE_NAME , null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // La base de datos no existe aun
            LogUtil.logInfo( CLASSNAME , "La base de datos no existe error:" + e.getMessage() ) ;
        }
        LogUtil.logInfo( CLASSNAME , "checkDataBaseExists...La base de datos existe?: " + (checkDB != null) );
        return checkDB != null;
    }*/

}
