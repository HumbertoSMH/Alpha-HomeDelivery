package algroup.com.mx.homedelivery.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;
import android.widget.Spinner;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.services.impl.CatalogosServicesImpl;
import algroup.com.mx.homedelivery.services.impl.VisitaServiceImpl;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.MotivoDescartado;
import algroup.com.mx.homedelivery.utils.MotivoUbicado;
import algroup.com.mx.homedelivery.utils.ValidadorGenerico;
import algroup.com.mx.homedelivery.utils.ValidadorUIMensajes;

/**
 * Created by devmac02 on 14/07/15.
 */
public class MotivosValidator {
    private static MotivosValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;



    private MotivosValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
    }

    public static MotivosValidator getSingleton( ){
        if( singleton == null ){
            singleton = new MotivosValidator(HomeDeliveryApp.getCurrentApplication());
        }
        return singleton;
    }

    public ValidadorUIMensajes validarMotivoDescartado( Spinner ubicadoSpinner ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        int index = ubicadoSpinner.getSelectedItemPosition();
        if( index == -1 ){
            validator.setCorrecto( false );
            validator.setMensaje("Seleccione el motivo por el que se retira");
        }

        //Se deshabilita la validación de campo de contexto
        /*else{
            MotivoDescartado motDes = CatalogosServicesImpl.getSingleton().getCatalogoMotivoDescartado().get( index );
            if( motDes.getIdMotivoDescartado() == Const.DESCARTADO_OTRO &&
                    comentarioEditText.getText().toString().trim().length() == 0 ){
                validator.setCorrecto( false );
                validator.setMensaje("Favor de escribir el motivo del retiro");
            }

        }*/

        return validator;
    }

    public ValidadorUIMensajes validarMotivoUbicado( Spinner ubicadoSpinner ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        int index = ubicadoSpinner.getSelectedItemPosition();
        if( index == -1 ){
            validator.setCorrecto( false );
            validator.setMensaje("Seleccione el motivo por el que se retira");
        }

       //Ya no se valida el motivo ubicado
       /* else{
            MotivoUbicado motUb = CatalogosServicesImpl.getSingleton().getCatalogoMotivoUbicado().get( index );
            if( motUb.getIdMotivoRetiro() == Const.UBICADO_OTRO &&
                    comentarioEditText.getText().toString().trim().length() == 0 ){
                validator.setCorrecto( false );
                validator.setMensaje("Favor de escribir el motivo del retiro");
            }

        }*/

        return validator;
    }

}