package com.ruben.woldhuis.androideindopdrachtapp.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.Camera2Activity;

public class PushNotification {
    private NotificationManagerCompat notificationManager;
    private static PushNotification instance;
    private static final String NOTIFICAITON_CHANNEL = "notification_channel";

    private PushNotification(Context context) {
        notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel sightChannel = new NotificationChannel(
                    NOTIFICAITON_CHANNEL,
                    "Nearby sight",
                    NotificationManager.IMPORTANCE_HIGH
            );
            sightChannel.setDescription("the notification appears when a user is near a sight with the tour running");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(sightChannel);
        }
    }

    public static PushNotification getInstance(Context context) {
        if (instance == null)
            instance = new PushNotification(context);
        return instance;
    }

    public void SendTextMessageNotification(TextMessage message, Context context) {
        Notification notification = new NotificationCompat.Builder(context, NOTIFICAITON_CHANNEL)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(message.getTarget().getFirstName())
                .setContentText(message.getTextMessage())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(1, notification);
    }

    public void SendIdentificationNotification(IdentificationMessage message, Context context) {
        Intent resultIntent = new Intent(context, Camera2Activity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("PUSH_NOTIFICATION_TAG", "SendIdentificationNotification: ");
        Notification notification = new NotificationCompat.Builder(context, NOTIFICAITON_CHANNEL)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getFireBaseToken())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(1, notification);
    }
}
