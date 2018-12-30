package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;

public class FriendsRequest implements IMessage {
    private MessageType messageType = MessageType.FriendsRequest_Message;
    private String fireBaseToken;

    public FriendsRequest(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }

    public static FriendsRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, FriendsRequest.class);
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
