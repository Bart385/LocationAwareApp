package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class ProfilePictureUpdate implements IMessage {
    private MessageType messageType = MessageType.ProfilePictureUpdate_Message;
    private String fireBaseToken;
    private User sender;
    private String profilePictureURL;

    public ProfilePictureUpdate(String fireBaseToken, User sender, String profilePictureURL) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.profilePictureURL = profilePictureURL;
    }

    public static ProfilePictureUpdate fromJson(String json) {
        return Constants.GSON.fromJson(json, ProfilePictureUpdate.class);
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

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
