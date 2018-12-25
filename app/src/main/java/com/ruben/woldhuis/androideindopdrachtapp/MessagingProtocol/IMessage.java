package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol;

public interface IMessage {
    MessageType getMessageType();

    String getFireBaseToken();

    String toJson();
}
