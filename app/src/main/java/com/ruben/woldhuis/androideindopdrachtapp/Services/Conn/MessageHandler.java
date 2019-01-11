package com.ruben.woldhuis.androideindopdrachtapp.Services.Conn;

import android.app.Application;
import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.EventCreationReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadAudioMessageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadImageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.UploadImageRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.AuthenticationSuccesfulMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.LocationUpdateMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.SignOutMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Services.PushNotification;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;


public class MessageHandler {
    private static final String TAG = "MESSAGE_HANDLER_TAG";
    private static MessageHandler instance;
    private PushNotification pushNotification;
    private Application application;

    private MessageHandler(PushNotification pushNotification, Application application) {
        this.pushNotification = pushNotification;
        this.application = application;
    }

    public static MessageHandler getInstance(PushNotification pushNotification, Application application) {
        if (instance == null)
            instance = new MessageHandler(pushNotification, application);
        return instance;
    }

    public void handleMessage(IMessage message) {
        switch (message.getMessageType()) {
            case LocationUpdate_Message:
                handleLocationUpdateMessage((LocationUpdateMessage) message);
                break;
            case UploadImageReply_Message:
                handleImageUploadedMessage((UploadImageReply) message);
                break;
            case SignOut_Message:
                handleSignOutMessage((SignOutMessage) message);
                break;
            case UploadImageRequest_Message:
                handleImageMessage((UploadImageRequest) message);
                break;
            case UploadAudioReply_Message:
                handleAudioMessage((UploadAudioMessageReply) message);
                break;
            case Text_Message:
                handleTextMessage((TextMessage) message);
                break;
            case Identification_Message:
                handleIdentificationMessage((IdentificationMessage) message);
                break;
            case FriendReply_Message:
                break;
            case FriendsReply_Message:
                break;
            case FriendRequest_Message:
                break;
            case FriendsRequest_Message:
                break;
            case UploadAudioRequest_Message:
                break;
            case AuthenticationFailed_Message:
                break;
            case AuthenticationSuccessful_Message:
                handleAuthenticationSuccessfulMessage((AuthenticationSuccesfulMessage) message);
                break;
            case EventCreationReply_Message:
                handleEventCreationReply((EventCreationReply) message);
                break;
            case EventCreationRequest_Message:
                break;
        }
    }

    private void handleLocationUpdateMessage(LocationUpdateMessage message) {
        Log.d(TAG, message.toJson());
    }

    private void handleImageUploadedMessage(UploadImageReply message) {
        Log.d(TAG, message.toJson());
    }

    private void handleSignOutMessage(SignOutMessage message) {
        Log.d(TAG, message.toJson());
    }

    private void handleImageMessage(UploadImageRequest message) {
        Log.d(TAG, message.toJson());
    }

    private void handleAudioMessage(UploadAudioMessageReply message) {
        Log.d(TAG, message.toJson());
    }

    private void handleTextMessage(TextMessage message) {
        Log.d(TAG, message.toJson());
        pushNotification.sendTextMessageNotification(message, application);
    }

    private void handleIdentificationMessage(IdentificationMessage message) {
        Log.d(TAG, message.toJson());
        pushNotification.sendIdentificationNotification(message, application);
    }

    private void handleAuthenticationSuccessfulMessage(AuthenticationSuccesfulMessage message) {
        Log.d(TAG, message.toJson());
        UserPreferencesService.getInstance(application).saveCurrentUser(message.getUser());
    }

    private void handleEventCreationReply(EventCreationReply message) {
        Log.d(TAG, message.toJson());
    }
}
