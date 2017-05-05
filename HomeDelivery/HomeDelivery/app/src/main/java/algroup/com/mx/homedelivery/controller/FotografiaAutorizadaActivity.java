package algroup.com.mx.homedelivery.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.business.utils.RespuestaBinaria;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.CustomUncaughtExceptionHandler;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.Util;
import algroup.com.mx.homedelivery.utils.ViewUtil;
import algroup.com.mx.homedelivery.utils.storage.AlbumStorageDirFactory;
import algroup.com.mx.homedelivery.utils.storage.BaseAlbumDirFactory;
import algroup.com.mx.homedelivery.utils.storage.FroyoAlbumDirFactory;

public class FotografiaAutorizadaActivity extends ActionBarActivity  implements View.OnClickListener {
    private static final String CLASSNAME = FotografiaAutorizadaActivity.class.getSimpleName();

    //Utiles
    private final static int ELEMENTO_VACIO = 0;

    //Services
    private VisitaService visitaService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView nombreMedicoTexView;
    private TextView especialidadTextView;
    private Button tomarFotoFrenteButton;
    private Button eliminarFotoFrenteButton;
    private ImageView fotoFrenteImageView;
    private Button tomarFotoAtrasButton;
    private Button eliminarFotoAtrasButton;
    private ImageView fotoAtrásImageView;
    private Button cancelarButton;
    private Button continuarButton;

    //Elementos para la foto
    private static final int ACTION_TAKE_PHOTO_B = 1;  //Foto GRANDE
    private static final int ACTION_TAKE_PHOTO_S = 2;  //foto pequeña

    private enum TipoFoto {FRENTE, ATRAS}

