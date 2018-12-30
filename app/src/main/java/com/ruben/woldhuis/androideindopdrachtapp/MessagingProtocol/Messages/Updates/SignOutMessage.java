package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;

public class SignOutMessage implements IMessage {
    private MessageType messageType = MessageType.SignOut_Message;
    private String fireBaseToken;
    private boolean signOut;

    public SignOutMessage(String fireBaseToken, boolean signOut) {
        this.fireBaseToken = fireBaseToken;
        this.signOut = signOut;
    }

    public static SignOutMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, SignOutMessage.class);
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
