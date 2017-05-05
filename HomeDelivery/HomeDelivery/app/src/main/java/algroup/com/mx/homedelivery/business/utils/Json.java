package algroup.com.mx.homedelivery.business.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import algroup.com.mx.homedelivery.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class Json {

    private static Map<ERROR_JSON, String> mapMSG;

    public static enum ERROR_JSON {
        LOGIN(1),
        CERRAR_VISITA(2),
        OBTENER_RUTA_PROMOTOR(3) ;

        private int idError;

        private ERROR_JSON(int idError) {
            this.idError = idError;
        }

        public int getIdError() {
            return idError;
        }
    }

    public static <U> U parseJson(String jsonRequest, Class<U> classType)
            throws Exception {
        U classResult = null;
        ObjectMapper mapper = new ObjectMapper();
        classResult = mapper.readValue(jsonRequest, classType);
        return classResult;
    }

    public static void solicitarMsgError( Response response, ERROR_JSON error){

        if ( mapMSG == null ) {
            mapMSG = new HashMap<>();
        }
        mapMSG.put(error, response.getMensaje());
    }

    public  static  String getMsgError(ERROR_JSON error_json){

        if (mapMSG != null){
            String msg = mapMSG.get( error_json );
            if (msg != null){
                mapMSG.remove( error_json );
                return msg;
            }
        }
        return null;
    }

}
