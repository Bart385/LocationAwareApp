package com.ruben.woldhuis.androideindopdrachtapp.Messages;

/**
 *
 */
public interface IMessage {
    /**
     * @return
     */
    MessageType getMessageType();

    /**
     * @return
     */
    String serialize();
}