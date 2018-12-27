package com.ruben.woldhuis.androideindopdrachtapp.Services.Conn;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.AudioMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.ImageMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.ImageUploadedMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.LocationUpdateMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.SignOutMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.TextMessage;

public class BackgroundMessageService extends IntentService {
    private static final String TAG = "BACKGROUND_MESSAGE_TAG";
    private TcpManagerService tcpManagerService;

    public BackgroundMessageService() {
        super("Messaging service");
        tcpManagerService = TcpManagerService.getInstance();
        tcpManagerService.subscribeToErrorEvents(error -> Log.e(TAG, error.getMessage()));
        tcpManagerService.subscribeToMessageEvents(message -> {
            switch (message.getMessageType()) {
                case LocationUpdate_Message:
                    handleLocationUpdateMessage((LocationUpdateMessage) message);
                    break;
                case ImageUploaded_Message:
                    handleImageUploadedMessage((ImageUploadedMessage) message);
                    break;
                case SignOut_Message:
                    handleSignOutMessage((SignOutMessage) message);
                    break;
                case Image_Message:
                    handleImageMessage((ImageMessage) message);
                    break;
                case Audio_Message:
                    handleAudioMessage((AudioMessage) message);
                    break;
                case Text_Message:
                    handleTextMessage((TextMessage) message);
                    break;
                case Identification_Message:
                    handleIdentificationMessage((IdentificationMessage) message);
                    break;
            }
        });
    }

    private void handleLocationUpdateMessage(LocationUpdateMessage message) {
        
    }

    private void handleImageUploadedMessage(ImageUploadedMessage message) {

    }

    private void handleSignOutMessage(SignOutMessage message) {

    }

    private void handleImageMessage(ImageMessage message) {

    }

    private void handleAudioMessage(AudioMessage message) {

    }

    private void handleTextMessage(TextMessage message) {

    }

    private void handleIdentificationMessage(IdentificationMessage message) {

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
