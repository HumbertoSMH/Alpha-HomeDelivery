package algroup.com.mx.homedelivery.business.persistence;

/**
 * Created by devmac02 on 09/07/15.
 */
public class Table {

    // To prevent someone from accidentally instantiating the HomeDeliveryPersistence class,
    // give it an empty constructor.
    public Table() {}
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String COMMA_SEP = " , ";
    private static final String SEMICOMMA_SEP = " ; ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String TEXT_DEFINITION_1 = TEXT_TYPE +  COMMA_SEP;
    private static final String TEXT_DEFINITION_2 = TEXT_TYPE  ;
    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE +  COMMA_SEP;
    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE  ;


    /* Inner class that defines the table contents */
    public static enum Rutas {
        COLUMN_NAME_IDRUTA (0 , "idRuta"),
        COLUMN_NAME_FECHAPROGRAMADA( 1 , "fechaProgramada" ),
        COLUMN_NAME_FECHACREACION ( 2 , "fechaCreacion" ),
        COLUMN_NAME_FECHAULTIMAMODIFICACION ( 3 , "fechaUltimaModificacion" ),
        COLUMN_NAME_CLAVEPROMOTOR( 4 , "clavePromotor"),
        COLUMN_NAME_PASSWORDPROMOTOR( 5 , "passwordPromotor");

        public static final String TABLE_NAME = "Rutas";


        public int index;
        public String column;

        private Rutas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Rutas[] rutasArray = Rutas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    /* Inner class that defines the table contents */
    public static enum Visitas {
        COLUMN_NAME_IDVISITA ( 0 , "idVisita"),
        COLUMN_NAME_ESTATUSVISITA(1,"estatusVisita"),
        COLUMN_NAME_CODIGOMEDICO(2, "codigoMedico"),
        COLUMN_NAME_NOMBREMEDICO(3, "nombreMedico"),
        COLUMN_NAME_IDESPECIALIDADMEDICO(4,"idEspecialidadMedico"),
        COLUMN_NAME_ESPECIALIDADMEDICO(5,"especialidadMedico"),
        COLUMN_NAME_DIRECCIONMEDICO(6,"direccionMedico"),
        COLUMN_NAME_FECHACIERRE(7,"fechaCierre"),
        COLUMN_NAME_FIRMAMEDICO(8, "firmaMedico"),
        COLUMN_NAME_NODIOCORREO(9, "noDioCorreo"),
        COLUMN_NAME_EMAILMEDICO(10,"emailMedico"),
        COLUMN_NAME_COMENTARIOS(11, "comentarios"),
        COLUMN_NAME_IDDESCARTE(12,"idDescarte"),
        COLUMN_NAME_DESCRIPCIONDESCARTEOTRO(13, "descripcionDescarteOtro"),
        COLUMN_NAME_IDUBICADO(14, "idUbicado"),
        COLUMN_NAME_DESCRIPCIONUBICADOOTRO(15, "descripcionUbicadoOtro"),
        COLUMN_NAME_CODIGOPAQUETE(16, "codigoPaquete"),
        COLUMN_NAME_PAQUETELOCALIZADO(17, "paqueteLocalizado"),
        COLUMN_NAME_FLUJOCIERRE(18, "flujoDeCierre"),
        //SeccionLocation
        COLUMN_NAME_LATITUD( 19 , "latitud" ),
        COLUMN_NAME_LONGITUD( 20 , "longitud" ),
        COLUMN_NAME_LATITUDCHECKIN( 21 , "latitudCheckIn" ),
        COLUMN_NAME_LONGITUDCHECKIN( 22 , "longitudCheckIn" ),

        //Parametros adicionales al detalle del medico
        COLUMN_NAME_PORTAFOLIO( 23 , "portafolio" ),
        COLUMN_NAME_IDPARRILLA( 24 , "idParrilla" ),
        //DESPARRILLA NUEVO
        COLUMN_NAME_DESPARRILLA(25, "desParrilla"),

