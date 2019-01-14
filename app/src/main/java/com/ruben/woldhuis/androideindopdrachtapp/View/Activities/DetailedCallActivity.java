package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.squareup.picasso.Picasso;

public class DetailedCallActivity extends Activity {
    private static final int RECORD_AUDIO_REQUEST_CODE = 201;
    private static final String TAG = "DETAILED_CALL_ACTIVITY_TAG";
    private FloatingActionButton answerCall;
    private FloatingActionButton cancelCall;
    private ImageView callerImage;
    private TextView callerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_call);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);

        Intent intent = getIntent();
        User target = (User) intent.getSerializableExtra("TARGET");

        answerCall = findViewById(R.id.accept_call_button);
        cancelCall = findViewById(R.id.cancel_call_button);
        callerImage = findViewById(R.id.caller_image);
        callerID = findViewById(R.id.caller_id_text);

        callerID.setText(target.getName());

        if (target.getProfilePictureURL() != null)
            Picasso.get().load(target.getProfilePictureURL()).into(callerImage);


        answerCall.setOnClickListener(view -> {
            Log.d(TAG, "answer call clicked");
        });

        cancelCall.setOnClickListener(view -> {
            Log.d(TAG, "cancel call clicked");
        });

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
