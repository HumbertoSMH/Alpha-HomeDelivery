package algroup.com.mx.homedelivery.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Medicamento;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.utils.RespuestaBinaria;
import algroup.com.mx.homedelivery.controller.validator.VisitaValidator;
import algroup.com.mx.homedelivery.services.CatalogosServices;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.CatalogosServicesImpl;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.StringUtils;
import algroup.com.mx.homedelivery.utils.ValidadorUIMensajes;
import algroup.com.mx.homedelivery.utils.ViewUtil;
import jim.h.common.android.zxinglib.integrator.IntentIntegratorBiaani;
import jim.h.common.android.zxinglib.integrator.IntentResult;

public class CapturarPaqueteActivity extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnClickListener {
    private static final String CLASSNAME = CapturarPaqueteActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private VisitaValidator visitaValidator;
    private CatalogosServices catalogosServices;

    //Business
    private Visita visita;

    //Control aux
    private Handler handler = new Handler();

    //UI Elementos
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    private EditText codigoPaqueteEditText;
    private Button buscarPaqueteButton;
    private Button escanearButton;
    private TextView mensajeErrorTextView;
    private Button cancelarPedidoButton;
    private Button continuarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visitaValidator = VisitaValidator.getSingleton();
        this.catalogosServices = CatalogosServicesImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.capturar_paquete_layout);

        this.nombreMedicoTexView = (TextView)findViewById( R.id.nombreMedicoTexView );
        this.nombreMedicoTexView.setText( this.visita.getNombreMedico() );
        this.especialidadTextView = (TextView)findViewById( R.id.especialidadTextView );
        this.especialidadTextView.setText( this.visita.getEspecialidadMedico() );

        this.codigoPaqueteEditText = (EditText) findViewById( R.id.codigoPaqueteEditText );
        this.codigoPaqueteEditText.setText( this.visita.getCodigoPaquete() );
        this.buscarPaqueteButton = (Button) findViewById( R.id.buscarPaqueteButton );
        this.escanearButton = (Button) findViewById( R.id.escanearButton );
        this.mensajeErrorTextView = (TextView) findViewById( R.id.mensajeErrorTextView );
        this.mensajeErrorTextView.setVisibility(View.GONE );

        this.cancelarPedidoButton = (Button) findViewById( R.id.cancelarPedidoButton );
        this.continuarButton = (Button) findViewById( R.id.continuarButton );

        this.buscarPaqueteButton.setOnClickListener( this );
        this.escanearButton.setOnClickListener( this );
        this.cancelarPedidoButton.setOnClickListener( this );
        this.continuarButton.setOnClickListener( this );



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_capturar_paquete, menu);
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
        String codigoPaquete = this.codigoPaqueteEditText.getText().toString();
//        int idParrilla = this.visita.getIdParrilla();
        String desParrilla = this.visita.getDesParrilla();
        int idEspecialidad = this.visita.getIdEspecialidadMedico();
        String keyPaqueteCatalogo = StringUtils.armarKeyPaqueteParaMapa(codigoPaquete, desParrilla );
