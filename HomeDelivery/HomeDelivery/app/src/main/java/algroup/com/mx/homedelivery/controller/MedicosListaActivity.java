package algroup.com.mx.homedelivery.controller;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Promotor;
import algroup.com.mx.homedelivery.business.Ruta;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.utils.EstatusVisita;
import algroup.com.mx.homedelivery.controller.adapter.MedicosListAdapter;
import algroup.com.mx.homedelivery.services.CatalogosServices;
import algroup.com.mx.homedelivery.services.PromotorService;
import algroup.com.mx.homedelivery.services.RutaService;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.CatalogosServicesImpl;
import algroup.com.mx.homedelivery.services.impl.PromotorServiceImpl;
import algroup.com.mx.homedelivery.services.impl.RutaServiceImpl;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.ViewUtil;


public class MedicosListaActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, android.support.v7.widget.SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnCloseListener {

    private final static String CLASSNAME = MedicosListaActivity.class.getSimpleName();

    //Services
    private PromotorService promotorService;
    private VisitaService visitaService;
    private RutaService rutaService;
    private CatalogosServices catalogosServices;

    //business
    private Promotor promotorActual;


    //UI ELements
    private TextView usuarioMedicosTextView;
    private TextView fechaRutaTextView;
    private ListView medicosListView;
    private TextView mensajeSinPaquetesTextView;
    private TextView coordenadasMedicoTextView;
    private Button salirMedicosButton;


    public static final int CONTACT_QUERY_LOADER = 0;
    public static final String QUERY_KEY = "query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.promotorService = PromotorServiceImpl.getSingleton();
        this.rutaService = RutaServiceImpl.getSingleton();
        this.catalogosServices = CatalogosServicesImpl.getSingleton();
        this.promotorActual = this.promotorService.getPromotorActual();
        this.prepararPantalla();

        if (getIntent() != null) {
            handleIntent(getIntent());
        }
    }


   private void prepararPantalla(){
       setContentView(R.layout.medicos_lista_layout);
       this.usuarioMedicosTextView = (TextView) findViewById( R.id.usuarioMedicosTextView );
       //Ruta orderTrabajo = this.visitaService.getRutaActual();
       //Ruta orderTrabajo = this.visitaService.getRutaActual();
       Ruta orderTrabajo = this.rutaService.refrescarRutaDesdeBase( this.visitaService.getRutaActual() );

       this.usuarioMedicosTextView.setText( "Bienvenido: " + this.promotorActual.getUsuario()  );

       this.fechaRutaTextView = (TextView) findViewById( R.id.fechaRutaTextView );
       this.fechaRutaTextView.setText(orderTrabajo.getFechaCreacion().substring(0, 10));

       findViewById(R.id.salirMedicosButton).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MedicosListaActivity.this, MainLogin.class);
               intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity( intent );
           }
       });

       this.medicosListView = (ListView) findViewById(R.id.medicosListView);
       this.medicosListView.setOnItemClickListener(this);

       Visita[] visitas = orderTrabajo.getVisitas();
       this.medicosListView.setAdapter(new MedicosListAdapter(visitas, this));

       //AJUSTE 20150811
       //Si no hay paquetes cargados se avisa
       Set<String> clavesPaquetes = this.catalogosServices.getTodosLosCodigosDePaquetesRegistrados();
       this.mensajeSinPaquetesTextView = (TextView)findViewById( R.id.mensajeSinPaquetesTextView);
       if( clavesPaquetes == null ||
               clavesPaquetes.isEmpty() ){
           ViewUtil.mostrarMensajeRapido( this , "¡No existen paquetes cargados!" );
           mensajeSinPaquetesTextView.setVisibility(View.VISIBLE);
       }

   }


    /**
     * Assuming this activity was started with a new intent, process the incoming information and
     * react accordingly.
     * @param intent
     */
    private void handleIntent(Intent intent) {
        // Special processing of the incoming intent only occurs if the if the action specified
        // by the intent is ACTION_SEARCH.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // SearchManager.QUERY is the key that a SearchManager will use to send a query string
            // to an Activity.
            String query = intent.getStringExtra(SearchManager.QUERY);

            // We need to create a bundle containing the query string to send along to the
            // LoaderManager, which will be handling querying the database and returning results.
            Bundle bundle = new Bundle();
            bundle.putString(QUERY_KEY, query);

            //ContactablesLoaderCallbacks loaderCallbacks = new ContactablesLoaderCallbacks(this);

            // Start the loader with the new query, and an object that will handle all callbacks.
            //getLoaderManager().restartLoader(CONTACT_QUERY_LOADER, bundle, loaderCallbacks);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_medicos_lista, menu);


        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search_medicos).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_medicos_lista, menu);

        MenuItem searchItem = menu.findItem(R.id.search_medicos);
        android.support.v7.widget.SearchView mSearchView = (android.support.v7.widget.SearchView) MenuItemCompat
                .getActionView(searchItem);

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
        mSearchView.setSubmitButtonEnabled(true);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.printLog(CLASSNAME, "onItemClick position:" + position + ", id:" + id);
