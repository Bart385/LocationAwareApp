package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventsAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.Adapters.FriendsRecyclerAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Meetup;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.MapFragment;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class EventActivity extends FragmentActivity {

    ArrayList<Meetup> meetups;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MapFragment mapFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);
meetups  = new ArrayList<>();
        meetups.add(new Meetup("Test", LocalTime.MIDNIGHT, new Location(1.0, 2.0)));

        Button addEventButton  = findViewById(R.id.AddEventButton);

        mRecyclerView = findViewById(R.id.AllEventRecyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AllEventsAdapter(this, meetups);
        mRecyclerView.setAdapter(mAdapter);

        mapFragment = new MapFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.eventMapFragment, mapFragment).commit();


    }
}
