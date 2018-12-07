package com.ruben.woldhuis.androideindopdrachtapp.Messages;

import java.nio.ByteBuffer;

public class MessageSerializer {
    /**
     * @param message
     * @return
     */
    public static byte[] serialize(IMessage message) {
        String msg = message.serialize();
        byte[] prefix = ByteBuffer.allocate(4).putInt(msg.length()).array();
        byte[] data = msg.getBytes();

        byte[] buffer = new byte[prefix.length + data.length];
        System.arraycopy(prefix, 0, buffer, 0, prefix.length);
        System.arraycopy(data, 0, buffer, prefix.length, data.length);

        return buffer;
    }

    /**
     * @param data
     * @return
     */
    public static IMessage deserialize(byte[] data) {
        return null;
    }
}
