package algroup.com.mx.homedelivery.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.controller.validator.VisitaValidator;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.ViewUtil;


public class TextoNegacionActivity extends ActionBarActivity implements View.OnClickListener{

    //Services
    private VisitaService visitaService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
//    private ScrollView negacionScrollView;
//    private TextView negacionTextView;
    private Button cancelarPedidoButton;
    private Button continuarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.texto_negacion_layout);

        this.nombreMedicoTexView = (TextView)findViewById( R.id.nombreMedicoTexView);
        this.nombreMedicoTexView.setText( this.visita.getNombreMedico() );

        this.especialidadTextView = (TextView)findViewById( R.id.especialidadTextView);
        this.especialidadTextView.setText(this.visita.getEspecialidadMedico());

        this.cancelarPedidoButton = (Button)findViewById( R.id.cancelarPedidoButton);
        this.continuarButton = (Button)findViewById( R.id.continuarButton);

        this.cancelarPedidoButton.setOnClickListener( this );
        this.continuarButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if( v == cancelarPedidoButton ){
            this.finish();
            return;
        }
        else if( v == continuarButton ){
                Intent intent = new Intent( this , ComentariosActivity.class);
                this.startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_texto_negacion, menu);
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
}
