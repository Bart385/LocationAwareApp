package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;

public class IdentificationMessage implements IMessage {
    private MessageType messageType = MessageType.Identification_Message;
    private String fireBaseToken;

    public IdentificationMessage(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }

    public static IdentificationMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, IdentificationMessage.class);
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
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
