package algroup.com.mx.homedelivery.utils;

import android.util.Log;

/**
 * Clase de constantes y parametros de la aplicación
 *
 * Created by MAMM on 15/04/15.
 *
 */
public class Const {

    /*Nivel de logger*/
    public static final int LOG_LEVEL = Log.INFO;

    public enum Enviroment{
        MOCK,
        FAKE,
        DES,
        DIS;

        public static Enviroment currentEnviroment = MOCK;
    }

    public static final int POSICION_CERO = 0;
    public static final int POSICION_UNO = 1;
    public static final int POSICION_DOS = 2;
    public static final int POSICION_TRES = 3;
    public static final int POSICION_CUATRO = 4;
    public static final int POSICION_CINCO = 5;
    public static final int POSICION_SEIS = 6;
    public static final int POSICION_SIETE = 7;
    public static final int POSICION_OCHO = 8;
    public static final int POSICION_NUEVE = 9;
    public static final int POSICION_DIEZ = 10;
    public static final int POSICION_ONCE = 11;
    public static final int POSICION_DOCE = 12;
    public static final int POSICION_TRECE = 13;
    public static final int POSICION_CATORCE = 14;
    public static final int POSICION_QUINCE = 15;
    public static final int POSICION_DIECISEIS = 16;
    public static final int POSICION_DIECISIETE = 17;
    public static final int POSICION_DIECIOCHO = 18;
    public static final int POSICION_DIECINUEVE = 19;

    public static final int LONGITUD_ARRAY_VACIO = 0;
    public static final int CANTIDAD_MINIMA_FOTOS = 3;
    public static final int ID_MOTIVO_RETIRO_OTRO = 999;


    public static final int UBICADO_OTRO = 79;
    public static final int DESCARTADO_OTRO = 75;

    public static final String[][] VOWELS = new String[][] {
            { "á", "é", "í", "ó", "ú", "Á", "É", "Í", "Ó", "Ú", "ñ" , "Ñ" , "ü" , "Ü" },
            { "a", "e", "i", "o", "u", "A", "E", "I", "O", "U" , "n" , "N" , "u" , "U"} };

    public enum ParametrosIntent{
        POSICION_VISITA( "posicionVisita" , 1),
        ID_VISITA( "idVisita" , 2),
        XXXXXX2( "xxx2" , 2),
        XXXXXX3( "xxx3" , 2);

        private String nombre;
        private int idParametro;
        private ParametrosIntent( String nombre , int idParametro ){
            this.nombre = nombre;
            this.idParametro = idParametro;
        }

        public String getNombre() { return nombre; }

        public int getIdParametro() {
            return idParametro;
        }
    }

    public enum TipoDespliegueActivity{
        ALTA,
        CONSULTA;
    }

    public enum MedidasReduccionImagen{
        GRANDE_PORTRAIT( 2424 ,3232 , "Medidas aproximadas WxH:2424x3232 , pesos en Megas: 2M" ),
        MEDIANA_PORTRAIT( 1212 , 1616 , "Medidas aproximadas WxH: 1212x1616 , pesos en Megas: 1M" ),
        PEQUENA_PORTRAIT(  606 , 808 , "Medidas aproximadas WxH: 606x808 , pesos en Megas: 0.3M" ),
        GRANDE_LANDSCAPE( 3232 , 2424 , "Medidas aproximadas WxH:3232x2424 , pesos en Megas: 2M" ),
        MEDIANA_LANDSCAPE( 1616 , 1212 ,  "Medidas aproximadas WxH: 1616x1212 , pesos en Megas: 1M" ),
        PEQUENA_LANDSCAPE(  808 , 606 ,  "Medidas aproximadas WxH: 808x606 , pesos en Megas: 0.3M" );

        public int width;
        public int heigh;
        public String descripcion;


        MedidasReduccionImagen(  int width , int heigh ,  String descripcion ){
            this.width = width;
            this.heigh = heigh;
            this.descripcion = descripcion;
        }
    }

    public enum UrlPromotoriaWeb{
        URL_LOGIN( "ValidarLoginPromotor" ),
        URL_RUTA( "ObtenerRutaPromotor" ),
        URL_CIERRE_VISITA( "checkin" );

