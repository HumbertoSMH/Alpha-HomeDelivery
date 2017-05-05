package algroup.com.mx.homedelivery.business.utils;

/**
 * Created by devmac02 on 06/07/15.
 */
public class UtilLocation {
    private String latitud;
    private String longitud;

    public UtilLocation( ) {
        this.latitud = "";
        this.longitud = "";
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "UtilLocation{" +
                "latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }
}
