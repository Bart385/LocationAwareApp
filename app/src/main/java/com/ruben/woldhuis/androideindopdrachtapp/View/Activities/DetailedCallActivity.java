package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository.UserRepository;
import com.ruben.woldhuis.androideindopdrachtapp.Services.SIP.SinchManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailedCallActivity extends FragmentActivity {
    private static final String TAG = "DETAILED_CALL_ACTIVITY_TAG";
    private Call mCall;
    private SinchManagerService mSinchManager;
    private UserRepository userRepository;
    private FloatingActionButton answerCall;
    private FloatingActionButton cancelCall;
    private ImageView callerImage;
    private TextView callerID;
    private boolean initialized = false;
    private User target;
    private String userUID = null;
    private String call_ID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detailed_call);
        userRepository = new UserRepository(getApplication());
        userRepository.getmUsers().observe(this, users -> {
            if (!initialized)
                initCall(users);
            initialized = true;
        });
        Intent intent = getIntent();
        target = (User) intent.getSerializableExtra("TARGET");
        userUID = intent.getStringExtra("USER_UID");
        call_ID = intent.getStringExtra("CALL_ID");
    }

    private void initCall(List<User> users) {
        if (target == null)
            for (User u : users) {
                if (u.getUid().equals(userUID)) {
                    target = u;
                    break;
                }
            }
        Log.d(TAG, "initCall: " + users.size());
        mSinchManager = SinchManagerService.getInstance(getApplication(), UserPreferencesService.getInstance(getApplication()).getCurrentUser());

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
            if (mCall != null)
                mCall.hangup();
            finish();
        });


        CallClient callClient = mSinchManager.getSinchClient().getCallClient();
        if (call_ID == null)
            mCall = callClient.callUser(target.getUid());
        else
            mCall = callClient.getCall(call_ID);
        callClient.addCallClientListener(new SinchCallClientListener());

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