        COLUMN_NAME_COORDENADASMEDICO( 26 , "coordenadasMedico" ),
        COLUMN_NAME_ACTUALIZOINFORMACION( 27 , "actualizoInformacion" ),

        COLUMN_NAME_FECHACHECKIN ( 28 , "fechaCheckin" ),

        //VALORES SI ES AUTORIZADO EL MEDICO
        COLUMN_NAME_NOMBREAUTORIZADO( 29 , "nombreAutorizado"),
        COLUMN_NAME_ESAUTORIZADO( 30 , "esAutorizado"),
        COLUMN_NAME_FOTOFRENTE( 31 , "fotoFrente"),
        COLUMN_NAME_FOTOATRAS( 32 , "fotoAtras"),

        //RUTA
        COLUMN_NAME_IDRUTA ( 33 , "idRuta" );

        public static final String TABLE_NAME = "Visita";

        public int index;
        public String column;

        private Visitas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Visitas[] rutasArray = Visitas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static enum PaquetesEntregados {
        COLUMN_NAME_CODIGO_PAQUETE (0 , "codigoPaquete"),
        COLUMN_NAME_ENTREGADO( 1 , "entregado" );

        public static final String TABLE_NAME = "PaquetesEntregadosEnRuta";


        public int index;
        public String column;

        private PaquetesEntregados( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            PaquetesEntregados[] rutasArray = PaquetesEntregados.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static final String SQL_CREATE_TABLE_RUTAS =
            "CREATE TABLE " + Rutas.TABLE_NAME + " (" +
                    Rutas.COLUMN_NAME_IDRUTA.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Rutas.COLUMN_NAME_FECHAPROGRAMADA.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHACREACION.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_CLAVEPROMOTOR.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column + TEXT_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_RUTAS = "DROP TABLE IF EXISTS " + Rutas.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_VISITAS =
            "CREATE TABLE " + Visitas.TABLE_NAME + " (" +
                    Visitas.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Visitas.COLUMN_NAME_ESTATUSVISITA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_CODIGOMEDICO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NOMBREMEDICO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDESPECIALIDADMEDICO.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_ESPECIALIDADMEDICO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_DIRECCIONMEDICO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FECHACIERRE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FIRMAMEDICO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NODIOCORREO.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_EMAILMEDICO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_COMENTARIOS.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDDESCARTE.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_DESCRIPCIONDESCARTEOTRO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDUBICADO.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_DESCRIPCIONUBICADOOTRO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_CODIGOPAQUETE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_PAQUETELOCALIZADO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FLUJOCIERRE.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_LATITUD.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_LONGITUD.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_LATITUDCHECKIN.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_LONGITUDCHECKIN.column + TEXT_DEFINITION_1 +

                    //PARAMETROS ADICIONALES A LA UBICACION DEL MEDICO
                    Visitas.COLUMN_NAME_PORTAFOLIO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDPARRILLA.column + INTEGER_DEFINITION_1 +
                    //DESPARRILLA NUEVO
                    Visitas.COLUMN_NAME_DESPARRILLA.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_COORDENADASMEDICO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_ACTUALIZOINFORMACION.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FECHACHECKIN.column + TEXT_DEFINITION_1 +

                    //Control persona autorizada
                    Visitas.COLUMN_NAME_NOMBREAUTORIZADO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_ESAUTORIZADO.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FOTOFRENTE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FOTOATRAS.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_IDRUTA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_VISISTAS = "DROP TABLE IF EXISTS " + Visitas.TABLE_NAME + " ; ";


    public static final String SQL_CREATE_TABLE_PAQUETES_ENTREGADOS =
            "CREATE TABLE " + PaquetesEntregados.TABLE_NAME + " (" +
                    PaquetesEntregados.COLUMN_NAME_CODIGO_PAQUETE.column + TEXT_DEFINITION_2 + " PRIMARY KEY, " +
                    PaquetesEntregados.COLUMN_NAME_ENTREGADO.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_PAQUETES_ENTREGADOS = "DROP TABLE IF EXISTS " + PaquetesEntregados.TABLE_NAME + " ; ";

}
