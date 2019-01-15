package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.EventDetailActivity;

public class DetailEventMapFragment extends Fragment implements OnMapReadyCallback {
GoogleMap mMap;
Event event;

    public DetailEventMapFragment(){
    //needs to stay empty
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null)
            event = (Event) getArguments().getSerializable("object");
        return inflater.inflate(R.layout.fragment_eventmap, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
        }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude())).title(event.getEventName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude()), 8));
        System.out.println("Test");
    }

    public static DetailEventMapFragment newInstance(Event event) {
        DetailEventMapFragment fragment = new DetailEventMapFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", event);
        fragment.setArguments(bundle);

        return fragment;
    }
}
