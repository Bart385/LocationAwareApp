package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventParticipantsAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.SubscribeToEventRequest;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.MapFragment;
import java.io.Serializable;
import java.util.ArrayList;

import static com.ruben.woldhuis.androideindopdrachtapp.View.Activities.EventActivity.openMap;

public class EventDetailActivity extends FragmentActivity implements Serializable {

    public TextView eventName;
    public TextView eventTime;
    public TextView eventLocation;
    public RecyclerView mRecyclerView;
    public Button subscribeButton;


    public MapFragment mapFragment;
    public RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView.Adapter mAdapter;

    public ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("Meetup");

        allUsers = new ArrayList<>();

        eventName = findViewById(R.id.eventName_eventDetail);
        eventTime = findViewById(R.id.eventTime_eventDetail);
        eventLocation = findViewById(R.id.eventLocation_eventDetail);
        subscribeButton = findViewById(R.id.subscribeButton_eventDetail);

        eventName.setText(event.getEventName());
        eventLocation.setText(event.getLocation().toString());
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

        openMap(getSupportFragmentManager(), R.id.MapDetailedEventActivity);



        //TODO: Alles invullen voor Event Detail
    }
}
