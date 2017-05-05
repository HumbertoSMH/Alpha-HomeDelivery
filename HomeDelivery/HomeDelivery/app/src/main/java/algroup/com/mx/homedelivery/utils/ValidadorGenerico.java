package algroup.com.mx.homedelivery.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by MAMM on 17/04/15.
 */
public class ValidadorGenerico {

    private static ValidadorGenerico singleton;
    private Resources resources;
    private Context mContext;

    private static final String EXPRESION_CORREO = "^\\w+([\\.-]?\\w+){4,}@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    private static final String EXPRESION_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$";
    private static final String EXPRESION_NOMBRE = "[a-zA-ZáéíóúñÁÉÍÓÚ ]{3,}";
    //private static final String EXPRESION_TELEFONO = "[0-9]{10}";


    private ValidadorGenerico(Context context) {
        resources = context.getResources();
        mContext = context;
    }

    public static ValidadorGenerico getSingleton(Context context) {
        if (singleton == null) {
            singleton = new ValidadorGenerico(context);
        }
        return singleton;
    }
}
