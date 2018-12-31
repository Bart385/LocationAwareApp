package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public interface IMessage {
    MessageType getMessageType();

    String getFireBaseToken();

    User getSender();

    String toJson();
}
