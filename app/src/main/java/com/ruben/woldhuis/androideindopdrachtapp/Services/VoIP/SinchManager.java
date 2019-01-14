package com.ruben.woldhuis.androideindopdrachtapp.Services.VoIP;

import android.app.Application;
import android.media.AudioManager;

import com.ruben.woldhuis.androideindopdrachtapp.Listeners.CallStatusListener;
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

public class SinchManager {
    private static SinchManager instance;
    private Application application;
    private Call mCall;
    private CallStatusListener statusListener;

    public static SinchManager getInstance(Application application, CallStatusListener statusListener) {
        if (instance == null)
            instance = new SinchManager(application);
        instance.changeCallStatusListener(statusListener);
        return instance;
    }


    private SinchManager(Application application) {
        this.application = application;
        SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(application)
                .userId("b")
                .applicationKey(application.getString(R.string.sinch_application_key))
                .applicationSecret(application.getString(R.string.sinch_application_secret))
                .environmentHost(SIP_SERVER_HOSTNAME)
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();
       // sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
    }

    public void changeCallStatusListener(CallStatusListener statusListener) {
        this.statusListener = statusListener;
    }

/*
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
            statusListener.onStatusTextChanged("");

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
    }*/
}