//        String keyPaqueteCatalogo = StringUtils.armarKeyPaqueteParaMapa( codigoPaquete , idParrilla , idEspecialidad );
//        String keyPaqueteCatalogo = StringUtils.armarKeyPaqueteParaMapa( codigoPaquete  , idEspecialidad );

        if( v == cancelarPedidoButton ){
            this.finish();
            return;
        }
        else if( v == continuarButton ){
            //Validar que se tengan datos capturados
            ValidadorUIMensajes validatorResult = visitaValidator.validarCodigoPaquete(this.codigoPaqueteEditText);
            if( validatorResult.isCorrecto() == false ){
                this.codigoPaqueteEditText.setError( validatorResult.getMensaje() );
                return;
            }
            //si existe un codigo de paquete se muestra alerta para preguntar si queire indicar que no se ubicó
            //Validar que se trate de un paquete válido

            if( CapturarPaqueteActivity.this.catalogosServices.validarQueEnClavePaqueteExisteElIdentificadorDelPaquete( codigoPaquete ) == false ){
                StringBuilder sb = new StringBuilder();
                sb.append("No fue localizado el paquete:" +  codigoPaquete );
                sb.append(", ¿Deseas continuar de todos modos?");
                ViewUtil.mostrarAlertaAceptarCancelar( sb.toString(), this, this );
                return;
            }else
            if( this.catalogosServices.validarExisteKeyPaqueteEnCatalogoCompleto(keyPaqueteCatalogo) == false ){
                StringBuilder sb = new StringBuilder();
                sb.append("El paquete:" +  codigoPaquete + ", no coincide con la parrilla del médico");
                sb.append(", ¿Deseas continuar de todos modos?");
                ViewUtil.mostrarAlertaAceptarCancelar( sb.toString(), this, this );
                return;
            }

            boolean codigoAsignadoPreviamente = CapturarPaqueteActivity.this.visitaService.validarSiCodigoPaqueteNoFueAsignadoPreviamente( codigoPaquete.trim() );
            if( codigoAsignadoPreviamente ){
                CapturarPaqueteActivity.this.mensajeErrorTextView.setText( "012 - Este paquete fue entregado previamente" );
                CapturarPaqueteActivity.this.mensajeErrorTextView.setVisibility(View.VISIBLE);
                CapturarPaqueteActivity.this.codigoPaqueteEditText.setText(codigoPaquete);
                return;
            }

            this.preparaDetallePaqueteEnVisita( keyPaqueteCatalogo , codigoPaquete );
            this.desplegarPantallaDetallePaquete(codigoPaquete);

        }
        else if( v == buscarPaqueteButton ){
            //Validar que se tengan datos capturados
            ValidadorUIMensajes validatorResult = visitaValidator.validarCodigoPaquete(this.codigoPaqueteEditText);
            if( validatorResult.isCorrecto() == false ){
                this.codigoPaqueteEditText.setError( validatorResult.getMensaje() );
                return;
            }
            //Validar que se trate de un paquete válido

            if( CapturarPaqueteActivity.this.catalogosServices.validarQueEnClavePaqueteExisteElIdentificadorDelPaquete( codigoPaquete ) == false ){
                CapturarPaqueteActivity.this.mensajeErrorTextView.setText( "002 - EL paquete no existe en el catálogo" );
                CapturarPaqueteActivity.this.mensajeErrorTextView.setVisibility(View.VISIBLE);
                return;
            }else
            if( this.catalogosServices.validarExisteKeyPaqueteEnCatalogoCompleto(keyPaqueteCatalogo) == false ){
                CapturarPaqueteActivity.this.mensajeErrorTextView.setText( "002 - La parrilla del médico no coincide con la del paquete" );
                this.mensajeErrorTextView.setVisibility( View.VISIBLE );
                return;
            }

            boolean codigoAsignadoPreviamente = CapturarPaqueteActivity.this.visitaService.validarSiCodigoPaqueteNoFueAsignadoPreviamente( codigoPaquete.trim() );
            if( codigoAsignadoPreviamente ){
                CapturarPaqueteActivity.this.mensajeErrorTextView.setText( "012 - Este paquete fue entregado previamente" );
                CapturarPaqueteActivity.this.mensajeErrorTextView.setVisibility(View.VISIBLE);
                CapturarPaqueteActivity.this.codigoPaqueteEditText.setText(codigoPaquete);
                return;
            }


            this.preparaDetallePaqueteEnVisita( keyPaqueteCatalogo , codigoPaquete );
            this.desplegarPantallaDetallePaquete(codigoPaquete);
        }
        else if( v == escanearButton ){
                    IntentIntegratorBiaani.initiateScan(this, R.layout.capture_bar_code,
                            R.id.viewfinder_view, R.id.preview_view, true);
            return;
        }

    }

    private void preparaDetallePaqueteEnVisita(String keyPaqueteCatalogo , String codigoPaquete) {
        List<Medicamento> medicamentos = this.catalogosServices.getPaquetePorCodigo( keyPaqueteCatalogo );
        Medicamento[] medicamentosArray = medicamentos.toArray( new Medicamento[0]);
        this.visita.setMedicamentos( medicamentosArray );
        this.visita.setCodigoPaquete( codigoPaquete );
        this.visita.setPaqueteLocalizado(RespuestaBinaria.SI );
        ViewUtil.mostrarMensajeRapido( this, "¡El paquete fue localizado!" );
        this.visitaService.actualizarVisitaActual();
    }


    private void desplegarPantallaDetallePaquete(String codigoPaquete) {
        Intent intent = new Intent( this , DetalleEntregaActivity.class );
        this.startActivity( intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        LogUtil.printLog(CLASSNAME, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case IntentIntegratorBiaani.REQUEST_CODE:
                IntentResult scanResult = IntentIntegratorBiaani.parseActivityResult(requestCode,
                        resultCode, intent);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                if (result != null) {
                    LogUtil.printLog(CLASSNAME, "El valor recuperado:" + result.toString());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.printLog(CLASSNAME, "El valor recuperado:" + result.toString());
                            String codigoPaquete = result.toString();
//                            int idParrilla = CapturarPaqueteActivity.this.visita.getIdParrilla();
                            String desParrilla = CapturarPaqueteActivity.this.visita.getDesParrilla();
                            int idEspecialidad = CapturarPaqueteActivity.this.visita.getIdEspecialidadMedico();
                            String keyPaqueteCatalogo = StringUtils.armarKeyPaqueteParaMapa(codigoPaquete, desParrilla );
//                            String keyPaqueteCatalogo = StringUtils.armarKeyPaqueteParaMapa( codigoPaquete , idParrilla , idEspecialidad );
//                            String keyPaqueteCatalogo = StringUtils.armarKeyPaqueteParaMapa( codigoPaquete  , idEspecialidad );

                            if( CapturarPaqueteActivity.this.catalogosServices.validarQueEnClavePaqueteExisteElIdentificadorDelPaquete(codigoPaquete) == false ){
                                CapturarPaqueteActivity.this.mensajeErrorTextView.setText( "001 - EL paquete no existe en el catálogo" );
                                CapturarPaqueteActivity.this.mensajeErrorTextView.setVisibility(View.VISIBLE);
                                CapturarPaqueteActivity.this.codigoPaqueteEditText.setText( codigoPaquete );
                                return;
                            }else

                            if (CapturarPaqueteActivity.this.catalogosServices.validarExisteKeyPaqueteEnCatalogoCompleto(keyPaqueteCatalogo) == false) {
                                CapturarPaqueteActivity.this.mensajeErrorTextView.setText( "001 - La parrilla del médico no coincide con la del paquete" );
                                CapturarPaqueteActivity.this.mensajeErrorTextView.setVisibility(View.VISIBLE);
                                CapturarPaqueteActivity.this.codigoPaqueteEditText.setText( codigoPaquete );
                                return;
                            }
                            boolean codigoAsignadoPreviamente = CapturarPaqueteActivity.this.visitaService.validarSiCodigoPaqueteNoFueAsignadoPreviamente( codigoPaquete.trim() );
                            if( codigoAsignadoPreviamente ){
                                CapturarPaqueteActivity.this.mensajeErrorTextView.setText( "012 - Este paquete fue entregado previamente" );
                                CapturarPaqueteActivity.this.mensajeErrorTextView.setVisibility(View.VISIBLE);
                                CapturarPaqueteActivity.this.codigoPaqueteEditText.setText(codigoPaquete);
                                return;
                            }

                            CapturarPaqueteActivity.this.preparaDetallePaqueteEnVisita( keyPaqueteCatalogo , codigoPaquete);
                            CapturarPaqueteActivity.this.desplegarPantallaDetallePaquete(codigoPaquete);
                        }

                    });
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog(CLASSNAME, "AlertClicked: dialog:" + dialog + " , wich:" + which);
        if( which == AlertDialog.BUTTON_POSITIVE ){
            String codigoPaquete = this.codigoPaqueteEditText.getText().toString();
            this.visita.setCodigoPaquete( codigoPaquete );
            this.visita.setPaqueteLocalizado(RespuestaBinaria.NO );
            this.visitaService.actualizarVisitaActual();
            this.desplegarPantallaFirma();
        }else {
            //No se realiza nada
        }
    }


    private void desplegarPantallaFirma( ) {
        Intent intent = new Intent( this , FirmaMedicoActivity.class );
        this.startActivity( intent);
    }
}