        private String url;

        private UrlPromotoriaWeb( String url){
            this.url = url;
        }

        public String getUrl() {
            return PATH + url;
        }

        public static final String PATH = "http://services.alphagroup.mx/AlphaMerchandising.svc/";

    }




    public enum VersionAPK{
        VER_1_1_0( "1.1.0" , "Versión inicial de la aplicación móvil" ),
        VER_1_2_0( "1.2.0" , "Soporte offline, codigo de barras, vista del listado de paquetes" ),
        VER_1_2_1( "1.2.1" , "Reglas para ubicado, reglas en el armado del keyPaquete(idportafolio y idEspecialidad)" ),
        VER_1_2_2( "1.2.2" , "Se cierra el objeto cursor del sqlite para evitar fallos en el manejo de la base de datos" ),
        VER_1_2_3( "1.2.3" , "Se agrega validacion para saber si existe el paquete en catalogo, y se elimina la validacion de portafolio en el mismo." ),
        VER_1_2_4( "1.2.4" , "Se modifican las variables idPortafolio por idParrilla tanto para el medico y el paquete. Se habilita la validación sobre los 3 elementos ( paquete, idParrilla, idEspecialidad)" ),
        VER_1_2_5( "1.2.5" , "Se habilita validación para no actualizar la ubicación si son visitas no guardadas, y se enviar directo al cierre de la visita si esta en estatus NO GUARDADO y se trata de un flujo de UBICADO: 20150807" ),
        VER_1_2_6( "1.2.6" , "Se habilita mensaje para indicar que no se cuentan con paquetes cargados al realizar login: 20150811" ),
        VER_1_2_7( "1.2.7" , "Se agrego el campo 'desParrilla' y el logo de Pfizer: 20150904" ),
        VER_1_2_8( "1.2.8" , "Se agrego busqueda de medico, ordenamiento, campo nodiocorreo, se pinta el correo electronico, soporte a checkin y fuera de linea" ),
        VER_1_2_9( "1.2.9" , "Se ajustaron las funcionalidades, y corrigieron las funcionalidades" ),
        VER_1_3_0( "1.3.0" , "Ajuste en la localización en seccion de menu, caja de texto cortado, se deshabilita la caja de texto para motivo otro en ubicado y descartado, se ajustan los flujos del estatus de la visita en el menu para no permitir editarlos y para soportar que ubicado pueda realizar un entregador " ),
        VER_1_3_1( "1.3.1" , "Se incorpora la funcioncalidad de persona autorizada para firmar, Se agrege pantalla para elegir entre medico y persona autorizada, y se habilita la pantalla para tomar las fotografias" ),
        VER_1_3_2( "1.3.2" , "Se ajusta para actualizar a Entregado despues de un ubicado, cuando no se tenia conexión, ya que no permitia la actualización a Entregado" );

        public String version;
        public String descripcion;
        VersionAPK( String version , String descripcion ){
            this.version = version;
            this.descripcion = descripcion;
        }

        public static VersionAPK getUltimaVersion() {
            VersionAPK[]  versiones = VersionAPK.values();
            return versiones[ versiones.length -1 ];
        }
    }


    public static final int FLUJO_ENTREGADO_ID = 1;
    public static final int FLUJO_UBICADO_ID = 2;
    public static final int FLUJO_DESCARTADO_ID = 3;

    public enum FlujoDeCierre{
        FLUJO_ENTREGADO( FLUJO_ENTREGADO_ID , "Entregado"),
        FLUJO_UBICADO( FLUJO_UBICADO_ID  , "Ubicado"),
        FLUJO_DESCARTADO( FLUJO_DESCARTADO_ID , "Descartado");

        public final int idFlujo;
        public final String nombre;

        private FlujoDeCierre( int idFlujo , String nombre){
            this.idFlujo = idFlujo;
            this.nombre = nombre;
        }
    }


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String PATH = "/data/data/algroup.com.mx.homedelivery/databases/";
    public static final String DATABASE_NAME = "HomeDeliveryDB.db";

}
