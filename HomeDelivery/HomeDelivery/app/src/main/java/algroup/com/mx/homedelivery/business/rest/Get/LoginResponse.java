package algroup.com.mx.homedelivery.business.rest.Get;


import algroup.com.mx.homedelivery.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class LoginResponse extends Response {

    public static LoginResponse generarResponseExitoso(){
        LoginResponse response = new LoginResponse();
        response.setSeEjecutoConExito( true );
        response.setMensaje( "" );
        response.setClaveError( "" );
        return response;
    }
}
