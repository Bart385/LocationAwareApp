package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class UploadAudioMessageReply implements IMessage {
    private MessageType messageType = MessageType.UploadAudioReply_Message;
    private String fireBaseToken;
    private User sender;
    private String audioFileUrl;

    public UploadAudioMessageReply(String fireBaseToken, User sender, String audioFileUrl) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.audioFileUrl = audioFileUrl;
    }

    public static UploadAudioMessageReply fromJson(String json) {
        return Constants.GSON.fromJson(json, UploadAudioMessageReply.class);
    }

    @Override
    public User getSender() {
        return sender;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getAudioFileUrl() {
        return audioFileUrl;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
