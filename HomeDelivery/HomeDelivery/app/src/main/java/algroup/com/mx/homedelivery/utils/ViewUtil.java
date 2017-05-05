package algroup.com.mx.homedelivery.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.internal.widget.ViewUtils;
import android.util.Base64;
import android.widget.Toast;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.controller.MainLogin;


/**
 * Created by devmac03 on 21/04/15.
 */
public class ViewUtil {
    private static final String CLASSNAME = ViewUtils.class.getSimpleName();

    public static AlertDialog mostrarAlertaAceptarCancelar( String message , Context context , DialogInterface.OnClickListener onClickListener ) {
        Alert alert = new Alert( context);
        alert.setMessage( message );
        alert.setButton(AlertDialog.BUTTON_POSITIVE,
                context.getResources().getString(R.string.Aceptar),
                onClickListener);
        alert.setButton(AlertDialog.BUTTON_NEGATIVE,
                context.getResources().getString(R.string.Cancelar),
                onClickListener);
        alert.show();
        return alert;
    }

    public static AlertDialog mostrarAlertaDosOpciones( String message , Context context , DialogInterface.OnClickListener onClickListener,  OpcionBotones opcionA , OpcionBotones opcionB ) {
        Alert alert = new Alert( context);
        alert.setMessage( message );
        alert.setButton(opcionA.id,
                opcionA.label,
                onClickListener);
        alert.setButton(opcionB.id,
                opcionB.label,
                onClickListener);
        alert.show();
        return alert;
    }

    public static AlertDialog mostrarAlertaTresOpciones( String message , Context context , DialogInterface.OnClickListener onClickListener,   OpcionBotones opcionNegativa , OpcionBotones opcionPositiva , OpcionBotones opcionNeutral ) {
        Alert alert = new Alert( context);
        alert.setMessage( message );
        alert.setButton(opcionNegativa.id,
                opcionNegativa.label,
                onClickListener);
        alert.setButton(opcionPositiva.id,
                opcionPositiva.label,
                onClickListener);
        alert.setButton(opcionNeutral.id,
                opcionNeutral.label,
                onClickListener);
        alert.show();
        return alert;
    }

    public static AlertDialog mostrarAlertaAceptarCancelarConTitulo( String titulo , String message , Context context , DialogInterface.OnClickListener onClickListener ) {
        Alert alert = new Alert( context);
        alert.setTitle( titulo );
        alert.setMessage( message );
        alert.setButton(AlertDialog.BUTTON_POSITIVE,
                context.getResources().getString(R.string.Aceptar),
                onClickListener);
        alert.setButton(AlertDialog.BUTTON_NEGATIVE,
                context.getResources().getString(R.string.Cancelar),
                onClickListener);
        alert.show();
        return alert;
    }

    public static void mostrarMensajeRapido( final Context context , final String message){
        new Thread()
        {
            public void run()
            {
                try{
                    ((Activity)context).runOnUiThread(new Runnable(){
                        public void run() {
                            Toast.makeText(context.getApplicationContext(),
                                    message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception exc ){
                    LogUtil.printLog( CLASSNAME , "No se puede mostrar el toast:" + exc.getMessage() );
                }

            }
        }.start();

    }


    public static String obtenerStringBase64( byte[] buffer ){
        return Base64.encodeToString(buffer, 0, buffer.length, Base64.NO_WRAP);
        //buffer =  UtilsMock.getImageMock(PromotorMovilApp.getPromotorMovilApp());
        //return Base64.encodeToString(buffer, 0, buffer.length, Base64.NO_WRAP);
    }

    public static void redireccionarALogin(Activity origen){
        LogUtil.printLog( CLASSNAME , "redireccionarALogin origen:" + origen );
        ViewUtil.mostrarMensajeRapido( origen , "Ocurrio un problema al procesar su petición, favor de reportar con el administrador!" );
        Intent intentToRedirect = new Intent( origen, MainLogin.class );
        intentToRedirect.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        origen.startActivity( intentToRedirect );
    }

    public static void mostrarAlertaDeError( String msg , Activity activityActual ){

        AlertDialog.Builder builder = new AlertDialog.Builder(activityActual);
        builder.setCancelable(false).setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.setTitle( "Mensaje de Error" );
        builder.setMessage( msg );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public static enum OpcionBotones{
        ELIMINAR( DialogInterface.BUTTON_NEGATIVE , "Eliminar"),
        VER(DialogInterface.BUTTON_POSITIVE , "Ver"),
        SALIR(DialogInterface.BUTTON_NEUTRAL , "Salir");

        public int id;
        public String label;
        OpcionBotones( int id , String label ){
            this.id = id;
            this.label = label;
        }
    }
}
