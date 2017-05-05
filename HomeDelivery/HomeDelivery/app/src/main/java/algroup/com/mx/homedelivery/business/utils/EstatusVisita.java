package algroup.com.mx.homedelivery.business.utils;

/**
 * Created by MAMM on 19/04/15.
 */
public enum EstatusVisita {
    EN_RUTA( 27 , "En ruta" , 0xFFFFFFFF ), //Gris
    ENTREGADA ( 28 , "Entregada" , 0xFFFFFF66 ),  //amarillo
    UBICADO( 29 , "Ubicado" , 0xFFFFFF66), //Rojo
    DESCARTADO( 30 , "Descartado" , 0xFFFFFF66 ),  //verde
    CANCELADA( 47 , "Cancelada" , 0xFFFE506E ),  //Morado
    NO_VISITADA( 31 , "No visitada" , 0xFF473C8B ),
    NO_GUARDADA( 999 , "No guardada" , 0xFFFFAA66 );


    //http://www.color-hex.com/Color

    public static final int EN_RUTA_ID_WEB = 27;
    public static final int ENTREGADO_ID_WEB = 28;
    public static final int UBICADO_ID_WEB = 29;
    public static final int DESCARTADO_ID_WEB = 30;
    public static final int CANCELADA_ID_WEB = 47;
    public static final int NO_VISITADA_ID_WEB = 31;


    private int idEstatus;
    private String nombreEstatus;
    private int color;

    private EstatusVisita(int idEstatus, String nombreEstatus, int color){
        this.idEstatus = idEstatus;
        this.nombreEstatus = nombreEstatus;
        this.color = color;
    }

    public int getIdEstatus() { return idEstatus; }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public int getColor() {
        return color;
    }

    public static EstatusVisita getEstatusVisitaFromId( int idEstatus ){
        EstatusVisita ev = null;
        EstatusVisita[] todosLosEstatus = EstatusVisita.values();
        for( EstatusVisita itemEstatus : todosLosEstatus ){
            if(itemEstatus.getIdEstatus() == idEstatus ){
                ev = itemEstatus;
            }
        }
        return ev;
    }
}
