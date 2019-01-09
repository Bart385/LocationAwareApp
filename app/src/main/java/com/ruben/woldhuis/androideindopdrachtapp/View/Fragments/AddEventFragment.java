package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.LocationListener;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.ruben.woldhuis.androideindopdrachtapp.MainActivity;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.EventCreationReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.EventCreationRequest;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;

import java.util.Queue;

import static android.content.Context.LOCATION_SERVICE;
import static com.ruben.woldhuis.androideindopdrachtapp.View.Activities.EventActivity.openMap;

public class AddEventFragment extends Fragment {

    double latitude;
    double longitude;
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addevent, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView EventName = view.findViewById(R.id.eventTitle);
        Button cancelButton = view.findViewById(R.id.EventCancelButton);
        Button confirmButton = view.findViewById(R.id.confirmAddEvent);

        Application app = getActivity().getApplication();
        confirmButton.setOnClickListener(view1 -> {
            TcpManagerService.getInstance().submitMessage(new EventCreationRequest(
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    UserPreferencesService.getInstance(app).getCurrentUser(),
                    getGps(),
                    String.valueOf(EventName.getText())
            ));
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            openMap(getActivity().getSupportFragmentManager());
        });

        cancelButton.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            openMap(getActivity().getSupportFragmentManager());
        });

    }

    public Location getGps() {
        LocationManager locationManager = (LocationManager) MainActivity.getInstance().getSystemService(LOCATION_SERVICE);

        android.location.Location locationLocationmng;

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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
                if (locationManager != null) {
                    locationLocationmng = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    location = new Location(locationLocationmng.getLatitude(), locationLocationmng.getLongitude());
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


}
