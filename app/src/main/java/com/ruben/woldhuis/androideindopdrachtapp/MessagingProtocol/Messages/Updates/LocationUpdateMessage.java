package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class LocationUpdateMessage implements IMessage {
    private MessageType messageType = MessageType.LocationUpdate_Message;
    private String fireBaseToken;
    private Location location;
    private User sender;

    public LocationUpdateMessage(String fireBaseToken, Location location, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.location = location;
        this.sender = sender;
    }

    public static LocationUpdateMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, LocationUpdateMessage.class);
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

    public Location getLocation() {
        return location;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
