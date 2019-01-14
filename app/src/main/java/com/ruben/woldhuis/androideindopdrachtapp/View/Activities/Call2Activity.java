package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

import static com.ruben.woldhuis.androideindopdrachtapp.Constants.SIP_SERVER_HOSTNAME;


public class Call2Activity extends Activity {

    private Call call;
    private TextView callState;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call2);
        SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId("b")
                .applicationKey(getString(R.string.sinch_application_key))
                .applicationSecret(getString(R.string.sinch_application_secret))
                .environmentHost(SIP_SERVER_HOSTNAME)
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();

        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());


        callState = findViewById(R.id.callState);

        button = findViewById(R.id.call_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make a call!
                if (call == null) {
                    call = sinchClient.getCallClient().callUser("a");
                    call.addCallListener(new SinchCallListener());
                    button.setText(getString(R.string.hang_up));
                } else {
                    call.hangup();
                    call = null;
                    button.setText(getString(R.string.make_call));
                }
            }
        });

    }

    private void askPermissions() {

    }

    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //Pick up the call!
            call = incomingCall;
            call.answer();
            call.addCallListener(new SinchCallListener());
            button.setText(getString(R.string.hang_up));
        }
    }

    private class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            callState.setText("");

        }

        @Override
        public void onCallEstablished(Call establishedCall) {
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            callState.setText(getString(R.string.call_connected));

        }

        @Override
        public void onCallProgressing(Call progressingCall) {
            callState.setText(getString(R.string.call_ringing));
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            //don't worry about this right now
        }
    }
}
