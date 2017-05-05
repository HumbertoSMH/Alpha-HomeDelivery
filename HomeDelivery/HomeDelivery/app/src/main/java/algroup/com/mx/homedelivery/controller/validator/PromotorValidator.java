package algroup.com.mx.homedelivery.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.utils.ValidadorGenerico;
import algroup.com.mx.homedelivery.utils.ValidadorUIMensajes;


/**
 * Created by MAMM on 17/04/15.
 */
public class PromotorValidator {

    private static PromotorValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;

    private PromotorValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
    }

    public static PromotorValidator getSingleton( ){
        if( singleton == null ){
            singleton = new PromotorValidator(HomeDeliveryApp.getCurrentApplication());
        }
        return singleton;
    }

    public ValidadorUIMensajes validarUsuario( EditText usuarioEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String usuarioText = usuarioEditText.getText().toString();
        if( usuarioText.trim().length() == 0){
            validator.setMensaje( "El usuario no puede esta vacio" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }


    public ValidadorUIMensajes validarPassword( EditText passwordEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String passwordText = passwordEditText.getText().toString();
        if( passwordText.trim().length() == 0){
            validator.setMensaje( "El password no puede esta vacio" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }
}
