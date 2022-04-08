package it.univaq.veloxapp.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;

public class LocationHelper {

    private Context context;
    private LocationManager manager;
    private ActivityResultLauncher<String> launcher;

    public LocationHelper(Context context, ActivityResultLauncher<String> launcher) {
        this.context = context;
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.launcher = launcher;
    }

    public void start(LocationListener listener) {

        int checkCoarse = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        int checkFine = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        if (checkCoarse == PackageManager.PERMISSION_GRANTED && checkFine == PackageManager.PERMISSION_GRANTED){
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        }else{
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }


    }

    public void stop(LocationListener listener){

        manager.removeUpdates(listener);

    }
}