    ;
    private TipoFoto tipoFotoSeleccionado;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView;
    private Bitmap mImageBitmap;

    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_PROM_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.prepararPantalla();

    }

    private void prepararPantalla() {
        setContentView(R.layout.fotografia_autorizada_layout);

        this.nombreMedicoTexView = (TextView) this.findViewById(R.id.nombreMedicoTexView);
        this.nombreMedicoTexView.setText(this.visita.getNombreMedico());
        this.especialidadTextView = (TextView) this.findViewById(R.id.especialidadTextView);
        this.especialidadTextView.setText(this.visita.getEspecialidadMedico());

        this.tomarFotoFrenteButton = (Button) this.findViewById(R.id.tomarFotoFrenteButton);
        this.eliminarFotoFrenteButton = (Button) this.findViewById(R.id.eliminarFotoFrenteButton);
        this.fotoFrenteImageView = (ImageView) this.findViewById(R.id.fotoFrenteImageView);
        this.tomarFotoAtrasButton = (Button) this.findViewById(R.id.tomarFotoAtrasButton);
        this.eliminarFotoAtrasButton = (Button) this.findViewById(R.id.eliminarFotoAtrasButton);
        this.fotoAtrásImageView = (ImageView) this.findViewById(R.id.fotoAtrásImageView);

        this.cancelarButton = (Button) this.findViewById(R.id.cancelarButton);
        this.continuarButton = (Button) this.findViewById(R.id.continuarButton);

        this.tomarFotoFrenteButton.setOnClickListener(this);
        this.eliminarFotoFrenteButton.setOnClickListener(this);
        this.tomarFotoAtrasButton.setOnClickListener(this);
        this.eliminarFotoAtrasButton.setOnClickListener(this);
        this.continuarButton.setOnClickListener(this);
        this.cancelarButton.setOnClickListener(this);


        this.verificarCondicionesParaMostrarBotones();
        this.prepararImagenYStorage();

    }

    private void verificarCondicionesParaMostrarBotones() {
        LogUtil.printLog(CLASSNAME, "verificarCondicionesParaMostrarBotones");
        this.tomarFotoFrenteButton.setVisibility(View.GONE);
        this.eliminarFotoFrenteButton.setVisibility(View.GONE);
        if (visita.getFotoFrenteBase64().length == ELEMENTO_VACIO) {
            this.tomarFotoFrenteButton.setVisibility(View.VISIBLE);
            fotoFrenteImageView.setBackgroundResource(R.drawable.muestra_credencial);
        } else {
            this.eliminarFotoFrenteButton.setVisibility(View.VISIBLE);
            Bitmap bm = BitmapFactory.decodeByteArray(visita.getFotoFrenteBase64(), 0, visita.getFotoFrenteBase64().length);
            Drawable image = new BitmapDrawable(this.getResources(), bm);
            fotoFrenteImageView.setBackground(image);
        }

        this.tomarFotoAtrasButton.setVisibility(View.GONE);
        this.eliminarFotoAtrasButton.setVisibility(View.GONE);
        if (visita.getFotoAtrasBase64().length == ELEMENTO_VACIO) {
            this.tomarFotoAtrasButton.setVisibility(View.VISIBLE);
            fotoAtrásImageView.setBackgroundResource(R.drawable.muestra_credencial);
        } else {
            this.eliminarFotoAtrasButton.setVisibility(View.VISIBLE);
            Bitmap bm = BitmapFactory.decodeByteArray(visita.getFotoAtrasBase64(), 0, visita.getFotoAtrasBase64().length);
            Drawable image = new BitmapDrawable(this.getResources(), bm);
            fotoAtrásImageView.setBackground(image);
        }

    }

    private void prepararImagenYStorage() {
        //mImageView = (ImageView) findViewById(R.id.imageView1);
        mImageBitmap = null;

        //this.capturarFotoButton = (Button) findViewById(R.id.capturarFotoButton);
//        setBtnListenerOrDisable(
//                this.capturarFotoButton,
//                mTakePicSOnClickListener,
//                MediaStore.ACTION_IMAGE_CAPTURE
//        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verificarCondicionesParaMostrarBotones();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fotografia_autorizada, menu);
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
        if (v == cancelarButton) {
            this.finish();
            return;
        } else if (v == tomarFotoFrenteButton) {
            tipoFotoSeleccionado = TipoFoto.FRENTE;
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
            return;
        } else if (v == tomarFotoAtrasButton) {
            tipoFotoSeleccionado = TipoFoto.ATRAS;
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
            return;
        } else if(v == eliminarFotoFrenteButton){
            this.visita.setFotoFrenteBase64( new byte[0]);
            this.verificarCondicionesParaMostrarBotones();
        }else if(v == eliminarFotoAtrasButton){
            this.visita.setFotoAtrasBase64(new byte[0]);
            this.verificarCondicionesParaMostrarBotones();
        } else if (v == continuarButton) {

            if (visita.getFotoFrenteBase64().length > 0 &&
                    visita.getFotoAtrasBase64().length > 0) {
                Intent intent = new Intent(this, CapturarPaqueteActivity.class);
                this.visitaService.actualizarVisitaActual();
                this.startActivity(intent);
            } else {
                ViewUtil.mostrarMensajeRapido(this, "¡Se requiere contar con las dos fotografías!");
            }
        }
    }


    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //BLOQUE 20150729
        switch (actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch
        //END BLOQUE

        startActivityForResult(takePictureIntent, actionCode);
    }


     /*
    * BLOQUE DE METODOS PARA SOPORTAR IMAGENES GRANDES
    * */


    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }


    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    /* Photo album for this application */
    private String getAlbumName() {
        return "fotos_credencial";
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_TAKE_PHOTO_B: {   //Procesar fotografia GRANDES
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
//                    RevisionFotoListAdapter revisionFotoListAdapter = (RevisionFotoListAdapter) (this.fotografiasListView.getAdapter());
//                    revisionFotoListAdapter.setRevisionFotos( this.visita.getRevisionFoto() );
//                    revisionFotoListAdapter.notifyDataSetChanged();
                }
                break;
            } // ACTION_TAKE_PHOTO_B
            case ACTION_TAKE_PHOTO_S: { //Procesar fotografia peuqeñas
                if (resultCode == RESULT_OK) {
                    guardaFotoEnVisita(data);
//                    RevisionFotoListAdapter revisionFotoListAdapter = (RevisionFotoListAdapter) (this.fotografiasListView.getAdapter());
//                    revisionFotoListAdapter.setRevisionFotos( this.visita.getRevisionFoto() );
//                    revisionFotoListAdapter.notifyDataSetChanged();

                }
                break;
            } // ACTION_TAKE_PHOTO_S
        } // switch
        this.onResume();
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            Bitmap imagenCapturada = setPic();
            guardaFotoGrandeEnVisita( imagenCapturada );
            //galleryAddPic();
            mCurrentPhotoPath = null;
        }
    }

    private Bitmap setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */
        int targetH = 0;   //mImageView.getHeight();
        int targetW = 0;     //mImageView.getWidth();


		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        if( photoH > photoW ){
        /*Se definen las dimensiones de la imagen que se desea generar*/
            targetH = Const.MedidasReduccionImagen.PEQUENA_PORTRAIT.heigh;     // 640;
            targetW = Const.MedidasReduccionImagen.PEQUENA_PORTRAIT.width;     //480;
        }else{
            targetH = Const.MedidasReduccionImagen.PEQUENA_LANDSCAPE.heigh;    //480;
            targetW = Const.MedidasReduccionImagen.PEQUENA_LANDSCAPE.width;    //640;
        }

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 0;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        return bitmap;
    }

    private void guardaFotoEnVisita(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmapCapturado = (Bitmap) extras.get("data");
        byte[] imagenbyte = Util.getArrayByteDeBitMap(mImageBitmapCapturado);
        if( tipoFotoSeleccionado == TipoFoto.FRENTE){
            this.visita.setFotoFrenteBase64( imagenbyte);
        }else if( tipoFotoSeleccionado == TipoFoto.ATRAS){
            this.visita.setFotoAtrasBase64(imagenbyte);
        }
    }

    private void guardaFotoGrandeEnVisita( Bitmap mImageBitmapCapturado) {
        LogUtil.printLog( CLASSNAME , "guardaFotoGrandeEnVisita" );
        byte[] imagenbyte = Util.getArrayByteDeBitMap( mImageBitmapCapturado );
        if( tipoFotoSeleccionado == TipoFoto.FRENTE){
            this.visita.setFotoFrenteBase64( imagenbyte);
        }else if( tipoFotoSeleccionado == TipoFoto.ATRAS){
            this.visita.setFotoAtrasBase64(imagenbyte);
        }
    }

}

