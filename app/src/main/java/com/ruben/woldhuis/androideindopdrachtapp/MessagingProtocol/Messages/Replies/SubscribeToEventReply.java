package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class SubscribeToEventReply implements IMessage {
    private MessageType messageType = MessageType.SubscribeToEventReply_Message;
    private String fireBaseToken;
    private User sender;
    private Event event;

    public SubscribeToEventReply(String fireBaseToken, User sender, Event event) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.event = event;
    }

    public static SubscribeToEventReply fromJson(String json) {
        return Constants.GSON.fromJson(json, SubscribeToEventReply.class);
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

    public Event getEvent() {
        return event;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
