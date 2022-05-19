package it.univaq.veloxapp.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;

public class LocationHelper {

    private final Context context;
    private final LocationManager locationManager;
    private final ActivityResultLauncher<String[]> locationPermissionLauncher;

    public LocationHelper(Context context, ActivityResultLauncher<String[]> locationPermissionLauncher) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.locationPermissionLauncher = locationPermissionLauncher;
    }

    public void start(LocationListener listener) {

        int checkCoarse = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        int checkFine = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        if (checkCoarse == PackageManager.PERMISSION_GRANTED && checkFine == PackageManager.PERMISSION_DENIED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10, listener);
        } else if (checkFine == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10, listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, listener);
        }else locationPermissionLauncher.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void stop(LocationListener listener){

        locationManager.removeUpdates(listener);

    }

}
