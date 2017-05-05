package algroup.com.mx.homedelivery.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.controller.validator.MotivosValidator;
import algroup.com.mx.homedelivery.controller.validator.VisitaValidator;
import algroup.com.mx.homedelivery.services.CatalogosServices;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.CatalogosServicesImpl;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;
import algroup.com.mx.homedelivery.utils.ValidadorUIMensajes;
import algroup.com.mx.homedelivery.utils.ViewUtil;


public class UbicadoMedicoActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Services
    private VisitaService visitaService;
    private CatalogosServices catalogosServices;
    private MotivosValidator motivosValidator;

    //Business
    private Visita visita;

    //UI Elements
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    private Spinner ubicadoSpinner;
    private Button cancelarUbicadoButton;
    private Button continuarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.catalogosServices = CatalogosServicesImpl.getSingleton();
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.motivosValidator = MotivosValidator.getSingleton();
        this.prepararPantalla();
    }


    private void prepararPantalla(){
        setContentView(R.layout.ubicado_medico_layout);

        nombreMedicoTexView = (TextView)this.findViewById( R.id.nombreMedicoTexView );
        nombreMedicoTexView.setText(this.visita.getNombreMedico());
        especialidadTextView = (TextView)this.findViewById( R.id.especialidadTextView );
        especialidadTextView.setText( this.visita.getEspecialidadMedico() );

        ubicadoSpinner = (Spinner)this.findViewById( R.id.ubicadoSpinner );
        List<MotivoUbicado> motivosUbicado = this.catalogosServices.getCatalogoMotivoUbicado();
        List<String> motivosStringArray = getMotivosStringArray(motivosUbicado);
        ArrayAdapter<String> motivosAdapter = new ArrayAdapter<String>( this , R.layout.custom_spinner_item, motivosStringArray );
        ubicadoSpinner.setAdapter(motivosAdapter);
        ubicadoSpinner.setOnItemSelectedListener( this );

        cancelarUbicadoButton = (Button)this.findViewById( R.id.cancelarUbicadoButton );
        continuarButton = (Button)this.findViewById( R.id.continuarButton );

        cancelarUbicadoButton.setOnClickListener( this );
        continuarButton.setOnClickListener( this );

    }

    private List<String> getMotivosStringArray( List<MotivoUbicado> motivos ){
        List<String> motivosUbicado = new ArrayList<String>();
        for( MotivoUbicado itemMotivo : motivos){
            motivosUbicado.add( itemMotivo.getIdMotivoRetiro() + "-" + itemMotivo.getDescripcionMotivoRetiro());
        }
        return motivosUbicado;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubicado_medico, menu);
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
        if( v == cancelarUbicadoButton ){
            this.finish();
            return;
        }
        else if( v == continuarButton ){
            ValidadorUIMensajes validator =  this.motivosValidator.
                    validarMotivoUbicado(this.ubicadoSpinner );
            if( validator.isCorrecto() == false ){
                ViewUtil.mostrarMensajeRapido(this, validator.getMensaje());
                return;
            }else{
                int index = this.ubicadoSpinner.getSelectedItemPosition();
                MotivoUbicado motivoUbicado = this.catalogosServices.getCatalogoMotivoUbicado().get(index);
                this.visita.setIdUbicado(motivoUbicado.getIdMotivoRetiro());
            }

            Intent intent = new Intent( this , ComentariosActivity.class );
            this.startActivity(intent );
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MotivoUbicado motivoUbicado = this.catalogosServices.getCatalogoMotivoUbicado().get( position );

        //Se deshabilita la logica para pintar o no el bloque de comentarios
     /*   if( motivoUbicado.getIdMotivoRetiro() == Const.UBICADO_OTRO ){
            this.comentarioEditText.setEnabled(true);
            this.comentarioEditText.setBackgroundColor(0xFFF5F6CE);
        }else{
            this.comentarioEditText.setEnabled( false );
            this.comentarioEditText.setText( "" );
            this.comentarioEditText.setBackgroundColor(Color.TRANSPARENT);

        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
