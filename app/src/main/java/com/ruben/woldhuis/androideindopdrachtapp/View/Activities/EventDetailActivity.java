package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.ruben.woldhuis.androideindopdrachtapp.MainActivity;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.SubscribeToEventRequest;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.DetailEventMapFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class EventDetailActivity extends FragmentActivity implements Serializable {

    public TextView eventName;
    public TextView eventTime;
    public TextView eventLocation;
    public RecyclerView mRecyclerView;
    public Button subscribeButton, navOpenButton;

    public Event event;

    public Location gpsLocation;
    public Location location;

    public ArrayList<User> allUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);

        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("Event");

        allUsers = new ArrayList<>();

        eventName = findViewById(R.id.eventName_eventDetail);
        eventTime = findViewById(R.id.eventTime_eventDetail);
        eventLocation = findViewById(R.id.eventLocation_eventDetail);
        subscribeButton = findViewById(R.id.subscribeButton_eventDetail);
        navOpenButton = findViewById(R.id.startRoute);

        eventName.setText(event.getEventName());
        eventLocation.setText(locationstring(event));
        eventTime.setText(event.getExpirationDateAsString());

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TcpManagerService.getInstance().submitMessage(new SubscribeToEventRequest(
                        UserPreferencesService.getInstance(getApplication()).getAuthenticationKey(),
                        UserPreferencesService.getInstance(getApplication()).getCurrentUser(),
                        event.getEventUID()));
            }
        });
        location = getGps();
        navOpenButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=" + location.getLatitude() + ","+ location.getLongitude() +"&daddr=" + event.getLocation().getLatitude() + ","+ event.getLocation().getLongitude()));
            startActivity(intent1);
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment DEF = DetailEventMapFragment.newInstance(event);
        fragmentManager.beginTransaction().replace(R.id.MapDetailedEventActivity, DEF).commit();


    }

    public String locationstring(Event event) {
        String s = getString(R.string.yourlocation) + " Lat: " + event.getLocation().getLatitude() + " & Lon: " + event.getLocation().getLongitude();
        return s;
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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }
}
