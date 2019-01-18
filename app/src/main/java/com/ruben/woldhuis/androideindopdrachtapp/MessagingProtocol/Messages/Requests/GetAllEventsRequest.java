package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class GetAllEventsRequest implements IMessage {
    private MessageType messageType = MessageType.GetAllEventsRequest_Message;
    private String fireBaseToken;
    private User sender;

    public GetAllEventsRequest(String fireBaseToken, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
    }

    public static GetAllEventsRequest fromJson(String json) {
        return Constants.GSON.fromJson(json, GetAllEventsRequest.class);
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

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
