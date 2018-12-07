package com.ruben.woldhuis.androideindopdrachtapp.Messages;

import java.time.LocalDateTime;

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
    String getSender();

    /**
     * @return
     */
    LocalDateTime getTimeSend();

    /**
     * @return
     */
    String getMessage();

    /**
     * @return
     */
    String serialize();
}