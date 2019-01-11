package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

import java.util.ArrayList;

public class GetAllEventsReply implements IMessage {
    private MessageType messageType = MessageType.GetAllEventsReply_Message;
    private String fireBaseToken;
    private User sender;
    private ArrayList<Event> events;

    public GetAllEventsReply(String fireBaseToken, User sender, ArrayList<Event> events) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.events = events;
    }

    public static GetAllEventsReply fromJson(String json) {
        return Constants.GSON.fromJson(json, GetAllEventsReply.class);
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

    public ArrayList<Event> getEvents() {
        return events;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
