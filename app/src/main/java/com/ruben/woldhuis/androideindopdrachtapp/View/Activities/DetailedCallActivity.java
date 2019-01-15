package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.SIP.SinchManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailedCallActivity extends Activity {
    private static final String TAG = "DETAILED_CALL_ACTIVITY_TAG";
    private Call mCall;
    private SinchManagerService mSinchManager;

    private FloatingActionButton answerCall;
    private FloatingActionButton cancelCall;
    private ImageView callerImage;
    private TextView callerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detailed_call);

        Intent intent = getIntent();
        User target = (User) intent.getSerializableExtra("TARGET");
        mSinchManager = SinchManagerService.getInstance(getApplication(), UserPreferencesService.getInstance(getApplication()).getCurrentUser());

        answerCall = findViewById(R.id.accept_call_button);
        cancelCall = findViewById(R.id.cancel_call_button);
        callerImage = findViewById(R.id.caller_image);
        callerID = findViewById(R.id.caller_id_text);

        CallClient callClient = mSinchManager.getSinchClient().getCallClient();
        mCall = callClient.callUser(target.getUid());
        callClient.addCallClientListener(new SinchCallClientListener());


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
    protected void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }



    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //Pick up the call!
            mCall = incomingCall;
            mCall.answer();
            mCall.addCallListener(new SinchCallListener());
        }
    }

    private class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            //      statusListener.onStatusTextChanged("");

        }

        @Override
        public void onCallEstablished(Call establishedCall) {
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            //   callState.setText(getString(R.string.call_connected));

        }

        @Override
        public void onCallProgressing(Call progressingCall) {
            //  callState.setText(getString(R.string.call_ringing));
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            //don't worry about this right now
        }
    }
}
