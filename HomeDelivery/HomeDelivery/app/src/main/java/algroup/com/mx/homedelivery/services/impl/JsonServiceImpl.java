package algroup.com.mx.homedelivery.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Collections;

import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoDescarteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoMotivoUbicadoResponse;
import algroup.com.mx.homedelivery.business.rest.Get.CatalogoPaqueteResponse;
import algroup.com.mx.homedelivery.business.rest.Get.LoginResponse;
import algroup.com.mx.homedelivery.business.rest.Get.RutaOperadorResponse;
import algroup.com.mx.homedelivery.business.rest.Post.CerrarVisitaRequest;
import algroup.com.mx.homedelivery.business.rest.Post.CerrarVisitaResponse;
import algroup.com.mx.homedelivery.business.rest.Post.EntregaMedico;
import algroup.com.mx.homedelivery.business.rest.Response;
import algroup.com.mx.homedelivery.services.JsonService;
import algroup.com.mx.homedelivery.utils.Const;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.NombreMedicoEnVisitaComparator;
import algroup.com.mx.homedelivery.utils.StringUtils;
import algroup.com.mx.homedelivery.utils.Util;


/**
 * Created by MAMM on 28/04/2015.
 */
public class JsonServiceImpl implements JsonService {

    private static final String CLASSNAME = JsonServiceImpl.class.getSimpleName();

    private final String URL_BASE = "http://services.alphagroup.mx/AlphaHomeDelivery.svc";

    private final String URL_LOGIN = URL_BASE + "/validarOperador";
    private final String URL_OBTENER_RUTA_OPERADOR = URL_BASE + "/obtenerRutaOperador";
    private final String URL_OBTENER_MOTIVO_DESCARTE = URL_BASE + "/obtenerMotivosDescarte";
    private final String URL_OBTENER_MOTIVO_UBICADO = URL_BASE + "/obtenerMotivosUbicado";
    private final String URL_CERRAR_ENTREGA = URL_BASE + "/cerrarEntrega";

    private final String URL_OBTENER_PAQUETES = URL_BASE + "/obtenerPaquetesRuta";


    private static JsonService singleton;

    public static JsonService getSingleton(){

        if ( singleton == null ){
            singleton = new JsonServiceImpl();
        }
        return  singleton;
    }


    public LoginResponse realizarPeticionLogin(String usuario, String password){
        LogUtil.printLog(CLASSNAME, "realizarPeticionLogin ..");
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet =
                new HttpGet(URL_LOGIN + "?claveOperador=" + usuario + "&passwordOperador=" + password );
        httpGet.setHeader("content-type", "application/json");
        LoginResponse loginResponse = null;
        try{
            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
              //  respStr = "{ \"claveError\":\"\", \"mensaje\":\"\", \"seEjecutoConExito\":true }";
                //respStr = "{ \"claveError\":\"1999\", \"mensaje\":\"Error de miguel\", \"seEjecutoConExito\":false }";
            }

            LogUtil.printLog(CLASSNAME ,"Respuesta del jsonnn = " + respStr);

            loginResponse = new LoginResponse();

            loginResponse = Util.parseJson(respStr, LoginResponse.class);

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionLogin = " + loginResponse);
            return loginResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            ex.printStackTrace();
            loginResponse = new  LoginResponse ();
            loginResponse.setSeEjecutoConExito( false );
            loginResponse.setMensaje( "Msg:" + ex.getMessage()+ ", causa:" + ex.getCause() );
            loginResponse.setClaveError( "" + 1999 );
        }

