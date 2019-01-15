package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    public Button subscribeButton;

    public Event event;


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

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment DEF = DetailEventMapFragment.newInstance(event);
        fragmentManager.beginTransaction().replace(R.id.MapDetailedEventActivity, DEF).commit();


    }

    public String locationstring(Event event) {
        String s = getString(R.string.yourlocation) + " Lat: " + event.getLocation().getLatitude() + " & Lon: " + event.getLocation().getLongitude();
        return s;
    }
}
