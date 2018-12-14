package com.ruben.woldhuis.androideindopdrachtapp.Services.Conn;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Handles the notifications
 */
public class BackgroundMessageService extends IntentService {
    private static final String TAG = "SERVICE_TAG";
    private static final String CHANNEL_ID = "MESSAGE_NOTIFICATION";
    private Socket socket;
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    public BackgroundMessageService() {
        super("Background messaging service");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //   createNotificationChannel();
        //  CompletableFuture.runAsync(createConnectionAsync());
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Message", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel for issuing message notifications.");
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    NotificationCompat.Builder mBuilder;

    private Runnable createConnectionAsync() {
        return () -> {
            try {
                this.socket = new Socket(Constants.SERVER_HOSTNAME, Constants.SERVER_PORT);
                this.toServer = new DataOutputStream(socket.getOutputStream());
                this.toServer.flush();
                this.fromServer = new DataInputStream(socket.getInputStream());
                Log.d(TAG, "Connected to server");
                fromServer.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    int notificationId = 0;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent == null){

        }
        Log.d(TAG, "HANDLING INTENT");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");

        for (int i = 0; i < 5; i++) {
            Log.d(TAG, "Counting... " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("message", "Counting done...");
        receiver.send(1234, bundle);
             /*   NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.support.v4.R.drawable.notification_bg)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        for (int i = 0; i < 10; i++) {
            notificationManager.notify(notificationId, mBuilder.build());
            notificationId++;
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
