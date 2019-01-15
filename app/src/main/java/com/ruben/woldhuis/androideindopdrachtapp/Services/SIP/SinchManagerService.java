package com.ruben.woldhuis.androideindopdrachtapp.Services.SIP;

import android.app.Application;

import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;

import static com.ruben.woldhuis.androideindopdrachtapp.Constants.SIP_SERVER_HOSTNAME;

public class SinchManagerService {
    private static SinchManagerService instance;
    private Application application;
    private SinchClient sinchClient;

    public static SinchManagerService getInstance(Application application) {
        if (instance == null)
            instance = new SinchManagerService(application);
        return instance;
    }

    private SinchManagerService(Application application) {
        this.application = application;
        sinchClient = Sinch.getSinchClientBuilder()
                .context(application)
                .userId(UserPreferencesService.getInstance(application).getCurrentUser().getUid())
                .applicationKey(application.getString(R.string.sinch_application_key))
                .applicationSecret(application.getString(R.string.sinch_application_secret))
                .environmentHost(SIP_SERVER_HOSTNAME)
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.setSupportActiveConnectionInBackground(true);
        sinchClient.setSupportManagedPush(true);
        sinchClient.start();
    }

    public SinchClient getSinchClient() {
        return sinchClient;
    }

}
