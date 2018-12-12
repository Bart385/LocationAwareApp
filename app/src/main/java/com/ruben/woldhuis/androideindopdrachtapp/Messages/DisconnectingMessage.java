package com.ruben.woldhuis.androideindopdrachtapp.Messages;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;

import java.time.LocalDateTime;

public class DisconnectingMessage implements IMessage {
    private MessageType messageType = MessageType.Disconnecting_Message;
    private String sender;
    private LocalDateTime timeSend;
    private String message;

    /**
     * @param sender
     * @param timeSend
     * @param message
     */
    public DisconnectingMessage(String sender, LocalDateTime timeSend, String message) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
    }

    public static DisconnectingMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, DisconnectingMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return null;
    }

    public LocalDateTime getTimeSend() {
        return null;
    }

    public String getMessage() {
        return null;
    }

    @Override
    public String serialize() {
        return Constants.GSON.toJson(this);
    }

    @Override
    public String toString() {
        return "DisconnectingMessage{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                ", timeSend=" + timeSend +
                ", message='" + message + '\'' +
                '}';
    }
}
