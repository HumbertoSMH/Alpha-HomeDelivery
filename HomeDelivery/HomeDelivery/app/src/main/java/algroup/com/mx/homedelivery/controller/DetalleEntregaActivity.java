package algroup.com.mx.homedelivery.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Medicamento;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.controller.adapter.DetalleMedicamentoListAdapter;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;


public class DetalleEntregaActivity extends ActionBarActivity implements View.OnClickListener {

    //Services
    private VisitaService visitaService;

    //Business
    private Visita visita;

    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    private TextView codigoPaqueteTextView;
    private ListView productosListView;
    private Button cancelarEntregaButton;
    private Button guardarEntregaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.prepararPantalla();

    }

    private void prepararPantalla(){
        setContentView(R.layout.detalle_entrega_layout);

        nombreMedicoTexView = (TextView)this.findViewById( R.id.nombreMedicoTexView );
        nombreMedicoTexView.setText( this.visita.getNombreMedico());
        especialidadTextView = (TextView)this.findViewById( R.id.especialidadTextView );
        especialidadTextView.setText( this.visita.getEspecialidadMedico());
        codigoPaqueteTextView = (TextView)DetalleEntregaActivity.this.findViewById( R.id.codigoPaqueteTextView );
        codigoPaqueteTextView.setText( "Código Paquete:"  + this.visita.getCodigoPaquete() );
        productosListView = (ListView)this.findViewById( R.id.productosListView );
        cancelarEntregaButton = (Button)this.findViewById( R.id.cancelarEntregaButton );
        guardarEntregaButton = (Button)this.findViewById( R.id.guardarEntregaButton );

        this.productosListView.setAdapter( new DetalleMedicamentoListAdapter( this.visita.getMedicamentos() , this ) );

        this.cancelarEntregaButton.setOnClickListener( this );
        this.guardarEntregaButton.setOnClickListener( this );



    }

//    private List<String> recuperarStringArrayDetalleEntrega(Medicamento[] medicamentos) {
//        List<String> detalleEntrega = new ArrayList<String>();
//
//        for(Medicamento itemMedicamento : medicamentos ){
//            StringBuilder sb = new StringBuilder();
//            sb.append( "(" + itemMedicamento.getCantidad() + ") ");
//            sb.append( itemMedicamento.getNombreMedicamento() + " " );
//            sb.append( itemMedicamento.getCantidad() + " " );
//            sb.append( "\nLote:" + itemMedicamento.getLote() + " " );
//            sb.append( "\nCaducidad:" + itemMedicamento.getFechaCaducidad() + " " );
//            detalleEntrega.add( sb.toString() );
//        }
//        return detalleEntrega;
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_entrega, menu);
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
        if( v == cancelarEntregaButton ){
            this.finish();
            return;
        }
        else if( v == guardarEntregaButton) {
            Intent intent = new Intent( this , FirmaMedicoActivity.class );
            this.startActivity( intent );
        }
    }
}
