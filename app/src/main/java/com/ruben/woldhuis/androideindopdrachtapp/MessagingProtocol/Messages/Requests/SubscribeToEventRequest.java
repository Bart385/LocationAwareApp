package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class SubscribeToEventRequest implements IMessage {
    private MessageType messageType = MessageType.SubscribeToEventRequest_Message;
    private String fireBaseToken;
    private User sender;
    private String eventUID;

    public SubscribeToEventRequest(String fireBaseToken, User sender, String eventUID) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.eventUID = eventUID;
    }

    public static SubscribeToEventRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, SubscribeToEventRequest.class);
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
    public User getSender() {
        return sender;
    }

    public String getEventUID() {
        return eventUID;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
