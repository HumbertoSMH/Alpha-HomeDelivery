package algroup.com.mx.homedelivery.business.persistence;

/**
 * Created by devmac02 on 23/07/15.
 */
public class TablaCatalogos {

    // To prevent someone from accidentally instantiating the HomeDeliveryPersistence class,
    // give it an empty constructor.
    public TablaCatalogos() {}
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String COMMA_SEP = " , ";
    private static final String SEMICOMMA_SEP = " ; ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String TEXT_DEFINITION_1 = TEXT_TYPE +  COMMA_SEP;
    private static final String TEXT_DEFINITION_2 = TEXT_TYPE  ;
    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE +  COMMA_SEP;
    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE  ;

    public static enum Medicamentos {
        COLUMN_NAME_IDMEDICAMENTO ( 0 , "idMedicamento"),
        COLUMN_NAME_NOMBREMEDICAMENTO ( 1 , "nombreMedicamento"),
        COLUMN_NAME_CANTIDAD ( 2 , "cantidad" ),
        COLUMN_NAME_LOTE ( 3 , "lote" ),
        COLUMN_NAME_FECHACADUCIDAD (4 , "fechaCaducidad" ),

        COLUMN_NAME_CODIGOPAQUETE (5, "codigoPaquete");


        public static final String TABLE_NAME = "Medicamentos";

        public int index;
        public String column;

        private Medicamentos( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Medicamentos[] medArray = Medicamentos.values();
            int size = medArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = medArray[j].column;
            }
            return columnas;
        }
    }

    public static enum Ubicado {
        COLUMN_NAME_IDUBICADO( 0 , "idUbicado"),
        COLUMN_NAME_DESCRIPCIONUBICADO ( 1 , "descripcionUbicado");

        public static final String TABLE_NAME = "Ubicado";

        public int index;
        public String column;

        private Ubicado( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Ubicado[] udicadoArray = Ubicado.values();
            int size = udicadoArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = udicadoArray[j].column;
            }
            return columnas;
        }
    }

    public static enum Descartado {
        COLUMN_NAME_IDDESCARTADO( 0 , "idDescartado"),
        COLUMN_NAME_DESCRIPCIONDESCARTADO ( 1 , "descripcionDescartado");

        public static final String TABLE_NAME = "Descartado";

        public int index;
        public String column;

        private Descartado( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Descartado[] descartadoArray = Descartado.values();
            int size = descartadoArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = descartadoArray[j].column;
            }
            return columnas;
        }
    }

    public static final String SQL_CREATE_TABLE_MEDICAMENTOS =
            "CREATE TABLE " + Medicamentos.TABLE_NAME + " (" +
                    Medicamentos.COLUMN_NAME_IDMEDICAMENTO.column + INTEGER_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.column + TEXT_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_CANTIDAD.column + INTEGER_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_LOTE.column + TEXT_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_FECHACADUCIDAD.column + TEXT_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_CODIGOPAQUETE.column + TEXT_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_MEDICAMENTOS = "DROP TABLE IF EXISTS " + Medicamentos.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_UBICADO =
            "CREATE TABLE " + Ubicado.TABLE_NAME + " (" +
                    Ubicado.COLUMN_NAME_IDUBICADO.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Ubicado.COLUMN_NAME_DESCRIPCIONUBICADO.column + TEXT_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_UBICADO = "DROP TABLE IF EXISTS " + Ubicado.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_DESCARTADO =
            "CREATE TABLE " + Descartado.TABLE_NAME + " (" +
                    Descartado.COLUMN_NAME_IDDESCARTADO.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Descartado.COLUMN_NAME_DESCRIPCIONDESCARTADO.column + TEXT_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_DESCARTADO = "DROP TABLE IF EXISTS " + Descartado.TABLE_NAME + " ; ";
}
