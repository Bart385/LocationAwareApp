package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class EventChatMessage implements IMessage {
    private MessageType messageType = MessageType.EventChat_Message;
    private String fireBaseToken;
    private User sender;
    private String eventUID;
    private IMessage[] content;

    public EventChatMessage(String fireBaseToken, User sender, String eventUID, IMessage... content) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.eventUID = eventUID;
        this.content = content;
    }

    public static EventChatMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, EventChatMessage.class);
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

    public IMessage[] getContent() {
        return content;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
