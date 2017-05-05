package algroup.com.mx.homedelivery.utils;

import android.util.Log;

/**
 * Created by devmac03 on 15/04/15.
 */
public class LogUtil {
    private static final String CLASSNAME = LogUtil.class.getSimpleName();

    /*
    * Genera el Log de la aplicación segun el nivel descrito como parametro
    * */
    public static void printLog( String tag , String message ){
        if( Const.LOG_LEVEL == Log.DEBUG ){
            LogUtil.logDebug(  tag ,  message );
        }
        else if( Const.LOG_LEVEL == Log.INFO ){
            LogUtil.logInfo(  tag ,  message );
        }
        else if( Const.LOG_LEVEL == Log.WARN ){
            LogUtil.logWarn(  tag ,  message );
        }
        else if( Const.LOG_LEVEL == Log.ERROR ){
            LogUtil.logError(  tag ,  message );
            }
    }

    public static void logDebug( String tag , String message){
        Log.d( tag , message );
    }

    public static void logInfo( String tag , String message){
        Log.i( tag , message );
    }

    public static void logWarn( String tag , String message){
        Log.w( tag , message );
    }

    public static void logError( String tag , String message){
        Log.e( tag , message );
    }


}
