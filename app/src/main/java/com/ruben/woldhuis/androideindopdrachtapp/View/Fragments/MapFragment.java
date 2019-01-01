package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruben.woldhuis.androideindopdrachtapp.MainActivity;
import com.ruben.woldhuis.androideindopdrachtapp.R;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveListener, LocationListener {
    private static final int REQUEST_PERMISSIONS_ID = 1;
    private static final int BOUND_PADDING = 100;
    double latitude;
    double longitude;
    private GoogleMap mMap;
    private Queue<Runnable> runnables;
    private android.location.Location location;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        runnables = new LinkedBlockingQueue<>();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MAP_READY_TAG", "onMapReady: ");
        mMap = googleMap;
/*
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSIONS_ID);
        else
            mMap.setMyLocationEnabled(true);
        getGps();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .bearing(0)
                .zoom(15)
                .build()));
        LatLng currentposition = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(currentposition).title("Test"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(currentposition));

        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setMinZoomPreference(13);
        mMap.setMaxZoomPreference(20);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
//        UiSettings settings = googleMap.getUiSettings();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        Iterator<Runnable> iterator = runnables.iterator();
        while (iterator.hasNext()) {
            Runnable runnable = iterator.next();
            runnable.run();

        }*/


    }

    public Location getGps() {
        LocationManager locationManager = (LocationManager) MainActivity.getInstance().getSystemService(LOCATION_SERVICE);

        // getting GPS status
        boolean isGPSEnabled = false;
        if (locationManager != null) {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        if (isGPSEnabled) {
            if (location == null) {
                if (ActivityCompat.checkSelfPermission(MainActivity.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this, null);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public Location getLocation() {
        return location;
    }


    private boolean checkPermission(String permission) {
        if (ActivityCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void requestPermission(String permission, int code) {
        requestPermissions(new String[]{permission}, code);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    public void addMarker(GoogleMap googleMap) {
        Location location;
        location = getGps();
        MarkerOptions options = new MarkerOptions();
        options = options.position(new LatLng(location.getLatitude(), location.getLongitude()));
        googleMap.addMarker(options);
    }
}
