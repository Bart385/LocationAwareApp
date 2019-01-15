package com.ruben.woldhuis.androideindopdrachtapp;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.MessageHandler;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository.UserRepository;
import com.ruben.woldhuis.androideindopdrachtapp.Services.PushNotification;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.VolleyRequests.GetServerRequest;
import com.ruben.woldhuis.androideindopdrachtapp.Services.VolleyService;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.LoginActivity;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.MapFragment;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.NavigationDrawerFragment;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String TAG = "MAIN_ACTIVITY_TAG";

    private static MainActivity instance;

    private VolleyService volleyService;
    private FirebaseAuth mAuth;
    private MapFragment mMapFragment;
    private UserPreferencesService userPreferencesService;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private android.support.v4.app.FragmentManager fragmentManager;
    private UserRepository userRepository;

    public static MainActivity getInstance() {
        return instance;
    }

    //TODO: Check for googleplayservices
    /*https://firebase.google.com/docs/cloud-messaging/android/client*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Log.d(TAG, "onCreate: " + key);
            }
        }
        volleyService = VolleyService.getInstance(getApplication());
      //  volleyService.doRequest(new GetServerRequest());
        userPreferencesService = UserPreferencesService.getInstance(getApplication());
        userRepository = new UserRepository(getApplication());
        mAuth = FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    userPreferencesService.saveFireBaseMessagingId(token);
                    authenticateWithServer();
                    // Log and toast
                    Log.d(TAG, token);
                });

        setContentView(R.layout.activity_main);
        //  askPermissions();
        instance = this;
        mMapFragment = new

                MapFragment();

        fragmentManager =

                getSupportFragmentManager();
        fragmentManager.beginTransaction().

                replace(R.id.container, mMapFragment).

                commit();

        mNavigationDrawerFragment = (NavigationDrawerFragment)

                getSupportFragmentManager().

                        findFragmentById(R.id.navigation_drawer);

        mTitle =

                getTitle();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout)

                        findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.setUpHeader();

        //Alles voor de map

        mMapFragment.getMapAsync(googleMap -> {
            Toast.makeText(getApplicationContext(), TAG, Toast.LENGTH_LONG).show();
            mMapFragment.onMapReady(googleMap);
        });
        mMapFragment.onCreate(savedInstanceState);
        userRepository.getmUsers().observe(this, friends -> mMapFragment.updateFriends(friends));
        User user = new User("TEST", "TEST", "TEST");
        user.setLocation(new Location(1, 5));
        user.setProfilePictureURL("http://206.189.3.15/images/Testing.jpg");
        userRepository.deleteUser(user);
    }

    private void authenticateWithServer() {
        TcpManagerService.getInstance().subscribeToMessageEvents(message ->
                MessageHandler.getInstance(PushNotification.getInstance(getApplication(), this), getApplication()).handleMessage(message));
        TcpManagerService.getInstance().subscribeToErrorEvents(error -> Log.e("MAIN_ACTIVITY_TAG", error.getMessage()));
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            //Log.d("MAIN_ACTIVITY_TAG", userPreferencesService.getAuthenticationKey());
            mAuth.getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            if (idToken != null) {
                                UserPreferencesService.getInstance(getApplication()).saveAuthenticationKey(idToken);
                                TcpManagerService.getInstance().submitMessage(new IdentificationMessage(idToken, userPreferencesService.getFireBaseMessagingId()));

                            }
                        } else
                            Log.e("IDENTIFICATION_TAG", task.getException().getMessage());
                    });
        } /*else {
            Log.d("MAIN_ACTIVITY_TAG", userPreferencesService.getAuthenticationKey());
            TcpManagerService.getInstance().submitMessage(new IdentificationMessage(userPreferencesService.getAuthenticationKey()));
        }*/
    }

    private void setupGoogleMaps() {

    }

    private void askPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.GPS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.GPS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Do Something
                } else {
                    Log.d("PERMISSION_TAG", "No location permission");
                    finish();
                }
                break;

        }
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
            case 5:
                mTitle = getString(R.string.title_section5);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }
}

