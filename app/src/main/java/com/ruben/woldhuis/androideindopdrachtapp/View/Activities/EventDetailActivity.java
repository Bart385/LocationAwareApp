package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventParticipantsAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class EventDetailActivity extends Activity implements Serializable {

    public TextView eventName;
    public TextView eventTime;
    public TextView eventLocation;
    public RecyclerView mRecyclerView;
    public Button subscribeButton;

    public RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView.Adapter mAdapter;

    public ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);

        allUsers = new ArrayList<>();

        eventName = findViewById(R.id.eventName_eventDetail);
        eventTime = findViewById(R.id.eventTime_eventDetail);
        eventLocation = findViewById(R.id.eventLocation_eventDetail);
        subscribeButton = findViewById(R.id.subscribeButton_eventDetail);

        mRecyclerView = findViewById(R.id.AllEventRecyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AllEventParticipantsAdapter(this, allUsers);
        mRecyclerView.setAdapter(mAdapter);

        //TODO: Alles invullen voor Event Detail
    }
}