        return loginResponse;
    }


    public RutaOperadorResponse realizarPeticionObtenerRutaOperador(String usuario){
        LogUtil.printLog( CLASSNAME , "realizarPeticionObtenerRutaOperador .." );
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(URL_OBTENER_RUTA_OPERADOR + "?claveOperador=" + usuario );

        httpGet.setHeader("content-type", "application/json");
        RutaOperadorResponse rutaOperadorResponse = null;
        try{

            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr="{ \"claveError\":\"1999\", \"mensaje\":\"Error de miguel\", \"seEjecutoConExito\":false }";
                respStr="{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"rutaOperador\":{\"fechaCreacion\":\"2015-08-14 13:14\",\"fechaProgramada\":\"2015-08-17 00:00\",\"fechaUltimaModificacion\":\"2015-08-14 13:14\",\"idRuta\":72,\"visitas\":[{\"idEstatus\":27,\"idVisitaOperador\":2295,\"medicoVisita\":{\"codigoMedico\":\"C1-1560992\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CANAL DE APATLACO MZ 10 LOTE 8 Apatlaco Iztapalapa  Distrito Federal CP: 9430 Días atención:  Horario:  Tel. Consultorio: 5556330167 Tel. Celular: \",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"LIDIA PEREZ SIERRA\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2296,\"medicoVisita\":{\"codigoMedico\":\"C1-3081913\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"PLAZA DE TEPOZAN 15  Dr. Alfonso Ortiz Tirado Iztapalapa  Distrito Federal CP: 9020 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5557003920 Tel. Celular: 5519945835\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"ROSARIO MARTINEZ CHAVEZ\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2297,\"medicoVisita\":{\"codigoMedico\":\"C1-6496261\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CANAL DE TEZONTLE 1520 LOTE 5 Dr. Alfonso Ortiz Tirado Iztapalapa  Distrito Federal CP: 9020 Días atención: L a V Horario:  Tel. Consultorio: 5556004457 Tel. Celular: 5534862205\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"JUAN CARLOS CONCHA GARCIA\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2298,\"medicoVisita\":{\"codigoMedico\":\"C1-346190\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"3A CERRADA DE RETOÑO 6  El Retoño Iztapalapa  Distrito Federal CP: 9440 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556720238 Tel. Celular: \",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":1,\"nombreCompleto\":\"RAUL CASTILLO GONZALEZ\",\"portafolioDescripcion\":\"Dolor Gep\"}},{\"idEstatus\":27,\"idVisitaOperador\":2299,\"medicoVisita\":{\"codigoMedico\":\"C1-1692375\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"DIBUJANTES 65  El Sifón Iztapalapa  Distrito Federal CP: 9400 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5528430905\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"MARIA ELENA RUIZ ESQUIVEL\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2300,\"medicoVisita\":{\"codigoMedico\":\"C1-2605347\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"MECANOGRAFOS 89  El Sifón Iztapalapa  Distrito Federal CP: 9400 Días atención: L a V Horario: 13 a 16 hrs. Tel. Consultorio: 5546235986 Tel. Celular: 5534738124\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"MARIO ROJAS VILLARAN\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2301,\"medicoVisita\":{\"codigoMedico\":\"C1-7246699\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CERRADA DE LARINGOLOGOS 20  El Triunfo Iztapalapa  Distrito Federal CP: 9430 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5526598943\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":7,\"nombreCompleto\":\"BERENICE VELAZQUEZ RODRIGUEZ\",\"portafolioDescripcion\":\"Dolor GIP\"}},{\"idEstatus\":27,\"idVisitaOperador\":2302,\"medicoVisita\":{\"codigoMedico\":\"C1-852618\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"REAL DEL MONTE 45  El Triunfo Iztapalapa  Distrito Federal CP: 9430 Días atención: L a V Horario: 16 a 19 hrs. Tel. Consultorio: 5556341861 Tel. Celular: 5519155781\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"AGUSTIN ROSAS MARTINEZ\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2303,\"medicoVisita\":{\"codigoMedico\":\"C1-1005490\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"FERNANDO MONTES DE OCA MZ 10 LT 28 Guadalupe del Moral Iztapalapa  Distrito Federal CP: 9300 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556947679 Tel. Celular: 5539239764\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"AURORA SERVIN ARREOLA\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2304,\"medicoVisita\":{\"codigoMedico\":\"C1-5102984\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"AV. DEL CANAL 16  Guadalupe del Moral Iztapalapa  Distrito Federal CP: 9300 Días atención: L a V Horario: 16 a 19 hrs. Tel. Consultorio: 56948054 Tel. Celular: 5511549611\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"OMAR RAMIREZ CANO RAMIREZ CANO\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2305,\"medicoVisita\":{\"codigoMedico\":\"C1-917036\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"ROSARIO CASTELLANOS 108  Guadalupe del Moral Iztapalapa  Distrito Federal CP: 9300 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556405097 Tel. Celular: 5548278805\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"URBANO PEREZ SANCHEZ\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2306,\"medicoVisita\":{\"codigoMedico\":\"C1-8080935\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SANTA MARIA Y SAN JOSE 2  Jardines de Churubusco Iztapalapa  Distrito Federal CP: 9410 Días atención: L a V Horario:  Tel. Consultorio: 5563641306 Tel. Celular: 5543771131\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"URIEL TORICES ROMERO\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2307,\"medicoVisita\":{\"codigoMedico\":\"C1-1193038\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"PROLONGACION MONTERREY 7  Jardines de Churubusco Iztapalapa  Distrito Federal CP: 9410 Días atención: L a V Horario: 09 a 21 hrs. Tel. Consultorio: 5526211341 Tel. Celular: 5520077620\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"IRMA LEONOR PAEZ OLIVAREZ\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2308,\"medicoVisita\":{\"codigoMedico\":\"C1-4987361\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SAN RAFAEL ATLIXCO 186  La Valenciana Iztapalapa  Distrito Federal CP: 9110 Días atención: Martes Horario:  Tel. Consultorio:  Tel. Celular: 5528420684\",\"especialidadDescripcion\":\"Psiquiatría\",\"idEspecialidad\":20,\"idParrilla\":16,\"nombreCompleto\":\"ANDRES BARRERA MEDINA\",\"portafolioDescripcion\":\"SNC\"}},{\"idEstatus\":27,\"idVisitaOperador\":2309,\"medicoVisita\":{\"codigoMedico\":\"C1-835646\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"25 DE FEBRERO 1861  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5518370426\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"ANDRES MORALES FLORES\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2310,\"medicoVisita\":{\"codigoMedico\":\"C1-840668\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"23 DE JULIO DE 1859 MZ174 LT 2004 Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención:  Horario: 16 a 19 hrs. Tel. Consultorio: 5556947317 Tel. Celular: 5530790848\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"ELBA FLORES BECERRIL\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2311,\"medicoVisita\":{\"codigoMedico\":\"C1-3629502\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SUR 121 230  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556853305 Tel. Celular: \",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":71,\"nombreCompleto\":\"EUGENIO SANDOVAL TREJO\",\"portafolioDescripcion\":\"Fems\"}},{\"idEstatus\":27,\"idVisitaOperador\":2312,\"medicoVisita\":{\"codigoMedico\":\"C1-1229973\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"EJE 5 SUR 1344 C Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario: 13 a 16 hrs. Tel. Consultorio: 5556008985 Tel. Celular: 5525477577\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"RICARDO RANGEL MORALES\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2313,\"medicoVisita\":{\"codigoMedico\":\"C1-1182518\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"BATALLA DE PASO DE OVEJAS LT. 1148  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención:  Horario: 16 a 19 hrs. Tel. Consultorio: 5556002503 Tel. Celular: 5539068199\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"MARIA GUADALUPE PITALUA GARDUÑO\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2314,\"medicoVisita\":{\"codigoMedico\":\"C1-1453262\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"EJE 5 SUR Y LEYES DE REFORMA CALLE 17 S/N  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario:  Tel. Consultorio: 5556931660 Tel. Celular: 5526765616\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"DIONICIO GERARDO HERNANDEZ VAZQUEZ\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2315,\"medicoVisita\":{\"codigoMedico\":\"C1-1459077\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SUR 23 164- A  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5534014494\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"VICTOR EFREN DELGADO LOPEZ\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2316,\"medicoVisita\":{\"codigoMedico\":\"C1-5686709\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SUR 21 211 BIS  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5529641920\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"ALEJANDRA IGLESIAS COLIN\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2317,\"medicoVisita\":{\"codigoMedico\":\"C1-7738319\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"GABRIELA MISTRAL 479  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: M y J Horario: 11 a 13 hrs. Tel. Consultorio: 5556942108 Tel. Celular: \",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"INES VARGAS ROMERO\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2318,\"medicoVisita\":{\"codigoMedico\":\"C1-7908497\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CALLE 4 Y SUR 21 S/N  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L, M, V Horario:  Tel. Consultorio:  Tel. Celular: 5535909273\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"RICARDO JIMENEZ HERNANDEZ\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2319,\"medicoVisita\":{\"codigoMedico\":\"C1-1002297\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CALLE SUR 23 MZ31 294 Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556945890 Tel. Celular: 5522406700\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"JULIO RAUL REYES LOPEZ\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2320,\"medicoVisita\":{\"codigoMedico\":\"C1-2624159\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SUR 21 139  Leyes de Reforma 1a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556131833 Tel. Celular: 5517731354\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"MARTHA EDITH RODRIGUEZ CASIMIRO\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2321,\"medicoVisita\":{\"codigoMedico\":\"C1-1364975\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CALLE 18 MZ 52 LT537 LOC A Leyes de Reforma 2a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario:  Tel. Consultorio: 5556941736 Tel. Celular: \",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"MA VERONICA ORTIZ GONZALEZ\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2322,\"medicoVisita\":{\"codigoMedico\":\"C1-5249424\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CALLE 18 MZ52 LT 537 LOC A Leyes de Reforma 2a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario:  Tel. Consultorio: 5556941736 Tel. Celular: 5539186420\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"AYESHA LUNA ORTIZ\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2323,\"medicoVisita\":{\"codigoMedico\":\"C1-2207411\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"GABRIELA MISTRAL MZ47 478-B Leyes de Reforma 3a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5528581808\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"BERTHA CAMACHO ROGEL\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2324,\"medicoVisita\":{\"codigoMedico\":\"C1-3275083\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"AVENIDA CANAL DEL MORAL MZ. 82 LT. A Leyes de Reforma 3a Sección Iztapalapa  Distrito Federal CP: 9310 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5517962574\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"EDUARDO CALDERON DOMINGUEZ\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2325,\"medicoVisita\":{\"codigoMedico\":\"C1-4258454\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"RIO MEZCALAPA 48  Paseos de Churubusco Iztapalapa  Distrito Federal CP: 9030 Días atención: L a V Horario: 13 a 16 hrs. Tel. Consultorio: 5556482273 Tel. Celular: 5518310839\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":1,\"nombreCompleto\":\"MARIA DEL CARMEN AGUILAR GONZALEZ\",\"portafolioDescripcion\":\"Dolor Gep\"}},{\"idEstatus\":27,\"idVisitaOperador\":2326,\"medicoVisita\":{\"codigoMedico\":\"C1-2229287\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"RIO ALTAR 78 8 Paseos de Churubusco Iztapalapa  Distrito Federal CP: 9030 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556866475 Tel. Celular: 5519491814\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":28,\"nombreCompleto\":\"MARIA DOLORES LOPEZ MORENO\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2327,\"medicoVisita\":{\"codigoMedico\":\"C1-3528506\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"RIO MEZCALAPA 48  Paseos de Churubusco Iztapalapa  Distrito Federal CP: 9030 Días atención: L a V Horario:  Tel. Consultorio: 5556482273 Tel. Celular: 5512867589\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":71,\"nombreCompleto\":\"JUAN GERARDO JACOME BAHENA\",\"portafolioDescripcion\":\"Fems\"}},{\"idEstatus\":27,\"idVisitaOperador\":2328,\"medicoVisita\":{\"codigoMedico\":\"C1-3937536\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CIRCUITO RIO SALINAS 46  Paseos de Churubusco Iztapalapa  Distrito Federal CP: 9030 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5537005118\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"CARMINA DIAZ HERNANDEZ\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2329,\"medicoVisita\":{\"codigoMedico\":\"C1-2825857\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"RIO TUXPAN 3  Paseos de Churubusco Iztapalapa  Distrito Federal CP: 9030 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556508070 Tel. Celular: 5522983650\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"MARIA GABRIELA TORRES TAPIA\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2330,\"medicoVisita\":{\"codigoMedico\":\"C1-3861194\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CIRCUITO RÍO SONORA 28 A  Real del Moral Iztapalapa  Distrito Federal CP: 9010 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556540450 Tel. Celular: 5561539596\",\"especialidadDescripcion\":\"Medicina Familiar\",\"idEspecialidad\":80,\"idParrilla\":17,\"nombreCompleto\":\"LAURA ARACELI SORIANO DE LA GARZA\",\"portafolioDescripcion\":\"Dolor GIP\"}},{\"idEstatus\":27,\"idVisitaOperador\":2331,\"medicoVisita\":{\"codigoMedico\":\"C1-888327\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CALLE 5 MZ 21 LT5 Renovación Iztapalapa  Distrito Federal CP: 9209 Días atención: L a V Horario: 16 a 19 hrs. Tel. Consultorio: 5556703775 Tel. Celular: 5521892319\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"ALEJANDRO JIMENEZ HERNANDEZ\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2332,\"medicoVisita\":{\"codigoMedico\":\"C1-2508630\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"11 MZ 49 LT 32 Renovación Iztapalapa  Distrito Federal CP: 9209 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5556929544 Tel. Celular: 5513597204\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"YOLANDA AGUIRRE HERNANDEZ\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2333,\"medicoVisita\":{\"codigoMedico\":\"C1-158386\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"LABORISTAS 65  San Andrés Tetepilco Iztapalapa  Distrito Federal CP: 9440 Días atención: L a V Horario: 13 a 16 hrs. Tel. Consultorio: 5556729187 Tel. Celular: 5534666515\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":1,\"nombreCompleto\":\"ANDRES HERNANDEZ ROCHA\",\"portafolioDescripcion\":\"Dolor Gep\"}},{\"idEstatus\":27,\"idVisitaOperador\":2334,\"medicoVisita\":{\"codigoMedico\":\"C1-3737859\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"PORTO ALEGRE 241  San Andrés Tetepilco Iztapalapa  Distrito Federal CP: 9440 Días atención: M y J Horario:  Tel. Consultorio:  Tel. Celular: 5534663296\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"MARGARITA SANCHEZ FRANCO\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2335,\"medicoVisita\":{\"codigoMedico\":\"C1-3577112\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"Municipio Libre 402  San Andrés Tetepilco Iztapalapa  Distrito Federal CP: 9440 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5555950034 Tel. Celular: \",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"EDGAR LIEVANO REYES\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2336,\"medicoVisita\":{\"codigoMedico\":\"C1-701983\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"INDEPENDENCIA 20  San Andrés Tetepilco Iztapalapa  Distrito Federal CP: 9440 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5554647410\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":1,\"nombreCompleto\":\"ROBERTO ESAU HERNANDEZ ESPINOSA\",\"portafolioDescripcion\":\"Dolor Gep\"}},{\"idEstatus\":27,\"idVisitaOperador\":2337,\"medicoVisita\":{\"codigoMedico\":\"C1-1548207\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"LEGISTAS 14  San José Aculco Iztapalapa  Distrito Federal CP: 9410 Días atención: L a V Horario: 09 a 21 hrs. Tel. Consultorio: 5556542188 Tel. Celular: 5585667678\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":7,\"nombreCompleto\":\"ALFREDO RUIZ GONZALEZ\",\"portafolioDescripcion\":\"Dolor GIP\"}},{\"idEstatus\":27,\"idVisitaOperador\":2338,\"medicoVisita\":{\"codigoMedico\":\"C1-5194375\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SAN JUANICO 74  San Juanico Nextipac Iztapalapa  Distrito Federal CP: 9400 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5559093353\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"MIGUEL ANGEL ACOSTA ITURBE\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2339,\"medicoVisita\":{\"codigoMedico\":\"C1-3164528\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CALZ. ERMITA IZTAPALAPA 278 502 Sinatel Iztapalapa  Distrito Federal CP: 9470 Días atención: L a V Horario: 16 a 19 hrs. Tel. Consultorio: 5556727408 Tel. Celular: 5516786939\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"JUAN ULISES RIZO ESCOBAR\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2340,\"medicoVisita\":{\"codigoMedico\":\"C1-1015513\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SUR 73 403 LOC 2 Sinatel Iztapalapa  Distrito Federal CP: 9470 Días atención: L a V Horario: 11 a 13 hrs. Tel. Consultorio: 5552439001 Tel. Celular: 5537355164\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"MIGUEL ANGEL ALCARAZ NUÑEZ\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2341,\"medicoVisita\":{\"codigoMedico\":\"C1-1508159\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CALZADA ERMITA IZTAPALAPA 278  Sinatel Iztapalapa  Distrito Federal CP: 9470 Días atención: L a V Horario: 16 a 19 hrs. Tel. Consultorio: 5555328035 Tel. Celular: 5542850240\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"PATRICIA GARDUÑO GARCIA VILLALOBOS\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2342,\"medicoVisita\":{\"codigoMedico\":\"C1-146493\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"CALZ ERMITA IZTAPALAPA 278 DESPACHO CONSULTORIO 202 Sinatel Iztapalapa  Distrito Federal CP: 9470 Días atención: L a V Horario: 13 a 16 hrs. Tel. Consultorio: 55398792 Tel. Celular: 5518412994\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"MARIA ESTELA SANTILLAN GOMEZ\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2343,\"medicoVisita\":{\"codigoMedico\":\"C1-243227\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"ANDRES MOLINA ENRIQUEZ 336-BIS  Sinatel Iztapalapa  Distrito Federal CP: 9470 Días atención: L a V Horario: 13 a 16 hrs. Tel. Consultorio: 5552434615 Tel. Celular: 5554350591\",\"especialidadDescripcion\":\"Ortopedía y Traumatología\",\"idEspecialidad\":87,\"idParrilla\":24,\"nombreCompleto\":\"DANIEL ESPINOSA TERAN\",\"portafolioDescripcion\":\"Cardio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2344,\"medicoVisita\":{\"codigoMedico\":\"C1-3370042\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"SUR 20 181  Tepalcates I Iztapalapa  Distrito Federal CP: 9210 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5513872730\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":30,\"nombreCompleto\":\"SELENE BANDALA CERVANTES\",\"portafolioDescripcion\":\"General\"}},{\"idEstatus\":27,\"idVisitaOperador\":2345,\"medicoVisita\":{\"codigoMedico\":\"C1-7627199\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"calz ignacio zaragoza 1711  Unidad Ejército Constitucionalista Iztapalapa  Distrito Federal CP: 9220 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5539148254\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":4,\"nombreCompleto\":\"FELICIANO ANAYA RODRIGUEZ\",\"portafolioDescripcion\":\"General 2\"}},{\"idEstatus\":27,\"idVisitaOperador\":2346,\"medicoVisita\":{\"codigoMedico\":\"C1-784187\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"INDEPENDENCIA 20  Zacahuitzco Iztapalapa  Distrito Federal CP: 9440 Días atención: L a V Horario:  Tel. Consultorio:  Tel. Celular: 5542417315\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":1,\"nombreCompleto\":\"MIGUEL ANGEL GIORGANA PERALTA\",\"portafolioDescripcion\":\"Dolor Gep\"}},{\"idEstatus\":27,\"idVisitaOperador\":2347,\"medicoVisita\":{\"codigoMedico\":\"C1-576624\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"INDEPENDENCIA 20  Zacahuitzco Iztapalapa  Distrito Federal CP: 9440 Días atención: L a V Horario:  Tel. Consultorio: 5555390792 Tel. Celular: 5554373882\",\"especialidadDescripcion\":\"Medicina General\",\"idEspecialidad\":16,\"idParrilla\":1,\"nombreCompleto\":\"GONZALO ANAYA BALONA\",\"portafolioDescripcion\":\"Dolor Gep\"}},{\"idEstatus\":27,\"idVisitaOperador\":2348,\"medicoVisita\":{\"codigoMedico\":\"C1-874772\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"EMILIO CARRANZA 258  Zacahuitzco Iztapalapa  Distrito Federal CP: 9440 Días atención: L a V Horario: 16 a 19 hrs. Tel. Consultorio: 5555396457 Tel. Celular: 5510040380\",\"especialidadDescripcion\":\"Odontólogo\",\"idEspecialidad\":18,\"idParrilla\":6,\"nombreCompleto\":\"LAURA PATRICIA CERVANTES CABALLERO\",\"portafolioDescripcion\":\"Respiratorio\"}},{\"idEstatus\":27,\"idVisitaOperador\":2349,\"medicoVisita\":{\"codigoMedico\":\"C1-294026\",\"coordenadas\":null,\"correo\":null,\"direccion\":\"EMILIO CARRANZA 272  Zacahuitzco Iztapalapa  Distrito Federal CP: 9440 Días atención: L, M, V Horario:  Tel. Consultorio: 5555328159 Tel. Celular: \",\"especialidadDescripcion\":\"Psiquiatría\",\"idEspecialidad\":20,\"idParrilla\":16,\"nombreCompleto\":\"PEDRO CAMACHO CERON\",\"portafolioDescripcion\":\"SNC\"}}]}}";
            }
            LogUtil.printLog(CLASSNAME, "Respuesta del jsonnn = " + respStr);

            rutaOperadorResponse = new RutaOperadorResponse();

            rutaOperadorResponse = Util.parseJson( respStr  , RutaOperadorResponse.class );

            LogUtil.printLog(CLASSNAME, "Respuesta del realizarPeticionObtenerRutaOperador = " + rutaOperadorResponse);


            if(     rutaOperadorResponse != null &&
                    rutaOperadorResponse.getRutaOperador() != null &&
                    rutaOperadorResponse.getRutaOperador().getVisitas() != null){
                //INI MAMM Bloque para ordenar el resultado de la consulta de medicos
                Collections.sort( rutaOperadorResponse.getRutaOperador().getVisitas(), new NombreMedicoEnVisitaComparator() );
                //END MAMM
            }


            return rutaOperadorResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            ex.printStackTrace();
            rutaOperadorResponse = new RutaOperadorResponse();
            rutaOperadorResponse.setSeEjecutoConExito( false );
            rutaOperadorResponse.setMensaje( "Msg:" + ex.getMessage()+ ", causa:" + ex.getCause() );
            rutaOperadorResponse.setClaveError( "" + 1999 );
        }

        return rutaOperadorResponse;
    }

    public CatalogoMotivoDescarteResponse realizarPeticionObtenerMotivoDescarte( ){
        LogUtil.printLog( CLASSNAME , "realizarPeticionObtenerMotivoDescarte .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_OBTENER_MOTIVO_DESCARTE  );
        httpGet.setHeader("content-type", "application/json");
        CatalogoMotivoDescarteResponse catalogoMotivoDescarteResponse = null;
        try{
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr);

            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr = "{\"claveError\":100,\"mensaje\":\"Error al cargar el catalogo de productos error mam\",\"seEjecutoConExito\":false,\"productos\":[ ]}";
                //respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"catalogoMotivoDescarte\":[{\"descripcion\":\"Ya no da consulta el Médico\",\"idDetalle\":38},{\"descripcion\":\"Falleció el Médico\",\"idDetalle\":39},{\"descripcion\":\"Otro\",\"idDetalle\":40}]}";
            }

            catalogoMotivoDescarteResponse = new CatalogoMotivoDescarteResponse();

            catalogoMotivoDescarteResponse = Util.parseJson( respStr  , CatalogoMotivoDescarteResponse.class );

            LogUtil.printLog( CLASSNAME , "Respuesta del realizarPeticionObtenerMotivoDescarte = " + catalogoMotivoDescarteResponse );

            return catalogoMotivoDescarteResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            ex.printStackTrace();
            catalogoMotivoDescarteResponse = new  CatalogoMotivoDescarteResponse();
            catalogoMotivoDescarteResponse.setSeEjecutoConExito( false );
            catalogoMotivoDescarteResponse.setMensaje( "Msg:" + ex.getMessage()+ ", causa:" + ex.getCause() );
            catalogoMotivoDescarteResponse.setClaveError( "" + 1999 );
        }

        return catalogoMotivoDescarteResponse;
    }



    public CatalogoPaqueteResponse realizarPeticionObtenerCatalogoPaquetes(String usuario){
        LogUtil.printLog( CLASSNAME , "realizarPeticionObtenerCatalogoPaquetes .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_OBTENER_PAQUETES + "?claveOperador=" + usuario );
        httpGet.setHeader("content-type", "application/json");
        CatalogoPaqueteResponse catalogoPaqueteResponse = null;
        try{
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr);

            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //25-02-16-00001
                //respStr = "{\"claveError\":100,\"mensaje\":\"Error al cargar el catalogo de productos error mam\",\"seEjecutoConExito\":false,\"productos\":[ ]}";
                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"listaPaquetesRuta\":[{\"codigoPaquete\":\"000321\",\"encontrado\":false,\"idEspecialidad\":16,\"idParrrilla\":4,\"idPaquete\":3,\"listaDetalles\":[{\"cantidad\":2,\"descripcion\":\"NEURONTIN 600MG X 3 TABS\",\"fechaCaducidadTexto\":\"2015-07-12 00:00\",\"lote\":\"64563\"},{\"cantidad\":1,\"descripcion\":\"POSTAL JUNIO\",\"fechaCaducidadTexto\":\"\",\"lote\":null}]},{\"codigoPaquete\":\"12345678\",\"encontrado\":false,\"idEspecialidad\":16,\"idParrrilla\":30,\"idPaquete\":4,\"listaDetalles\":[{\"cantidad\":2,\"descripcion\":\"NEURONTIN 600MG X 3 TABS\",\"fechaCaducidadTexto\":\"2015-07-12 00:00\",\"lote\":\"64563\"}]}]}";
            }

            catalogoPaqueteResponse = new CatalogoPaqueteResponse();

            catalogoPaqueteResponse = Util.parseJson( respStr  , CatalogoPaqueteResponse.class );

            LogUtil.printLog( CLASSNAME , "Respuesta del realizarPeticionObtenerCatalogoPaquetes = " + catalogoPaqueteResponse );

            return catalogoPaqueteResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            ex.printStackTrace();
            catalogoPaqueteResponse = new  CatalogoPaqueteResponse();
            catalogoPaqueteResponse.setSeEjecutoConExito( false );
            catalogoPaqueteResponse.setMensaje( "Msg:" + ex.getMessage()+ ", causa:" + ex.getCause() );
            catalogoPaqueteResponse.setClaveError( "" + 1999 );
        }

        return catalogoPaqueteResponse;
    }



    public CatalogoMotivoUbicadoResponse realizarPeticionObtenerMotivoUbicado( ){
        LogUtil.printLog( CLASSNAME , "realizarPeticionObtenerMotivoUbicado .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_OBTENER_MOTIVO_UBICADO  );
        httpGet.setHeader("content-type", "application/json");
        CatalogoMotivoUbicadoResponse catalogoMotivoUbicadoResponse = null;
        try{
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr);

            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr = "{\"claveError\":100,\"mensaje\":\"Error al cargar el catalogo de productos error mam\",\"seEjecutoConExito\":false,\"productos\":[ ]}";
                //respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"listadoMotivoUbicado\":[{\"descripcion\":\"No se encontraba el Médico\",\"idDetalle\":34},{\"descripcion\":\"Estaba ocupado el Médico\",\"idDetalle\":35},{\"descripcion\":\"No quizo firmar el Médico\",\"idDetalle\":36},{\"descripcion\":\"Otro\",\"idDetalle\":37}]}";
            }

            catalogoMotivoUbicadoResponse = new CatalogoMotivoUbicadoResponse();

            catalogoMotivoUbicadoResponse = Util.parseJson( respStr  , CatalogoMotivoUbicadoResponse.class );

            LogUtil.printLog( CLASSNAME , "Respuesta del realizarPeticionObtenerMotivoUbicado = " + catalogoMotivoUbicadoResponse );

            return catalogoMotivoUbicadoResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            ex.printStackTrace();
            catalogoMotivoUbicadoResponse = new  CatalogoMotivoUbicadoResponse();
            catalogoMotivoUbicadoResponse.setSeEjecutoConExito( false );
            catalogoMotivoUbicadoResponse.setMensaje( "Msg:" + ex.getMessage()+ ", causa:" + ex.getCause() );
            catalogoMotivoUbicadoResponse.setClaveError( "" + 1999 );
        }

        return catalogoMotivoUbicadoResponse;
    }

    public CerrarVisitaResponse realizarPostCerrarEntrega(EntregaMedico entregaMedico ){
        LogUtil.printLog( CLASSNAME , "realizarPostCerrarEntrega" );
        CerrarVisitaRequest request = new CerrarVisitaRequest();
        request.setEntregaMedico(entregaMedico);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost =
                new HttpPost( URL_CERRAR_ENTREGA );
        httpPost.setHeader("content-type", "application/json; charset=utf-8");
        CerrarVisitaResponse response = null;

        try{
            String jsonStringRequest = Util.getStringJSON( request );
            jsonStringRequest = StringUtils.removerCaracteresNoASCII(jsonStringRequest);

            StringEntity stringEntity = new StringEntity( jsonStringRequest );
            httpPost.setEntity(stringEntity);

            LogUtil.printLog(CLASSNAME, "StringEntity = " + jsonStringRequest );
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpPost.getURI().toString() );

            HttpResponse resp= null ;
            String respStr = null;
            if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK){
                respStr = "{\"claveError\":\"\",\"men saje\"peticion mock:\"\",\"seEjecutoConExito\":false}";
            }else{
                 resp = httpClient.execute(httpPost);
                 respStr = EntityUtils.toString(resp.getEntity());
            }



            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                respStr = "{\"claveError\":\"\",\"men saje\":\"\",\"seEjecutoConExito\":true}";
                //respStr = "{\"claveError\":100,\"mensaje\":\"Error al intentar realizar el checkout mam\",\"seEjecutoConExito\":false}";
            }

            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr );

            response = new CerrarVisitaResponse();

            response = Util.parseJson( respStr  , CerrarVisitaResponse.class );

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPostCerrarEntrega = " + response);

            return response;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            ex.printStackTrace();
            response = new CerrarVisitaResponse();
            Response cerrarEntregaResult = response.getCerrarEntregaResult();
            cerrarEntregaResult.setSeEjecutoConExito( false );
            cerrarEntregaResult.setMensaje( "Msg:" + ex.getMessage()+ ", causa:" + ex.getCause() );
            cerrarEntregaResult.setClaveError( "" + 1999 );
        }

        return response;
    }

}
