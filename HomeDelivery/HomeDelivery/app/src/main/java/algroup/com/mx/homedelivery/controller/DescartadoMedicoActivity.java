package algroup.com.mx.homedelivery.controller;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.controller.validator.MotivosValidator;
import algroup.com.mx.homedelivery.services.CatalogosServices;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.CatalogosServicesImpl;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.MotivoDescartado;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;
import algroup.com.mx.homedelivery.utils.ValidadorUIMensajes;
import algroup.com.mx.homedelivery.utils.ViewUtil;


public class DescartadoMedicoActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Services
    private VisitaService visitaService;
    private CatalogosServices catalogosServices;
    private MotivosValidator motivosValidator;

    //Business
    private Visita visita;

    //UI Elements
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    private Spinner descartadoSpinner;
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

    private void prepararPantalla() {
        setContentView(R.layout.descartado_medico_layout);

        nombreMedicoTexView = (TextView)this.findViewById( R.id.nombreMedicoTexView );
        nombreMedicoTexView.setText( this.visita.getNombreMedico() );
        especialidadTextView = (TextView)this.findViewById( R.id.especialidadTextView );
        especialidadTextView.setText( this.visita.getEspecialidadMedico() );

        descartadoSpinner = (Spinner)this.findViewById( R.id.descartadoSpinner );
        List<MotivoDescartado> motivosUbicado = this.catalogosServices.getCatalogoMotivoDescartado();
        List<String> motivosStringArray = getMotivosStringArray(motivosUbicado);
        ArrayAdapter<String> motivosAdapter = new ArrayAdapter<String>( this , R.layout.custom_spinner_item, motivosStringArray );
        descartadoSpinner.setAdapter( motivosAdapter );
        descartadoSpinner.setOnItemSelectedListener(this );

        cancelarUbicadoButton = (Button)this.findViewById( R.id.cancelarUbicadoButton );
        continuarButton = (Button)this.findViewById( R.id.continuarButton );

        cancelarUbicadoButton.setOnClickListener( this );
        continuarButton.setOnClickListener( this );
    }

    private List<String> getMotivosStringArray( List<MotivoDescartado> motivos ){
        List<String> motivosDescartado = new ArrayList<String>();
        for( MotivoDescartado itemMotivo : motivos){
            motivosDescartado.add( itemMotivo.getIdMotivoDescartado() + "-" + itemMotivo.getDescripcionMotivoDescartado());
        }
        return motivosDescartado;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_descartado_medico, menu);
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
        if( v == cancelarUbicadoButton ){
            this.finish();
            return;
        }
        else if( v == continuarButton ){
            ValidadorUIMensajes validator =  this.motivosValidator.
                    validarMotivoDescartado(this.descartadoSpinner );
            if( validator.isCorrecto() == false ){
                ViewUtil.mostrarMensajeRapido(this, validator.getMensaje());
                return;
            }else{
                int index = this.descartadoSpinner.getSelectedItemPosition();
                MotivoDescartado motivoDescartado = this.catalogosServices.getCatalogoMotivoDescartado().get(index);
                this.visita.setIdDescarte(motivoDescartado.getIdMotivoDescartado());
            }

            Intent intent = new Intent( this , ComentariosActivity.class );
            this.startActivity(intent );
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MotivoDescartado motivoDescartado = this.catalogosServices.getCatalogoMotivoDescartado().get(position);

        //Se deshabilita la logica para pintar o no el bloque de comentarios
        /*if( motivoDescartado.getIdMotivoDescartado() == Const.DESCARTADO_OTRO ){
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
