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

import com.ruben.woldhuis.androideindopdrachtapp.MainActivity;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadAudioMessageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.ChatActivity;

public class PushNotification {
    private static final String NOTIFICATION_CHANNEL = "notification_channel";
    private static final String TAG = "PUSH_NOTIFICATION_TAG";
    private static PushNotification instance;
    private NotificationManagerCompat notificationManager;

    private PushNotification(Context context) {
        notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chatChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    "New Chat Message",
                    NotificationManager.IMPORTANCE_HIGH
            );
            chatChannel.setDescription("A notification appears when another user sends a message");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chatChannel);
        }
    }

    public static PushNotification getInstance(Context context) {
        if (instance == null)
            instance = new PushNotification(context);
        return instance;
    }

    public void sendTextMessageNotification(TextMessage message, Context context) {
        Intent resultIntent = new Intent(context, ChatActivity.class);

        resultIntent.putExtra("message", message);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getTextMessage())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendImageMessageNotification(UploadAudioMessageReply message, Context context) {

    }

    public void sendIdentificationNotification(IdentificationMessage message, Context context) {
        Intent resultIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getFireBaseToken())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(1, notification);
    }
}
