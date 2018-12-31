package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class FriendsRequest implements IMessage {
    private MessageType messageType = MessageType.FriendsRequest_Message;
    private String fireBaseToken;
    private User sender;

    public FriendsRequest(String fireBaseToken, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
    }

    public static FriendsRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, FriendsRequest.class);
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
