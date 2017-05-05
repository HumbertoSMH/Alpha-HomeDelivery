package algroup.com.mx.homedelivery.controller;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.utils.RespuestaBinaria;
import algroup.com.mx.homedelivery.controller.validator.VisitaValidator;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.ValidadorUIMensajes;
import algroup.com.mx.homedelivery.utils.ViewUtil;


public class FirmaMedicoActivity extends ActionBarActivity implements View.OnClickListener {

    //Services
    private VisitaService visitaService;
    private VisitaValidator visitaValidator;

    //Business
    private Visita visita;

    //UI Elements
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    //private EditText codigoEditText;
    private CheckBox NoDioCorreoCheckBox;
    private EditText emailMedicoEditText;
    private Button cancelarPedidoButton;
    private Button continuarButton;


    //UI Elements for sign
    LinearLayout mContent;
    signature mSignature;
    //Button mClear, mGetSign, mCancel;
    Button mClear;
    public static String tempDir;
    public int count = 1;
    public String current = null;
    private Bitmap mBitmap;
    View mView;
    File mypath;

    private String uniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.visitaValidator = VisitaValidator.getSingleton();
        this.prepararPantalla();
    }

    private void prepararPantalla(){
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        setContentView(R.layout.firma_medico_layout);

        this.nombreMedicoTexView = (TextView)findViewById( R.id.nombreMedicoTexView);
        this.nombreMedicoTexView.setText( this.visita.getNombreMedico() );

        this.especialidadTextView = (TextView)findViewById( R.id.especialidadTextView);
        this.especialidadTextView.setText(this.visita.getEspecialidadMedico());

        //this.codigoEditText = (EditText)findViewById( R.id.codigoPaqueteEditText);
        //this.codigoEditText.setText( this.visita.getCodigoPaquete());
        this.emailMedicoEditText = (EditText)findViewById( R.id.emailMedicoEditText);
        this.emailMedicoEditText.setText(this.visita.getEmailMedico());

        this.NoDioCorreoCheckBox = (CheckBox)findViewById( R.id.NoDioCorreoCheckBox );

        this.cancelarPedidoButton = (Button)findViewById( R.id.cancelarPedidoButton);
        this.continuarButton = (Button)findViewById( R.id.continuarButton);

        this.cancelarPedidoButton.setOnClickListener( this );
        this.continuarButton.setOnClickListener( this );

        this.preparaFuncionFirma();

    }


    private void preparaFuncionFirma() {

        tempDir = Environment.getExternalStorageDirectory() + "/" + "FirmaTemporal"  + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir( "FirmaTemporal"  , Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath= new File(directory,current);


        mContent = (LinearLayout) findViewById(R.id.capturarFirmaLinearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mClear = (Button)findViewById(R.id.limpiarButton);
        //mGetSign = (Button)findViewById(R.id.getsign);
        //mGetSign.setEnabled(false);
        //mCancel = (Button)findViewById(R.id.cancel);
        mView = mContent;

        //yourName = (EditText) findViewById(R.id.yourName);

        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_firma_medico, menu);
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
        if( v == cancelarPedidoButton ){
//            ViewUtil.mostrarMensajeRapido( this , "El reporte se ha concluido sin firma");
            this.finish();
            /*Intent intent = new Intent( this , MedicosListaActivity.class );
            //Verificar 'POSICION_VISITA'
            intent.putExtra( Const.ParametrosIntent.POSICION_VISITA.getNombre() , this.visita.getIdVisita() );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
            return;
        }
        else if( v == continuarButton ){
            boolean existenErrores = this.realizarValidacionesAntesDeContinuar();
            if( existenErrores == false ){
                //this.visita.setCodigoPaquete(codigoEditText.getText().toString());
                this.visita.setEmailMedico(emailMedicoEditText.getText().toString());
                this.visita.setFirmaMedico(this.recuperarFirmaEnBase64());
                this.visitaService.actualizarVisitaActual();

//                Intent intent = new Intent( this , ComentariosActivity.class);
                Intent intent = new Intent( this , TextoNegacionActivity.class);
                this.startActivity(intent);
            }else {
                //No se realiza nada
            }


        }
    }

    private boolean realizarValidacionesAntesDeContinuar() {
        Boolean existenErrores = false;
//        ValidadorUIMensajes userValidatorMessage = visitaValidator.validarCodigoPaquete(codigoEditText);
//        if( userValidatorMessage.isCorrecto() == false ){
//            codigoEditText.setError( userValidatorMessage.getMensaje() );
//            existenErrores = true;
//        }

        if( NoDioCorreoCheckBox.isChecked() == false ){
            ValidadorUIMensajes userValidatorMessage = visitaValidator.validarCorreoElectronico(emailMedicoEditText);
            if( userValidatorMessage.isCorrecto() == false ){
                emailMedicoEditText.setError( userValidatorMessage.getMensaje() );
                existenErrores = true;
            }
        }else{
            this.visita.setNoDioCorreo(RespuestaBinaria.SI );
        }

        if( mSignature.contieneFirma() == false ){
            existenErrores = true;
            ViewUtil.mostrarMensajeRapido(this, "No se detecta la firma");
        }

        return existenErrores;
    }

    private byte[] recuperarFirmaEnBase64() {
        Log.v("log_tag", "Panel Saved");
        String imagenBase64 = null;
        byte[] imageByteArray = null;
        mView.setDrawingCacheEnabled(true);
        imageByteArray = mSignature.recuperarImagenBase64(mView);

        return imageByteArray;
    }

    private void limpiarCache() {
        Log.v("log_tag", "Panel Saved");
        //boolean error = captureSignature();
        mView.setDrawingCacheEnabled(true);
        mSignature.recuperarImagenBase64(mView);
        Bundle b = new Bundle();
        b.putString("status", "done");
        Intent intent = new Intent();
        intent.putExtras(b);
        setResult(RESULT_OK,intent);
    }

    //TODO MAMM Volver una utileria
    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:", String.valueOf(todaysDate));
        return(String.valueOf(todaysDate));

    }

    //TODO MAMM Volver una utileria
    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));

    }


    //TODO MAMM Volver una utileria
    private boolean prepareDirectory()
    {
        try
        {
            if (makedirs())
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //TODO MAMM Volver una utileria
    private boolean makedirs()
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory())
        {
            File[] files = tempdir.listFiles();
            for (File file : files)
            {
                if (!file.delete())
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }



    //INNER CLASS
    public class signature extends View
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public byte[] recuperarImagenBase64(View v)
        {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            String imagenBase64 = null;
            byte[] byteArray = null;
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {

                v.draw(canvas);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();


            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
            return byteArray;
        }

        public void clear()
        {
            path.reset();
            invalidate();
        }

        public boolean contieneFirma(){
            return !path.isEmpty();
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float eventX = event.getX();
            float eventY = event.getY();
            //mGetSign.setEnabled(true);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++)
                    {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY)
        {
            if (historicalX < dirtyRect.left)
            {
                dirtyRect.left = historicalX;
            }
            else if (historicalX > dirtyRect.right)
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top)
            {
                dirtyRect.top = historicalY;
            }
            else if (historicalY > dirtyRect.bottom)
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY)
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }


}
