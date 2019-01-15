package com.ruben.woldhuis.androideindopdrachtapp.Services;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.EventCreationReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.FriendReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadAudioMessageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadImageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.FriendRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.EventChatMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.ChatActivity;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.EventActivity;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.OnFriendRequestDialog;

public class PushNotification {
    private static final String NOTIFICATION_CHANNEL = "notification_channel";
    private static final String TAG = "PUSH_NOTIFICATION_TAG";
    private static PushNotification instance;
    private NotificationManagerCompat notificationManager;
    private Application application;
    private Activity activity;
    private int notificationIndex = 1;

    private PushNotification(Application application, Activity activity) {
        this.activity = activity;
        this.application = application;
        notificationManager = NotificationManagerCompat.from(application);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chatChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    "New Chat Message",
                    NotificationManager.IMPORTANCE_HIGH
            );
            chatChannel.setDescription("A notification appears when another user sends a message");

            NotificationManager manager = application.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chatChannel);
        }
    }

    public static PushNotification getInstance(Application application, Activity activity) {
        if (instance == null)
            instance = new PushNotification(application, activity);
        return instance;
    }

    public void sendTextMessageNotification(TextMessage message) {
        Intent resultIntent = new Intent(application, ChatActivity.class);
        resultIntent.putExtra("ContactObject", message.getSender());
        resultIntent.putExtra("message", message);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getTextMessage())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(notificationIndex, notification);
        notificationIndex++;
    }

    public void sendEventChatMessageNotification(EventChatMessage message) {
        Intent resultIntent = new Intent(application, EventActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle("EVENT CHAT")
                .setContentText(message.getEventUID())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(notificationIndex, notification);
        notificationIndex++;
    }

    public void sendEventCreationNotification(EventCreationReply message) {
        Intent resultIntent = new Intent(application, EventActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getEventName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(notificationIndex, notification);
        notificationIndex++;
    }

    public void sendFriendRequestNotification(FriendRequest message) {
        Intent resultIntent = new Intent(application, OnFriendRequestDialog.class);
        resultIntent.putExtra("TARGET", message.getSender());
        activity.startActivity(resultIntent);
        /*
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getSender().getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(notificationIndex, notification);
        notificationIndex++;
*/
    }

    public void sendFriendReplyNotification(FriendReply message) {
        Intent resultIntent = new Intent(application, ChatActivity.class);
        resultIntent.putExtra("ContactObject", message.getSender());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getFriend().getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(notificationIndex, notification);
        notificationIndex++;
    }


    public void sendAudioMessageNotification(UploadAudioMessageReply message) {
        Intent resultIntent = new Intent(application, ChatActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getSender().getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(notificationIndex, notification);
        notificationIndex++;
    }

    public void sendImageMessageNotification(UploadImageReply message) {
        Intent resultIntent = new Intent(application, ChatActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.twitter_button)
                .setContentTitle(message.getMessageType().name())
                .setContentText(message.getSender().getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(notificationIndex, notification);
        notificationIndex++;
    }
}

