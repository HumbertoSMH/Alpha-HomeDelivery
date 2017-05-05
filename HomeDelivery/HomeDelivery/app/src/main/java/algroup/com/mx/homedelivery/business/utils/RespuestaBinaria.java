package algroup.com.mx.homedelivery.business.utils;

/**
 * Created by devmac02 on 14/07/15.
 */
public enum RespuestaBinaria {
    NO( 0 , false),
    SI( 1 , true);

    private int idRespuesta;
    private boolean boolRespuesta;

    private RespuestaBinaria( int idRespuesta , boolean boolRespuesta){
        this.idRespuesta = idRespuesta;
        this.boolRespuesta = boolRespuesta;
    }

    public int getIdRespuesta() { return idRespuesta; }
    public boolean isBoolRespuesta() { return boolRespuesta; }

    public static String[] recuperarArrayNombresOpcionBinaria(){
        RespuestaBinaria[] values = RespuestaBinaria.values();
        String[] nombres = new String[ values.length ];
        for( int j = 0 ; j < values.length ; j++){
            nombres[j] = values[j].name();
        }
        return nombres;
    }


    public static RespuestaBinaria recuperarRespuestaPorId( int idRespuesta ){
        RespuestaBinaria rbBuscada = null;
        RespuestaBinaria[] values = RespuestaBinaria.values();
        for( RespuestaBinaria item : values ){
            if(item.getIdRespuesta() == idRespuesta ){
                rbBuscada = item;
            }
        }
        return rbBuscada;
    }

}

