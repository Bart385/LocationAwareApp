package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class SignOutMessage implements IMessage {
    private MessageType messageType = MessageType.SignOut_Message;
    private String fireBaseToken;
    private boolean signOut;
    private User sender;

    public SignOutMessage(String fireBaseToken, boolean signOut, User sender) {
        this.fireBaseToken = fireBaseToken;
        this.signOut = signOut;
        this.sender = sender;
    }

    public static SignOutMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, SignOutMessage.class);
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

    public boolean isSignOut() {
        return signOut;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
