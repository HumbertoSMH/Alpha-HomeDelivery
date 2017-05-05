package algroup.com.mx.homedelivery.services.impl;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import algroup.com.mx.homedelivery.HomeDeliveryApp;
import algroup.com.mx.homedelivery.services.LocationService;
import algroup.com.mx.homedelivery.utils.LogUtil;
import algroup.com.mx.homedelivery.utils.ViewUtil;


public final class LocationServiceImpl implements LocationService, LocationListener {
    
    private LocationManager locationManager;
    private Context context;
    private static LocationServiceImpl singleton;
    private boolean disponibleGPS = false;
    private boolean canGetLocation = false;
    private Location location;
    private double latitude;
    private double longitude;
    private static final String CLASSNAME = LocationServiceImpl.class.getSimpleName();
    private String provider;
    
    private LocationServiceImpl(Context context){
        this.context = context;
        getLocation();
    }
    
    public static LocationServiceImpl getSingleton( ){
        if(singleton == null){
            singleton = new LocationServiceImpl( HomeDeliveryApp.getCurrentApplication() );
        }
        return singleton;
    }

    @Override
    public Location getLocation() {
        LogUtil.printLog( CLASSNAME, "invoca getLocation!! " );
        boolean internetDisponible = false;
        try {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);

            disponibleGPS = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            internetDisponible = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!disponibleGPS && !internetDisponible) {
                LogUtil.printLog( CLASSNAME, "Esta disponible el GPS? = " + disponibleGPS +
                        " y el internet?= " + internetDisponible );
                canGetLocation = false;
                location = null;
            } else {
                setLocation(internetDisponible);
            }

        } catch (Exception e) {
            Log.e(CLASSNAME, e.getMessage());
            e.printStackTrace();
        }

        return location;
    }
    
    private void setLocation(boolean internetAvailable){
        boolean conexionAvailable = internetAvailable;
        canGetLocation = true;
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Log.d(CLASSNAME, "El provider es: " + provider);

        //Se intenta por internet si este esta disponible
        if(conexionAvailable) {
            Log.d(CLASSNAME, "Calculo de POSICION POR WIFI");
            locationManager.requestLocationUpdates(provider, 0, 0, this);

            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    LogUtil.printLog(CLASSNAME, "internetDisponible = "
                            + "latitude = " + latitude
                            + "longitude  = " + longitude);
                }
            }
        }

        if( location == null &&
                disponibleGPS ) {
            Log.d(CLASSNAME, "Calulo de POSICION POR GPS");
            locationManager.requestLocationUpdates(provider, 0, 0, this);

            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        }


        deshabilitarEscuchador();
    }

    @Override
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    @Override
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    @Override
    public boolean isLocation() {
        return this.canGetLocation;
    }

    @Override
    public void deshabilitarEscuchador() {
        locationManager.removeUpdates(this);

    }

    @Override
    public boolean getConexionActivada() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfos = connectivityMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean conexionDisponible = nwInfos.isAvailable();

        return conexionDisponible;
    }

    @Override
    public boolean isGPSDisponible() {
        return this.disponibleGPS;
    }

    @Override
    public void onLocationChanged(Location location) {
        
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(CLASSNAME, "provider disabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
    
    @Override
    public Location getLocationSetting() {
        return location;
    }

}
