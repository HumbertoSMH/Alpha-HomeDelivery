package algroup.com.mx.homedelivery.services;

import android.location.Location;

public interface LocationService {

    Location getLocation();

    double getLatitude();

    double getLongitude();

    boolean isLocation();

    void deshabilitarEscuchador();

    boolean getConexionActivada();

    boolean isGPSDisponible();

    void setLocation(Location location);

    Location getLocationSetting();

}
