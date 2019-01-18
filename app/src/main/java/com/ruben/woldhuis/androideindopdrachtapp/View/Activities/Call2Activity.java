package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.CallFriendAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository.UserRepository;

import java.util.ArrayList;

public class Call2Activity extends FragmentActivity {
    private static final int RECORD_AUDIO_REQUEST_CODE = 201;

    private UserRepository userRepository;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<User> contacts;


    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);

        setContentView(R.layout.activity_call2);
        mRecyclerView = findViewById(R.id.callable_contacts_recyclerview);
        contacts = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CallFriendAdapter(getApplicationContext(), contacts);
        mRecyclerView.setAdapter(mAdapter);

        userRepository = new UserRepository(getApplication());
        userRepository.getmUsers().observe(this, users -> {
            if (users != null) {
                contacts.clear();
                contacts.addAll(users);
                mAdapter.notifyDataSetChanged();
            }
        });


       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), DetailedCallActivity.class);
                intent.putExtra("TARGET", UserPreferencesService.getInstance(getApplication()).getCurrentUser());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_AUDIO_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
