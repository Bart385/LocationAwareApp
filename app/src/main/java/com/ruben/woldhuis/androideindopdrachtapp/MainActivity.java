package com.ruben.woldhuis.androideindopdrachtapp;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.ImageMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.BackgroundMessageService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.NavigationDrawerFragment;

import java.util.Date;

public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    private static MainActivity instance;

    private SupportMapFragment mapFragment;
    private android.support.v4.app.FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  askPermissions();

        TcpManagerService tcpManagerService = TcpManagerService.getInstance(
                (Error error) -> {
                    Log.d("ERROR_TAG", error.getMessage());
                }, (IMessage message) -> {
                    Log.d("MESSAGE_TAG", message.getMessageType().toString());
                });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // tcpManagerService.submitMessage(new IdentificationMessage("Me", new Date(), "Hello world!"));
        int[] colors = {Color.BLUE, Color.RED, Color.GREEN};
        Bitmap img = createImage(3840, 2160, Color.MAGENTA);
        Log.d("IMAGE_TAG", img.toString());
        tcpManagerService.submitMessage(new ImageMessage("Phone", ".jpg", new Date(), img));
        Bitmap img2 = createImage(1920, 1080, Color.BLUE);
        Log.d("IMAGE_TAG", img.toString());
        tcpManagerService.submitMessage(new ImageMessage("Phone", ".jpg", new Date(), img2));


        //  startBackgroundMessagingService();
<<<<<<< HEAD
*/

        instance = this;
        fragmentManager = getSupportFragmentManager();
        mapFragment = new SupportMapFragment();

        fragmentManager.beginTransaction().replace(R.id.container, mapFragment).commit();

=======
>>>>>>> develop

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.setUpHeader();

    }

<<<<<<< HEAD

=======
    public static Bitmap createImage(int width, int height, int color) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paint);
        return bitmap;
    }
>>>>>>> develop

    private void startBackgroundMessagingService() {
        Intent startService = new Intent(this, BackgroundMessageService.class);
        startService(startService);
        BackgroundMessageService.setErrorListener(error -> Log.d("ERROR_TAG", error.getMessage()));
        BackgroundMessageService.setMessageReceiverListener(message -> Log.d("MESSAGE_TAG", message.getMessageType().toString()));
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
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
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

    public static MainActivity getInstance() {
        return instance;
    }
}
