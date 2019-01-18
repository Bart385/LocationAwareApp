package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class AuthenticationFailedMessage implements IMessage {
    private MessageType messageType = MessageType.AuthenticationFailed_Message;
    private String fireBaseToken = "SERVER";
    private User sender = null;

    public AuthenticationFailedMessage() {
    }

    public static AuthenticationFailedMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, AuthenticationFailedMessage.class);
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

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}