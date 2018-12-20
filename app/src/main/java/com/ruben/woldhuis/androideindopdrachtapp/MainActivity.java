package com.ruben.woldhuis.androideindopdrachtapp;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.LocationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.BackgroundMessageService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.google.android.gms.maps.SupportMapFragment;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.Camera2Activity;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.NavigationDrawerFragment;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnMapReadyCallback {


    private static MainActivity instance;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private SupportMapFragment mapFragment;
    private android.support.v4.app.FragmentManager fragmentManager;

    MapFragment mMapFragment;

    public static Bitmap createImage(int width, int height, int color) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paint);
        return bitmap;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        //Intent intent = new Intent(this, Camera2Activity.class);
        setContentView(R.layout.activity_main);
        //  askPermissions();

   /*     TcpManagerService tcpManagerService = TcpManagerService.getInstance(
                (Error error) -> {
                    Log.d("ERROR_TAG", error.getMessage());
                }, (IMessage message) -> {
                    Log.d("MESSAGE_TAG", message.serialize());
                });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // tcpManagerService.submitMessage(new IdentificationMessage("Me", new Date(), "Hello world!"));
        Bitmap img = createImage(1920, 1080, Color.GREEN);
        Log.d("IMAGE_TAG", img.toString());
        tcpManagerService.submitMessage(new ImageMessage("Phone", ".jpg", new Date(), img));
        Bitmap img2 = createImage(1920, 1080, Color.BLUE);
        Log.d("IMAGE_TAG", img.toString());
        tcpManagerService.submitMessage(new ImageMessage("Phone", ".jpg", new Date(), img2));
        Bitmap img3 = createImage(1920, 1080, Color.RED);
        Log.d("IMAGE_TAG", img.toString());
        tcpManagerService.submitMessage(new ImageMessage("Phone", ".jpg", new Date(), img3));
*/
        //  startBackgroundMessagingService();


              instance = this;
              fragmentManager = getSupportFragmentManager();
              mapFragment = new SupportMapFragment();
              fragmentManager.beginTransaction().replace(R.id.container, mapFragment).commit();


      /*  MessageReceiver receiver = new MessageReceiver(new Message());
        Intent intent = new Intent(this, BackgroundMessageService.class);
        intent.putExtra("receiver", receiver);
        startService(intent);
*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.setUpHeader();

        //Alles voor de map

        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, mMapFragment);
        fragmentTransaction.commit();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
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

    public class Message {
        public void displayMessage(int resultCode, Bundle resultData) {
            String message = resultData.getString("message");
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}

