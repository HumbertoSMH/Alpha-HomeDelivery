package algroup.com.mx.homedelivery.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Ruta;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.utils.RespuestaBinaria;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.LogUtil;

public class IdentificarPersonaAFirmarActivity extends ActionBarActivity implements View.OnClickListener{
    private static final String CLASSNAME = IdentificarPersonaAFirmarActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    private RadioButton esMedicoTitularRadioButton;
    private TextView nombreMedicoOptionTextView;
    private RadioButton esPersonaAutorizadaRadioButton;
    private TextView nombreAutorizadoOptionTextView;
    private Button cancelarButton;
    private Button continuarButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.prepararPantalla();

    }

    private void prepararPantalla(){
        setContentView(R.layout.identificar_persona_afirmar_layout);

        this.nombreMedicoTexView = (TextView)this.findViewById( R.id.nombreMedicoTexView );
        this.nombreMedicoTexView.setText(this.visita.getNombreMedico());
        this.especialidadTextView = (TextView)this.findViewById( R.id.especialidadTextView );
        this.especialidadTextView.setText( this.visita.getEspecialidadMedico());

        this.nombreMedicoOptionTextView = (TextView)this.findViewById( R.id.nombreMedicoOptionTextView );
        this.nombreMedicoOptionTextView.setText(this.visita.getNombreMedico());
        this.nombreAutorizadoOptionTextView = (TextView)this.findViewById( R.id.nombreAutorizadoOptionTextView );
        this.nombreAutorizadoOptionTextView.setText(this.visita.getNombreAutorizado());
        this.esMedicoTitularRadioButton = (RadioButton)this.findViewById( R.id.esMedicoTitularRadioButton );
        this.esPersonaAutorizadaRadioButton = (RadioButton)this.findViewById( R.id.esPersonaAutorizadaRadioButton );


        this.cancelarButton = (Button)this.findViewById( R.id.cancelarButton );
        this.continuarButton = (Button)this.findViewById( R.id.continuarButton );
        this.continuarButton.setOnClickListener( this );
        this.cancelarButton.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_identificar_persona_afirmar, menu);
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
        if( v == cancelarButton ){
            this.finish();
            return;
        }
        else if( v == continuarButton) {
            Intent intent = null;
            if( esPersonaAutorizadaRadioButton.isChecked() ){
                this.visita.setEsAutorizado(RespuestaBinaria.SI);
                intent = new Intent( this , FotografiaAutorizadaActivity.class );
            }else{
                intent = new Intent( this , CapturarPaqueteActivity.class );
            }
            LogUtil.printLog( CLASSNAME , "Es persona Autorizada:" + this.visita.getEsAutorizado().isBoolRespuesta());
            this.visitaService.actualizarVisitaActual();
            this.startActivity( intent );
        }
    }





}
