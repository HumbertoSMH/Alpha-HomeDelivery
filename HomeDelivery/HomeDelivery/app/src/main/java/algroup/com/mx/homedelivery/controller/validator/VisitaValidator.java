package algroup.com.mx.homedelivery.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.services.VisitaService;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.ValidadorGenerico;
import algroup.com.mx.homedelivery.utils.ValidadorUIMensajes;

/**
 * Created by devmac02 on 14/07/15.
 */
public class VisitaValidator {

    private static VisitaValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;
    private VisitaService visitaService;

    private VisitaValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
        visitaService = VisitaServiceImpl.getSingleton( );
    }

    public static VisitaValidator getSingleton( ){
        if( singleton == null ){
            singleton = new VisitaValidator(HomeDeliveryApp.getCurrentApplication());
        }
        return singleton;
    }

    public ValidadorUIMensajes validarCodigoPaquete( EditText codigoPaqueteEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String codigoPaquete = codigoPaqueteEditText.getText().toString();
        if( codigoPaquete.trim().length() == 0){
            validator.setMensaje( "Capture el código" );
            validator.setCorrecto( false );
            return validator;
        }
        
        return validator;
    }


    public ValidadorUIMensajes validarCorreoElectronico( EditText correoElectronicoEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String email = correoElectronicoEditText.getText().toString();
        if( email.trim().length() == 0){
            validator.setMensaje( "Capture el email" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }

}
