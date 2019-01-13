package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventsAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Meetup;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.AddEventFragment;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.MapFragment;

import java.time.LocalTime;
import java.util.ArrayList;

public class EventActivity extends FragmentActivity {

    ArrayList<Meetup> meetups;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MapFragment mapFragment;
    private FragmentManager fragmentManager;
    private AddEventFragment addEventFragment;

    public static void openMap(FragmentManager support) {
        MapFragment mapFragment;
        FragmentManager fragmentManager;

        mapFragment = new MapFragment();
        fragmentManager = support;
        fragmentManager.beginTransaction().replace(R.id.eventMapFragment, mapFragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);
        meetups = new ArrayList<>();
        meetups.add(new Meetup("Test", LocalTime.MIDNIGHT, new Location(1.0, 2.0)));

        //TODO: Alle events opvragen.

        FloatingActionButton addEventButton = findViewById(R.id.AddEventButton);

        mRecyclerView = findViewById(R.id.AllEventRecyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AllEventsAdapter(this, meetups);
        mRecyclerView.setAdapter(mAdapter);

        openMap(getSupportFragmentManager());

        fragmentManager = getSupportFragmentManager();
        addEventFragment = new AddEventFragment();

        addEventButton.setOnClickListener(view -> {
            fragmentManager.beginTransaction().replace(R.id.eventMapFragment, addEventFragment).commit();
        });
    }
}
