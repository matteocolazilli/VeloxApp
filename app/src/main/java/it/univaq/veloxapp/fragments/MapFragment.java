package it.univaq.veloxapp.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import it.univaq.veloxapp.R;
import it.univaq.veloxapp.database.DB;
import it.univaq.veloxapp.model.Autovelox;
import it.univaq.veloxapp.utility.LocationHelper;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private AlertDialog alertDialog;
    private GoogleMap map;
    private Marker myMarker;
    private List<Marker> autoveloxMarker = new ArrayList<>();
    private LocationHelper locationHelper;
    private boolean firstUpdate = true;
    private Location myLocation = new Location("myposition");

    private ActivityResultLauncher<String> locationPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result){
                        locationHelper.start(MapFragment.this);
                    } else {
                        Toast.makeText(requireContext(),R.string.position_permissions_not_granted, Toast.LENGTH_SHORT).show();
                        requireActivity().onBackPressed();
                    }
            }});

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buildAlertMessageNoGps();
        locationHelper = new LocationHelper(requireContext(), locationPermissionLauncher);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        if (fragment != null) fragment.getMapAsync(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        if(alertDialog.isShowing()) alertDialog.dismiss();
        checkGPSEnabled();
        locationHelper.start(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        locationHelper.stop(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;
        map.setOnMarkerClickListener(marker -> {

            Autovelox autovelox = (Autovelox) marker.getTag();
            if (autovelox != null) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("autovelox", autovelox);
                Navigation.findNavController(MapFragment.this.requireView()).navigate(R.id.action_navMap_to_detailActivity,bundle);
                return true;
            }
            return false;
        });
        locationHelper.start(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        if (firstUpdate) {
            load(location);
        } else {
            myLocation.setLongitude(myMarker.getPosition().longitude);
            myLocation.setLatitude(myMarker.getPosition().latitude);
            boolean distant = location.distanceTo(myLocation) > 100;
            if (distant){
                load(location);
            }
        }
    }


    private void load(Location location){

        addMyMarker(location);

        //pulizia dei marker già presenti
        for (Marker marker : autoveloxMarker) marker.remove();

         // aggiornamento dei nuovi marker al cambiamento di posizione
        new Thread(()->{

            //per centrare la mappa sugli autovelox intorno alla mia posizione
            LatLngBounds.Builder bounds = new LatLngBounds.Builder();

            List<Autovelox> autoveloxList = DB.getInstance(requireContext()).autoveloxDao().findAll();

            for (Autovelox autovelox : autoveloxList){
                Location l = new Location("autovelox");
                l.setLatitude(autovelox.getLatitude());
                l.setLongitude(autovelox.getLongitude());

                if (l.distanceTo(location) > 10000) continue;

                MarkerOptions options = new MarkerOptions();
                options.title(autovelox.getAddress());
                options.position(new LatLng(l.getLatitude(), l.getLongitude()));
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                bounds.include(new LatLng(l.getLatitude(), l.getLongitude()));

                //aggiunta degli autovelox nel main thread
                requireActivity().runOnUiThread(() ->{
                    Marker marker = map.addMarker(options);
                    marker.setTag(autovelox); //per avere oggetto clickable
                    autoveloxMarker.add(marker);
                });

            }
            try {
                //per visualizzare solo i miei marker e non tutta la mappa
                requireActivity().runOnUiThread(()-> {
                            if (firstUpdate) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myMarker.getPosition(), 3));
                                map.animateCamera(CameraUpdateFactory.zoomTo(11), 3000, null);
                                firstUpdate = false;
                            } else {
                                map.animateCamera(CameraUpdateFactory.newLatLng(myMarker.getPosition()));
                            }
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    private void addMyMarker(Location location){

        if (myMarker == null){
            MarkerOptions options = new MarkerOptions();
            options.title(getString(R.string.my_location));
            options.position(new LatLng(location.getLatitude(), location.getLongitude()));
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            myMarker = map.addMarker(options);
        } else {
            myMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
        }
    }

    public void checkGPSEnabled() {
        if (!locationHelper.getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER))
            alertDialog.show();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(R.string.no_gps_alert_message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        requireContext().startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                        requireActivity().onBackPressed();
                        Toast.makeText(requireContext(), R.string.disabled_position_toast, Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog = builder.create();
    }

}

