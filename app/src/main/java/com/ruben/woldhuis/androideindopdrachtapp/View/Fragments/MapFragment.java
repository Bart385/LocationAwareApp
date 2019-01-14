package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;


import android.Manifest;
import android.content.Intent;
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
import com.ruben.woldhuis.androideindopdrachtapp.Models.PicassoMarker;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository.UserRepository;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.ProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveListener, LocationListener, GoogleMap.OnInfoWindowClickListener {
    private static final int REQUEST_PERMISSIONS_ID = 1;
    private static final int BOUND_PADDING = 100;
    double latitude;
    double longitude;
    private GoogleMap mMap;
    private Queue<Runnable> runnables;
    private android.location.Location location;
    private ArrayList<User> friends;
    private ArrayList<PicassoMarker> picassoMarkers;

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
        friends = new ArrayList<>();
        picassoMarkers = new ArrayList<>();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MAP_READY_TAG", "onMapReady: ");
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        for (User friend : friends) {
            if (friend.getLocation() != null) {
                LatLng pos = new LatLng(friend.getLocation().getLatitude(), friend.getLocation().getLongitude());
                MarkerOptions options = new MarkerOptions()
                        .position(pos)
                        .title(friend.getName());
                PicassoMarker marker = new PicassoMarker(googleMap.addMarker(options));
                if (friend.getProfilePictureURL() != null && !friend.getProfilePictureURL().isEmpty())
                    Picasso.get().load(friend.getProfilePictureURL()).into(marker);
                picassoMarkers.add(marker);
            }
        }

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

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        User friend = null;
        for (User u : friends) {
            if (u.getName().equals(marker.getTitle())) {
                friend = u;
                break;
            }
        }
        intent.putExtra("FRIEND", friend);
        startActivity(intent);
    }

    public void updateFriends(List<User> friends) {
        Log.d("MAP_FRAGMENT_TAG", "You have: " + friends.size() + " friends");
        this.friends.clear();
        this.friends.addAll(friends);
        getMapAsync(this::onMapReady);
    }
}
