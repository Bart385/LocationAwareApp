package com.ruben.woldhuis.androideindopdrachtapp.Messages;

import java.nio.ByteBuffer;
import java.util.ArrayList;

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

    public static String serializeArrayList(ArrayList<Object> value) {
        StringBuilder serialized = new StringBuilder();
        for (int idx = 0; idx < value.size(); idx++)
            serialized.append("index:" + idx + "," + value.get(idx) + ";");
        return serialized.toString();
    }

    public static String serializeArray(Object[] value) {
        StringBuilder serialized = new StringBuilder();
        for (int idx = 0; idx < value.length; idx++)
            serialized.append("index:" + idx + "," + value[idx] + ";");
        return serialized.toString();
    }

    /**
     * @param byteData
     * @return
     */
    public static IMessage deserialize(byte[] byteData) {
        String serialized = new String(byteData);
        String[] items = serialized.split(",");
        String type = items[0];
        StringBuilder data = new StringBuilder();
        for (int i = 1; i < items.length; i++)
            data.append(items[i]);
        switch (MessageType.valueOf(type)) {
            case Identification_Message:
                return IdentificationMessage.deserialize(data.toString());
            case Disconnecting_Message:
                return DisconnectingMessage.deserialize(data.toString());
        }
        return null;
    }
}
