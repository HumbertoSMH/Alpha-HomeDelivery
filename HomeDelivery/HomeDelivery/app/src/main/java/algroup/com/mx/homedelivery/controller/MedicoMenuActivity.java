package algroup.com.mx.homedelivery.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.utils.EstatusVisita;
import algroup.com.mx.homedelivery.business.utils.Json;
import algroup.com.mx.homedelivery.services.LocationService;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.LocationServiceImpl;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.ViewUtil;


public class MedicoMenuActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String CLASSNAME = MedicoMenuActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private LocationService locationService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    private TextView direccionMedicoTextView;
    private TextView portafolioTextView;
    private TextView coordenadasMedicoTextView;
    private Button entregadoButton;
    private Button cerrarReporteButton;
    private Button checkinButton;
    private Button ubicadoButton;
    private Button descartadoButton;
    private Button cancelarButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.locationService = LocationServiceImpl.getSingleton();
        Intent intent = this.getIntent();
        long idVisita = intent.getLongExtra(Const.ParametrosIntent.ID_VISITA.getNombre(), -1);
        if( idVisita != -1 ){
            this.visita = this.visitaService.getVisitaPorIdVisita( "" + idVisita );
        }else{
            this.visita = this.visitaService.getVisitaActual( );
        }

        this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.medico_menu_layout);
        nombreMedicoTexView = (TextView)this.findViewById( R.id.nombreMedicoTexView );
        nombreMedicoTexView.setText( this.visita.getNombreMedico() );
        especialidadTextView = (TextView)this.findViewById( R.id.especialidadTextView );
        especialidadTextView.setText( this.visita.getEspecialidadMedico() );
        direccionMedicoTextView = (TextView)this.findViewById( R.id.direccionMedicoTextView );
        direccionMedicoTextView.setText( this.visita.getDireccionMedico() );



        portafolioTextView  = (TextView)this.findViewById( R.id.portafolioTextView );
        portafolioTextView.setText( this.visita.getPortafolio() );

        coordenadasMedicoTextView = (TextView)this.findViewById( R.id.coordenadasMedicoTextView );
        coordenadasMedicoTextView.setText( this.visita.getCoordenadasMedico() );

        entregadoButton = (Button)this.findViewById( R.id.entregadoButton );
        ubicadoButton = (Button)this.findViewById( R.id.ubicadoButton );
        checkinButton = (Button)this.findViewById( R.id.checkinButton );
        descartadoButton = (Button)this.findViewById( R.id.descartadoButton );
        cancelarButton = (Button)this.findViewById( R.id.cancelarButton );

        cerrarReporteButton = (Button)this.findViewById( R.id.cerrarReporteButton );
        cerrarReporteButton.setVisibility( View.GONE );


        entregadoButton.setOnClickListener( this );
        ubicadoButton.setOnClickListener( this );
        descartadoButton.setOnClickListener( this );
        checkinButton.setOnClickListener( this );
        cancelarButton.setOnClickListener(this);
        cerrarReporteButton.setOnClickListener( this );

        //validaciones para mostrar en pantalla
        this.aplicarReglasParaHabilitarBotones();
    }

    private void aplicarReglasParaHabilitarBotones() {

        this.checkinButton.setVisibility(View.VISIBLE);
        this.entregadoButton.setVisibility( View.VISIBLE );
        this.cerrarReporteButton.setVisibility( View.VISIBLE );
        this.ubicadoButton.setVisibility( View.VISIBLE );
        this.descartadoButton.setVisibility( View.VISIBLE );

        //Estatus finales
        if( this.visita.getEstatusVisita() == EstatusVisita.ENTREGADA ||
                this.visita.getEstatusVisita() == EstatusVisita.DESCARTADO ){
            this.checkinButton.setVisibility(View.GONE);
            this.cerrarReporteButton.setVisibility(View.GONE);
            this.entregadoButton.setEnabled(false);
            this.ubicadoButton.setEnabled(false);
            this.descartadoButton.setEnabled(false);

        }else if( this.visita.getEstatusVisita() == EstatusVisita.UBICADO ){
            this.checkinButton.setVisibility(View.GONE);
            this.cerrarReporteButton.setVisibility(View.GONE);
            this.entregadoButton.setEnabled(true);
            this.ubicadoButton.setEnabled(false);
            this.descartadoButton.setEnabled(false);
        }else if( this.visita.getEstatusVisita() == EstatusVisita.NO_GUARDADA ){
            this.checkinButton.setVisibility( View.GONE);
            this.entregadoButton.setVisibility(View.GONE);
            this.cerrarReporteButton.setEnabled( true );
            this.ubicadoButton.setEnabled( false );
            this.descartadoButton.setEnabled(false);
        }else if( this.visita.getEstatusVisita() == EstatusVisita.CANCELADA ||
                this.visita.getEstatusVisita() == EstatusVisita.NO_VISITADA  ){
            this.checkinButton.setVisibility(View.GONE);
            this.cerrarReporteButton.setVisibility(View.GONE);
            this.entregadoButton.setEnabled(false);
            this.ubicadoButton.setEnabled(false);
            this.descartadoButton.setEnabled(false);
        }

        //Estatus de Ruta
        else if( this.visita.getFechaCheckIn() == null ||
                this.visita.getFechaCheckIn().length() == 0 ){
            this.cerrarReporteButton.setVisibility(View.GONE);
            this.entregadoButton.setVisibility(View.GONE);
            this.checkinButton.setEnabled(true);
            this.ubicadoButton.setEnabled(false);
            this.descartadoButton.setEnabled(false);
        }
        else {
            this.checkinButton.setVisibility(View.GONE);
            this.cerrarReporteButton.setVisibility(View.GONE);
            this.entregadoButton.setEnabled(true);
            this.ubicadoButton.setEnabled( true );
            this.descartadoButton.setEnabled(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medico_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if( v == checkinButton ){
            this.visitaService.registrarInicioDeVisita();
            prepararPantalla();
            return;
        }
        else if( v == entregadoButton ){
            this.visita.setFlujoDeCierre(Const.FlujoDeCierre.FLUJO_ENTREGADO );
            //intent = new Intent( this, CapturarPaqueteActivity.class);
            intent = this.identificarSiguientePantallaEnflujoEntregado();
        }
        else if( v == ubicadoButton ){
            //AJUSTE 20150807
            //Si la visita esta como no guardada y pertenece al flujo no ubicado, se envia directamente a cierre de
            if(  visita.getEstatusVisita() == EstatusVisita.NO_GUARDADA &&
                 visita.getFlujoDeCierre() == Const.FlujoDeCierre.FLUJO_UBICADO ){
                progressDialog = ProgressDialog.show( this, "", "Realizando cierre de la Visita" );
                CerrarVisitaTask cerrarVisisTask = new CerrarVisitaTask();
                cerrarVisisTask.execute( visita );
                return;
            }else{  //Caso contrario se habilita el nuevo flujo
                this.visita.setFlujoDeCierre(Const.FlujoDeCierre.FLUJO_UBICADO );
                intent = new Intent( this, UbicadoMedicoActivity.class);
            }
        }
        else if( v == descartadoButton ){
            this.visita.setFlujoDeCierre(Const.FlujoDeCierre.FLUJO_DESCARTADO );
            intent = new Intent( this, DescartadoMedicoActivity.class);
        }
        else if( v == cancelarButton ){
            this.finish();
            return;
        }
        else if( v == cerrarReporteButton ){
            progressDialog = ProgressDialog.show( this, "", "Realizando cierre de la Visita" );
            CerrarVisitaTask cerrarVisisTask = new CerrarVisitaTask();
            cerrarVisisTask.execute( visita );
            return;
        }

        this.visitaService.actualizarVisitaActual();
        this.startActivity( intent );
    }

    private Intent identificarSiguientePantallaEnflujoEntregado() {
        Intent intent = null;
        if( visita.getNombreAutorizado().length() > 0 ){
            intent = new Intent( this, IdentificarPersonaAFirmarActivity.class);
        }else{
            //flujo normal
            intent = new Intent( this, CapturarPaqueteActivity.class);
        }
        return intent;
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

            String msg = Json.getMsgError(Json.ERROR_JSON.CERRAR_VISITA);
            if (msg != null){
                msg = " Error al cerrar el reporte: " + msg;
                ViewUtil.mostrarMensajeRapido(MedicoMenuActivity.this, msg);
                //ViewUtil.mostrarAlertaDeError(msg, ComentariosActivity.this);
            } else{
                MedicoMenuActivity.this.visitaService.actualizarVisitaActual();
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append( "El reporte " )
                        .append( MedicoMenuActivity.this.visita.getNombreMedico() )
                        .append( " se cerró con éxito, el número de folio es el " )
                        .append( MedicoMenuActivity.this.visita.getIdVisita() ) ;
                ViewUtil.mostrarMensajeRapido( MedicoMenuActivity.this , msgBuilder.toString() );
            }

            MedicoMenuActivity.this.cerrarVistaDeReporte();
        }
    }

    private void cerrarVistaDeReporte() {
        Intent intent = new Intent(this, MedicosListaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        MedicoMenuActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                actualizarUbicaciónAsyncrono();
            }
        });
    }

    private void actualizarUbicaciónAsyncrono(){
        LogUtil.printLog( CLASSNAME , "actualizarUbicación en Thread Asyncrono" );

        Location location = locationService.getLocation();
        LogUtil.printLog( CLASSNAME , "Location:" + location);
        if( location == null || location.getLatitude() == 0 || location.getLongitude() == 0){
            ViewUtil.mostrarAlertaDeError( "¡No fue posible detectar tu ubicación!" , MedicoMenuActivity.this);
        }
    }
}
