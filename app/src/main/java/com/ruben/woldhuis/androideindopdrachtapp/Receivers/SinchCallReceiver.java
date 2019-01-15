package com.ruben.woldhuis.androideindopdrachtapp.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.DetailedCallActivity;

public class SinchCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent callIntent = new Intent(context, DetailedCallActivity.class);
        context.startActivity(callIntent);
    }
}