//        if( this.esValidoEntrarAVisita(position) == true){
            Intent intent = new Intent(MedicosListaActivity.this, MedicoMenuActivity.class);
            intent.putExtra( Const.ParametrosIntent.ID_VISITA.getNombre() , id );
            this.startActivity( intent );
//        }else{
//            ViewUtil.mostrarMensajeRapido(this, "No se puede ingresar a la visita seleccionada, verifique su estatus");
//        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        //Visita[] visitas = orderTrabajo.getVisitas();
        this.filtrarMedicosAMostrar(s);
        return false;
    }


    @Override
    public boolean onQueryTextChange(String s) {
        //No se ejecuta ninguna acción
        return false;
    }

    @Override
    public boolean onClose() {
        //ViewUtil.mostrarMensajeRapido( this , "onClose" + new Date() );
        this.filtrarMedicosAMostrar( "" );
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.prepararPantalla();
    }

    //    private boolean esValidoEntrarAVisita( int position ) {
////        boolean esValido = true;
//          boolean esValido = false;
//          //Visita visitaEnEstatusCheckIn = recuperarVisitaEnEstatusCheckIn();
//          Visita visitaSeleccionada = this.visitaService.getRutaActual().getVisitas()[ position ];
//
//          if( visitaSeleccionada.getEstatusVisita() == EstatusVisita.EN_RUTA ){
//              esValido = true;
//          }else
//          //if( visitaEnEstatusCheckIn == null ){
//            if( visitaSeleccionada.getEstatusVisita() == EstatusVisita.UBICADO ){
//                  esValido = true;
//             // }
//          }else
//            if( visitaSeleccionada.getEstatusVisita() == EstatusVisita.DESCARTADO ){
//                esValido = true;
//            }
//          /*if( visitaEnEstatusCheckIn != null ){
//              if( visitaEnEstatusCheckIn == visitaSeleccionada){
//                  esValido = true;
//              }
//          }*/
//        return esValido;
//    }

    /*private Visita recuperarVisitaEnEstatusCheckIn( ){
        Visita[] visitas = this.visitaService.getRutaActual().getVisitas();
        Visita visitaEnCheckIn = null;
        for( Visita itemVisita : visitas ){
            if(itemVisita.getEstatusVisita() == EstatusVisita.EN_RUTA ){
                visitaEnCheckIn = itemVisita;
                break;
            }
        }
        return visitaEnCheckIn;
    }*/

    private void filtrarMedicosAMostrar(String textoAFiltrar) {

        Ruta orderTrabajo = this.rutaService.refrescarRutaDesdeBase( this.visitaService.getRutaActual() );
        Visita[] visitas = orderTrabajo.getVisitas();

        if( textoAFiltrar.equals( "" ) == false ){
            List<Visita> visitasFiltradas = new ArrayList<Visita>();
            if( visitas != null){
                for( Visita itemVisita : visitas){
                    if(itemVisita.getNombreMedico().toLowerCase().contains( textoAFiltrar.toLowerCase() )){
                        visitasFiltradas.add( itemVisita );
                    }
                }
            }
            this.medicosListView.setAdapter(new MedicosListAdapter(visitasFiltradas.toArray( new Visita[0] ), this));
        }else{
            this.medicosListView.setAdapter(new MedicosListAdapter(visitas, this));
        }
    }


}
