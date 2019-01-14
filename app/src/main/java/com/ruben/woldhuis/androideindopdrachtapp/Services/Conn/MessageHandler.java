package com.ruben.woldhuis.androideindopdrachtapp.Services.Conn;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.EventCreationReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.FriendReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.FriendsReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.GetAllEventsReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.SubscribeToEventReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.SyncMissedMessageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UnsubscribeFromEventReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadAudioMessageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadImageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.FriendRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.FriendsRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.GetAllEventsRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.AuthenticationFailedMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.AuthenticationSuccesfulMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.EventChatMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.LocationUpdateMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository.EventRepository;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository.UserRepository;
import com.ruben.woldhuis.androideindopdrachtapp.Services.PushNotification;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;


public class MessageHandler {
    private static final String TAG = "MESSAGE_HANDLER_TAG";
    private static MessageHandler instance;
    private PushNotification pushNotification;
    private Application application;
    private UserRepository repository;

    private MessageHandler(PushNotification pushNotification, Application application) {
        this.pushNotification = pushNotification;
        this.application = application;
        repository = new UserRepository(application);
    }

    public static MessageHandler getInstance(PushNotification pushNotification, Application application) {
        if (instance == null)
            instance = new MessageHandler(pushNotification, application);
        return instance;
    }

    public void handleMessage(IMessage message) {
        switch (message.getMessageType()) {
            case EventChat_Message:
                handleEventChatMessage((EventChatMessage) message);
                break;
            case EventCreationReply_Message:
                handleEventCreationReplyMessage((EventCreationReply) message);
                break;
            case FriendRequest_Message:
                handleFriendRequestMessage((FriendRequest) message);
                break;
            case Text_Message:
                handleTextMessage((TextMessage) message);
                break;
            case FriendReply_Message:
                handleFriendReplyMessage((FriendReply) message);
                break;
            case FriendsReply_Message:
                handleFriendsReplyMessage((FriendsReply) message);
                break;
            case UploadAudioReply_Message:
                handleUploadAudioReplyMessage((UploadAudioMessageReply) message);
                break;
            case LocationUpdate_Message:
                handleLocationUpdateMessage((LocationUpdateMessage) message);
                break;
            case GetAllEventsReply_Message:
                handleGetAllEventsReplyMessage((GetAllEventsReply) message);
                break;
            case UploadImageReply_Message:
                handleUploadImageReplyMessage((UploadImageReply) message);
                break;
            case AuthenticationFailed_Message:
                handleAuthenticationFailedMessage((AuthenticationFailedMessage) message);
                break;
            case SubscribeToEventReply_Message:
                handleSubscribeToEventReply((SubscribeToEventReply) message);
                break;
            case SyncMissedMessagesReply_Message:
                handleSyncMissedMessagesReply((SyncMissedMessageReply) message);
                break;
            case AuthenticationSuccessful_Message:
                handleAuthenticationSuccessfulMessage((AuthenticationSuccesfulMessage) message);
                break;
            case UnsubscribeFromEventReply_Message:
                handleUnsubscribeFromEventReplyMessage((UnsubscribeFromEventReply) message);
                break;
        }
    }


    private void handleEventChatMessage(EventChatMessage message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            pushNotification.sendEventChatMessageNotification(message);
        }
    }

    private void handleEventCreationReplyMessage(EventCreationReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            pushNotification.sendEventCreationNotification(message);
            TcpManagerService.getInstance().submitMessage(new GetAllEventsRequest(
                    UserPreferencesService.getInstance(application).getAuthenticationKey(),
                    UserPreferencesService.getInstance(application).getCurrentUser()
            ));
        }
    }

    private void handleTextMessage(TextMessage message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            pushNotification.sendTextMessageNotification(message);
        }
    }

    private void handleFriendRequestMessage(FriendRequest message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            pushNotification.sendFriendRequestNotification(message);
        }
    }

    private void handleFriendReplyMessage(FriendReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            pushNotification.sendFriendReplyNotification(message);
            TcpManagerService.getInstance().submitMessage(new FriendsRequest(
                    UserPreferencesService.getInstance(application).getAuthenticationKey(),
                    UserPreferencesService.getInstance(application).getCurrentUser()
            ));
        }
    }

    private void handleFriendsReplyMessage(FriendsReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            for (User friend : message.getFriends())
                repository.insertUser(friend);
        }
    }

    private void handleUploadAudioReplyMessage(UploadAudioMessageReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            pushNotification.sendAudioMessageNotification(message);
        }
    }

    private void handleLocationUpdateMessage(LocationUpdateMessage message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            User user = message.getSender();
            user.setLocation(message.getLocation());
            repository.updateUser(user);
        }
    }

    private void handleGetAllEventsReplyMessage(GetAllEventsReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            //TODO: get the events from here and store them somewhere
            EventRepository eventRepository = new EventRepository(application);
            eventRepository.deleteAllEvents();
            Log.d(TAG, "handleGetAllEventsReplyMessage: " + message.getEvents().size());
            message.getEvents().forEach(event -> eventRepository.insertUser(event));
        }
    }

    private void handleUploadImageReplyMessage(UploadImageReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            pushNotification.sendImageMessageNotification(message);
        }
    }

    private void handleAuthenticationFailedMessage(AuthenticationFailedMessage message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            // TODO: log user out
        }
    }

    private void handleSubscribeToEventReply(SubscribeToEventReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(application, application.getString(R.string.subSuccessful), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleAuthenticationSuccessfulMessage(AuthenticationSuccesfulMessage message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {

          /*  TcpManagerService.getInstance().submitMessage(new GetAllEventsRequest(
                    UserPreferencesService.getInstance(application).getAuthenticationKey(),
                    UserPreferencesService.getInstance(application).getCurrentUser()
            ));*/
        }
    }

    private void handleSyncMissedMessagesReply(SyncMissedMessageReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            if (message.getMessages() != null)
                for (IMessage msg : message.getMessages())
                    handleMessage(msg);
        }
    }

    private void handleUnsubscribeFromEventReplyMessage(UnsubscribeFromEventReply message) {
        Log.d(TAG, "RECEIVED: " + message.toJson());
        if (!message.getFireBaseToken().equals("SERVER")) {
            Toast.makeText(application, application.getText(R.string.unregisteredSourceNotification), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(application, application.getString(R.string.unsubSuccessful), Toast.LENGTH_SHORT).show();
        }
    }
}
