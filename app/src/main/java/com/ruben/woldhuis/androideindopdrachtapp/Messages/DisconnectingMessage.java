package com.ruben.woldhuis.androideindopdrachtapp.Messages;

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

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getSender() {
        return null;
    }

    @Override
    public LocalDateTime getTimeSend() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String serialize() {
        return null;
    }
}
