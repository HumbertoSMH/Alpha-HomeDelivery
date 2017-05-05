package algroup.com.mx.homedelivery.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.utils.EstatusVisita;
import algroup.com.mx.homedelivery.business.utils.Json;
import algroup.com.mx.homedelivery.business.utils.RespuestaBinaria;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.ValidadorUIMensajes;
import algroup.com.mx.homedelivery.utils.ViewUtil;


public class ComentariosActivity extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    //Services
    private VisitaService visitaService;

    //Business
    private Visita visita;

    //ui elements
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    private EditText comentarioEditText;
    private Button cancelarComentarioButton;
    private Button cerrarReporteButton;
    private ProgressDialog progressDialog;
    private CheckBox cambioDatosCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.comentarios_layout);

        this.nombreMedicoTexView = (TextView)findViewById( R.id.nombreMedicoTexView);
        this.nombreMedicoTexView.setText(this.visita.getNombreMedico());

        this.especialidadTextView = (TextView)findViewById( R.id.especialidadTextView);
        this.especialidadTextView.setText( this.visita.getEspecialidadMedico() );

        this.comentarioEditText = (EditText)findViewById( R.id.comentarioEditText);
        this.comentarioEditText.setText(this.visita.getComentarios());

        this.cambioDatosCheckBox = (CheckBox)findViewById( R.id.cambioDatosCheckBox );

        this.cancelarComentarioButton = (Button)findViewById( R.id.cancelarComentarioButton);
        this.cerrarReporteButton = (Button)findViewById( R.id.cerrarReporteButton);

        this.cancelarComentarioButton.setOnClickListener( this );
        this.cerrarReporteButton.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comentarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if( v == cancelarComentarioButton ){
            this.finish();
            return;
        }
        else if( v == cerrarReporteButton ){
            //boolean existenErrores = this.realizarValidacionesAntesDeCerrarReporte();
            //if( existenErrores == false ){
            this.visita.setComentarios(this.comentarioEditText.getText().toString()) ;
            if( this.cambioDatosCheckBox.isChecked() == true ){
                this.visita.setActualizoInformacion(RespuestaBinaria.SI );
            }
            this.ajustarValoresSegunFlujoCierreVisita();
                StringBuilder msg = new StringBuilder();
                msg.append( "Desea cerrar el reporte " )
                        .append( this.visita.getNombreMedico() )
                        .append(" con Folio: ")
                        .append(this.visita.getIdVisita() )
                        .append( " como: " )
                        .append( this.visita.getFlujoDeCierre().nombre );
                ViewUtil.mostrarAlertaAceptarCancelarConTitulo("Cerrar Captura", msg.toString(), this, this);
            //}
        }
    }

    /*private boolean realizarValidacionesAntesDeCerrarReporte() {
        Boolean existenErrores = false;
        ValidadorUIMensajes userValidatorMessage = visitaValidator.validarNombreJefe(nombreJefeDeptoEditText);
        if( userValidatorMessage.isCorrecto() == false ){
            nombreJefeDeptoEditText.setError( userValidatorMessage.getMensaje() );
            existenErrores = true;
        }
        if( mSignature.contieneFirma() == false ){
            existenErrores = true;
            ViewUtil.mostrarMensajeRapido(this, "No se detecta la firma");
        }

        return existenErrores;
    }*/

    @Override
    public void onClick(DialogInterface dialog, int which) {
        //LogUtil.printLog( CLASSNAME , "AlertClicked: dialog:" + dialog + " , wich:" + which);
        if( which == AlertDialog.BUTTON_POSITIVE ){
//            StringBuilder msg = new StringBuilder();
//            msg.append( "El reporte " )
//                    .append( this.visita.getNombreMedico() )
//                    .append( " se cerró con éxito, el número de folio es el " )
//                    .append( this.visita.getIdVisita() ) ;
//            ViewUtil.mostrarMensajeRapido( this , msg.toString() );
//
//            this.cerrarVistaDeReporte();
//
//            Intent intent = new Intent( this , MedicoMenuActivity.class );
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            this.startActivity(intent);
            progressDialog = ProgressDialog.show( this, "", "Realizando cierre de la Visita" );
            CerrarVisitaTask cerrarVisisTask = new CerrarVisitaTask();
            cerrarVisisTask.execute( visita );
        }else {
            //No se realiza nada
        }
    }
    
    private void ajustarValoresSegunFlujoCierreVisita(){
        Const.FlujoDeCierre flujoCierre = this.visita.getFlujoDeCierre();
        switch ( flujoCierre.idFlujo ) {
            case Const.FLUJO_ENTREGADO_ID:
                this.visita.setEstatusVisita(EstatusVisita.ENTREGADA);
                this.visita.setIdDescarte(0);
                this.visita.setDescripcionDesacrteOtro("");
                this.visita.setIdUbicado(0);
                this.visita.setDescripcionUbicadoOtro("");
                break;
            case Const.FLUJO_UBICADO_ID:
                this.visita.setEstatusVisita(EstatusVisita.UBICADO);
                this.visita.setIdDescarte(0);
                this.visita.setDescripcionDesacrteOtro("");
                this.visita.setFirmaMedico(new byte[0]);
                this.visita.setCodigoPaquete("");
                this.visita.setEmailMedico("");
                break;
            case Const.FLUJO_DESCARTADO_ID:
                this.visita.setEstatusVisita(EstatusVisita.DESCARTADO);
                this.visita.setIdUbicado(0);
                this.visita.setDescripcionUbicadoOtro("");
                this.visita.setFirmaMedico(new byte[0]);
                this.visita.setCodigoPaquete("");
                this.visita.setEmailMedico("");
                break;
            default:
                //Some
                break;
        }
    }



    private void cerrarVistaDeReporte() {
        Intent intent = new Intent(this, MedicosListaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.putExtra( Const.ParametrosIntent.POSICION_VISITA.getNombre() , this.visita.getIdVisita() );
        startActivity(intent);
    }



    private class CerrarVisitaTask extends AsyncTask<Visita, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Visita... params) {

            visitaService.realizarCierreVisita(params[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progressDialog.dismiss();

//            boolean mostrarSiguienteAct = false;

            String msg = Json.getMsgError(Json.ERROR_JSON.CERRAR_VISITA);
            if (msg != null){
                msg = " Error al cerrar el reporte: " + msg;
                ViewUtil.mostrarMensajeRapido( ComentariosActivity.this , msg );
                //ViewUtil.mostrarAlertaDeError(msg, ComentariosActivity.this);
            } else{
//                mostrarSiguienteAct = true;
                ComentariosActivity.this.visitaService.actualizarVisitaActual();
                ComentariosActivity.this.visitaService.actualizarEstatusPaqueteEntregadoEnVisitaActual();
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append( "El reporte " )
                        .append( ComentariosActivity.this.visita.getNombreMedico() )
                        .append( " se cerró con éxito, el número de folio es el " )
                        .append( ComentariosActivity.this.visita.getIdVisita() ) ;
                ViewUtil.mostrarMensajeRapido( ComentariosActivity.this , msgBuilder.toString() );
            }

//            if ( mostrarSiguienteAct ){
                ComentariosActivity.this.cerrarVistaDeReporte();
//            }
        }
    }
}
