package algroup.com.mx.homedelivery.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.persistence.Table;
import algroup.com.mx.homedelivery.business.utils.EstatusVisita;
import algroup.com.mx.homedelivery.business.utils.RespuestaBinaria;
import algroup.com.mx.homedelivery.dao.VisitaDao;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.LogUtil;

/**
 * Created by devmac02 on 09/07/15.
 */
public class VisitaDaoImpl implements VisitaDao {
    private static String CLASSNAME = VisitaDaoImpl.class.getSimpleName();

    private static VisitaDao visitaDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public VisitaDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static VisitaDao getSingleton( ) {
        if (visitaDao == null) {
            visitaDao = new VisitaDaoImpl(HomeDeliveryApp.getCurrentApplication() );
        }
        return visitaDao;
    }



    @Override
    public void insertVisita( Visita visita , int idRuta) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = this.rellenarDatosAInsertar( visita , idRuta );

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                Table.Visitas.TABLE_NAME,
                null,
                values);
        LogUtil.printLog(CLASSNAME, "insertVisita: visita:" + visita);
    }

    public Visita getVisitaById( Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Visita visita = null;

        Cursor cursor = null;
        try {
             cursor = db.query(
                    Table.Visitas.TABLE_NAME,  // The table to query
                    Table.Visitas.getColumns(),                                // The columns to return
                    Table.Visitas.COLUMN_NAME_IDVISITA.column + " = " + idVisita,  // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                visita = this.cargarObjetoVisita( cursor);
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }
        return visita;
    }

    public Visita[] getVisitasByIdRuta(  Integer idRuta){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Visita[] visitasArray = new Visita[0];

        Cursor cursor = db.query(
                Table.Visitas.TABLE_NAME,  // The table to query
                Table.Visitas.getColumns(),                                // The columns to return
                Table.Visitas.COLUMN_NAME_IDRUTA.column + " = " + idRuta,                                        // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                Table.Visitas.COLUMN_NAME_NOMBREMEDICO.column + " ASC"                                                   // The sort order
        );

        int size = cursor.getCount();
        if( size  > 0){
            visitasArray = new Visita[size];
            cursor.moveToFirst();
            for (int i = 0; i < size; i++) {
                visitasArray[i] = this.cargarObjetoVisita(cursor);
                cursor.moveToNext();
            }
        }

        return visitasArray;

    }


    public Integer[] getIdVisitasQueNoSonDeRuta(  Integer idRuta){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Integer[] idVisitaSinRutaArray = new Integer[0];

        String[] columnsIdVisita = {Table.Visitas.COLUMN_NAME_IDVISITA.column};
        Cursor cursor = null;
        try {
             cursor = db.query(
                    Table.Visitas.TABLE_NAME,  // The table to query
                    columnsIdVisita,                                // The columns to return
                    Table.Visitas.COLUMN_NAME_IDRUTA.column + " <> " + idRuta,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                idVisitaSinRutaArray = new Integer[size];
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    idVisitaSinRutaArray[i] = cursor.getInt( 0 );
                    cursor.moveToNext();
                }
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }
        return idVisitaSinRutaArray;

    }


    public long updateVisita( Visita visita , int idRuta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( visita , idRuta );

        return db.update( Table.Visitas.TABLE_NAME , values, Table.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + visita.getIdVisita() , null);

    }

    public long updateIdRutaEnVisita( Visita visita , int idRuta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizarSoloIdRuta( visita , idRuta );
        return db.update( Table.Visitas.TABLE_NAME , values, Table.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + visita.getIdVisita() , null);
    }

    public long deleteVisitaById( int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.Visitas.TABLE_NAME , Table.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + idVisita , null);
    }




    private ContentValues rellenarDatosAInsertar( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        values.put( Table.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put( Table.Visitas.COLUMN_NAME_ESTATUSVISITA.column, visita.getEstatusVisita().name() );
        values.put( Table.Visitas.COLUMN_NAME_CODIGOMEDICO.column, visita.getCodigoMedico());
        values.put( Table.Visitas.COLUMN_NAME_NOMBREMEDICO.column, visita.getNombreMedico());
        values.put( Table.Visitas.COLUMN_NAME_IDESPECIALIDADMEDICO.column, visita.getIdEspecialidadMedico());
        values.put( Table.Visitas.COLUMN_NAME_ESPECIALIDADMEDICO.column, visita.getEspecialidadMedico());
        values.put( Table.Visitas.COLUMN_NAME_DIRECCIONMEDICO.column, visita.getDireccionMedico());
        values.put( Table.Visitas.COLUMN_NAME_FECHACIERRE.column, visita.getFechaCierre());
        values.put( Table.Visitas.COLUMN_NAME_FIRMAMEDICO.column, visita.getFirmaMedico());
        values.put( Table.Visitas.COLUMN_NAME_NODIOCORREO.column, visita.getNoDioCorreo().getIdRespuesta());
        values.put( Table.Visitas.COLUMN_NAME_ACTUALIZOINFORMACION.column, visita.getActualizoInformacion().getIdRespuesta());
        values.put( Table.Visitas.COLUMN_NAME_EMAILMEDICO.column, visita.getEmailMedico());
        values.put( Table.Visitas.COLUMN_NAME_COMENTARIOS.column, visita.getComentarios() );
        values.put( Table.Visitas.COLUMN_NAME_IDDESCARTE.column, visita.getIdDescarte());
        values.put( Table.Visitas.COLUMN_NAME_DESCRIPCIONDESCARTEOTRO.column, visita.getDescripcionDesacrteOtro());
        values.put( Table.Visitas.COLUMN_NAME_IDUBICADO.column, visita.getIdUbicado());
        values.put( Table.Visitas.COLUMN_NAME_DESCRIPCIONUBICADOOTRO.column, visita.getDescripcionUbicadoOtro());
        values.put( Table.Visitas.COLUMN_NAME_CODIGOPAQUETE.column, visita.getCodigoPaquete());
        values.put( Table.Visitas.COLUMN_NAME_PAQUETELOCALIZADO.column, visita.getPaqueteLocalizado().name());
        values.put( Table.Visitas.COLUMN_NAME_FLUJOCIERRE.column, visita.getFlujoDeCierre().name());
        values.put( Table.Visitas.COLUMN_NAME_LATITUD.column, visita.getLocation().getLatitud() );
        values.put( Table.Visitas.COLUMN_NAME_LONGITUD.column, visita. getLocation().getLongitud() );
        values.put( Table.Visitas.COLUMN_NAME_LATITUDCHECKIN.column, visita.getLocationCheckIn().getLatitud() );
        values.put( Table.Visitas.COLUMN_NAME_LONGITUDCHECKIN.column, visita.getLocationCheckIn().getLongitud() );
        values.put( Table.Visitas.COLUMN_NAME_PORTAFOLIO.column, visita.getPortafolio() );  //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_IDPARRILLA.column, visita.getIdParrilla() );  //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_DESPARRILLA.column, visita.getDesParrilla() );  //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_COORDENADASMEDICO.column, visita.getCoordenadasMedico() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_FECHACHECKIN.column, visita.getCoordenadasMedico() ); //columnas nuevas
        //Bloque Autorizado
        values.put( Table.Visitas.COLUMN_NAME_NOMBREAUTORIZADO.column, visita.getNombreAutorizado() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_ESAUTORIZADO.column, visita.getEsAutorizado().getIdRespuesta() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_FOTOFRENTE.column, visita.getFotoFrenteBase64() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_FOTOATRAS.column, visita.getFotoAtrasBase64() ); //columnas nuevas

        values.put( Table.Visitas.COLUMN_NAME_IDRUTA.column, idRuta );
        return values;
    }

    private ContentValues rellenarDatosAActualizar( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put( Table.Visitas.COLUMN_NAME_ESTATUSVISITA.column, visita.getEstatusVisita().name() );
        values.put( Table.Visitas.COLUMN_NAME_CODIGOMEDICO.column, visita.getCodigoMedico());
        values.put( Table.Visitas.COLUMN_NAME_NOMBREMEDICO.column, visita.getNombreMedico());
        values.put( Table.Visitas.COLUMN_NAME_IDESPECIALIDADMEDICO.column, visita.getIdEspecialidadMedico());
        values.put( Table.Visitas.COLUMN_NAME_ESPECIALIDADMEDICO.column, visita.getEspecialidadMedico());
        values.put( Table.Visitas.COLUMN_NAME_DIRECCIONMEDICO.column, visita.getDireccionMedico());
        values.put( Table.Visitas.COLUMN_NAME_FECHACIERRE.column, visita.getFechaCierre());
        values.put( Table.Visitas.COLUMN_NAME_FIRMAMEDICO.column, visita.getFirmaMedico());
        values.put( Table.Visitas.COLUMN_NAME_NODIOCORREO.column, visita.getNoDioCorreo().getIdRespuesta());
        values.put(Table.Visitas.COLUMN_NAME_ACTUALIZOINFORMACION.column, visita.getActualizoInformacion().getIdRespuesta());

        values.put( Table.Visitas.COLUMN_NAME_EMAILMEDICO.column, visita.getEmailMedico());
        values.put( Table.Visitas.COLUMN_NAME_COMENTARIOS.column, visita.getComentarios() );
        values.put( Table.Visitas.COLUMN_NAME_IDDESCARTE.column, visita.getIdDescarte());
        values.put( Table.Visitas.COLUMN_NAME_DESCRIPCIONDESCARTEOTRO.column, visita.getDescripcionDesacrteOtro());
        values.put( Table.Visitas.COLUMN_NAME_IDUBICADO.column, visita.getIdUbicado());
        values.put( Table.Visitas.COLUMN_NAME_DESCRIPCIONUBICADOOTRO.column, visita.getDescripcionUbicadoOtro());
        values.put( Table.Visitas.COLUMN_NAME_CODIGOPAQUETE.column, visita.getCodigoPaquete());
        values.put( Table.Visitas.COLUMN_NAME_PAQUETELOCALIZADO.column, visita.getPaqueteLocalizado().name());
        values.put( Table.Visitas.COLUMN_NAME_FLUJOCIERRE.column, visita.getFlujoDeCierre().name());
        values.put( Table.Visitas.COLUMN_NAME_LATITUD.column, visita.getLocation().getLatitud() );
        values.put( Table.Visitas.COLUMN_NAME_LONGITUD.column, visita.getLocation().getLongitud() );

        values.put(Table.Visitas.COLUMN_NAME_LATITUDCHECKIN.column, visita.getLocationCheckIn().getLatitud());
        values.put(Table.Visitas.COLUMN_NAME_LONGITUDCHECKIN.column, visita.getLocationCheckIn().getLongitud());

        values.put( Table.Visitas.COLUMN_NAME_PORTAFOLIO.column, visita.getPortafolio() );  //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_IDPARRILLA.column, visita.getIdParrilla() );  //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_DESPARRILLA.column, visita.getDesParrilla() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_COORDENADASMEDICO.column, visita.getCoordenadasMedico() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_FECHACHECKIN.column, visita.getFechaCheckIn() ); //columnas nuevas

        //Bloque autorizado
        values.put( Table.Visitas.COLUMN_NAME_NOMBREAUTORIZADO.column, visita.getNombreAutorizado() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_ESAUTORIZADO.column, visita.getEsAutorizado().getIdRespuesta() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_FOTOFRENTE.column, visita.getFotoFrenteBase64() ); //columnas nuevas
        values.put( Table.Visitas.COLUMN_NAME_FOTOATRAS.column, visita.getFotoAtrasBase64() ); //columnas nuevas

        values.put( Table.Visitas.COLUMN_NAME_IDRUTA.column, idRuta );
        return values;
    }

    private ContentValues rellenarDatosAActualizarSoloIdRuta( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put( Table.Visitas.COLUMN_NAME_IDRUTA.column, idRuta );
        return values;
    }

    private Visita cargarObjetoVisita( Cursor cursor ){
        Visita visita = new Visita();
        visita.setIdVisita("" + cursor.getInt(Table.Visitas.COLUMN_NAME_IDVISITA.index));

        String estatusNombre = cursor.getString( Table.Visitas.COLUMN_NAME_ESTATUSVISITA.index);
        visita.setEstatusVisita(EstatusVisita.valueOf(estatusNombre));

        visita.setCodigoMedico(cursor.getString(Table.Visitas.COLUMN_NAME_CODIGOMEDICO.index));
        visita.setNombreMedico(cursor.getString(Table.Visitas.COLUMN_NAME_NOMBREMEDICO.index));
        visita.setIdEspecialidadMedico(cursor.getInt(Table.Visitas.COLUMN_NAME_IDESPECIALIDADMEDICO.index));
        visita.setEspecialidadMedico(cursor.getString(Table.Visitas.COLUMN_NAME_ESPECIALIDADMEDICO.index));
        visita.setDireccionMedico(cursor.getString(Table.Visitas.COLUMN_NAME_DIRECCIONMEDICO.index));
        visita.setFechaCierre(cursor.getString(Table.Visitas.COLUMN_NAME_FECHACIERRE.index));
        visita.setFirmaMedico(cursor.getBlob(Table.Visitas.COLUMN_NAME_FIRMAMEDICO.index));
        visita.setNoDioCorreo(RespuestaBinaria.recuperarRespuestaPorId(cursor.getInt(Table.Visitas.COLUMN_NAME_NODIOCORREO.index)));
        visita.setActualizoInformacion(RespuestaBinaria.recuperarRespuestaPorId(cursor.getInt(Table.Visitas.COLUMN_NAME_ACTUALIZOINFORMACION.index)));

        visita.setEmailMedico(cursor.getString(Table.Visitas.COLUMN_NAME_EMAILMEDICO.index));
        visita.setComentarios(cursor.getString(Table.Visitas.COLUMN_NAME_COMENTARIOS.index));
        visita.setIdDescarte(cursor.getInt(Table.Visitas.COLUMN_NAME_IDDESCARTE.index));
        visita.setDescripcionDesacrteOtro(cursor.getString(Table.Visitas.COLUMN_NAME_DESCRIPCIONDESCARTEOTRO.index));
        visita.setIdUbicado(cursor.getInt(Table.Visitas.COLUMN_NAME_IDUBICADO.index));
        visita.setDescripcionUbicadoOtro(cursor.getString(Table.Visitas.COLUMN_NAME_DESCRIPCIONUBICADOOTRO.index));
        visita.setCodigoPaquete(cursor.getString(Table.Visitas.COLUMN_NAME_CODIGOPAQUETE.index));

        String paqueteLocalizadoString = cursor.getString(Table.Visitas.COLUMN_NAME_PAQUETELOCALIZADO.index);
        visita.setPaqueteLocalizado(RespuestaBinaria.valueOf(paqueteLocalizadoString));

        String flujoDeCierreName = cursor.getString(Table.Visitas.COLUMN_NAME_FLUJOCIERRE.index);
        visita.setFlujoDeCierre(Const.FlujoDeCierre.valueOf(flujoDeCierreName));
        visita.getLocation().setLatitud(cursor.getString(Table.Visitas.COLUMN_NAME_LATITUD.index));
        visita.getLocation().setLongitud(cursor.getString(Table.Visitas.COLUMN_NAME_LONGITUD.index));

        visita.getLocationCheckIn().setLatitud(cursor.getString(Table.Visitas.COLUMN_NAME_LATITUDCHECKIN.index));
        visita.getLocationCheckIn().setLongitud(cursor.getString(Table.Visitas.COLUMN_NAME_LONGITUDCHECKIN.index));

        visita.setPortafolio(cursor.getString(Table.Visitas.COLUMN_NAME_PORTAFOLIO.index));     //columnas nuevas
        visita.setIdParrilla(cursor.getInt(Table.Visitas.COLUMN_NAME_IDPARRILLA.index));     //columnas nuevas
        visita.setDesParrilla(cursor.getString(Table.Visitas.COLUMN_NAME_DESPARRILLA.index));     //columnas nuevas
        visita.setCoordenadasMedico(cursor.getString(Table.Visitas.COLUMN_NAME_COORDENADASMEDICO.index));    //columnas nuevas

        //Bloque autorizado
        visita.setNombreAutorizado(cursor.getString(Table.Visitas.COLUMN_NAME_NOMBREAUTORIZADO.index));
        visita.setEsAutorizado(RespuestaBinaria.recuperarRespuestaPorId(cursor.getInt(Table.Visitas.COLUMN_NAME_ESAUTORIZADO.index)));
        visita.setFotoFrenteBase64(cursor.getBlob(Table.Visitas.COLUMN_NAME_FOTOFRENTE.index));
        visita.setFotoAtrasBase64(cursor.getBlob(Table.Visitas.COLUMN_NAME_FOTOATRAS.index));


        visita.setFechaCheckIn(cursor.getString(Table.Visitas.COLUMN_NAME_FECHACHECKIN.index));    //columnas nuevas


        return visita;
    }

}
