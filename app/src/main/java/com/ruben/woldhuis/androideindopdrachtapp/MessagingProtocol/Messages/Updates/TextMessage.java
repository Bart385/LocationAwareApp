package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

import java.io.Serializable;

public class TextMessage implements IMessage, Serializable {
    private MessageType messageType = MessageType.Text_Message;
    private String fireBaseToken;
    private String textMessage;
    private User target;

    public TextMessage(String fireBaseToken, String textMessage, User target) {
        this.fireBaseToken = fireBaseToken;
        this.textMessage = textMessage;
        this.target = target;
    }

    public static TextMessage fromJson(String json) {
        return Constants.GSON.fromJson(json, TextMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public User getTarget() {
        return target;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
