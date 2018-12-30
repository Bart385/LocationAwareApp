package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;

public class FriendRequest implements IMessage {
    private MessageType messageType = MessageType.FriendRequest_Message;
    private String fireBaseToken;
    private String friendEmail;

    public FriendRequest(String fireBaseToken, String friendEmail) {
        this.fireBaseToken = fireBaseToken;
        this.friendEmail = friendEmail;
    }

    public static FriendRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, FriendRequest.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
