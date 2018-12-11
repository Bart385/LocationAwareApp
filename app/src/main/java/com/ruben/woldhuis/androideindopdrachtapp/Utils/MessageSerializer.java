package com.ruben.woldhuis.androideindopdrachtapp.Utils;

import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.Messages.DisconnectingMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.FriendRequestMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.LocationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.MessageType;

import java.io.IOException;

//TODO: Implement compression success/failed flag to byte[]
public class MessageSerializer {

    private static byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    /**
     * @param message
     * @return
     */
    public static byte[] serialize(IMessage message) {
        String msg = message.serialize();
        Log.d("MESSAGE_TAG", msg);
        byte[] compressedData = null;

        try {
            compressedData = CompressionUtil.compress(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] prefix = toByteArray(compressedData.length);
        for (byte b : prefix) {
            Log.d("SIZE_TAG", "" + b);

        }
        byte[] buffer = new byte[prefix.length + compressedData.length];
        System.arraycopy(prefix, 0, buffer, 0, prefix.length);
        System.arraycopy(compressedData, 0, buffer, prefix.length, compressedData.length);
        return buffer;
    }

    /**
     * @param serialized
     * @return
     */
    public static IMessage deserialize(String serialized) {
        System.out.println(serialized);
        String[] elements = serialized.split(",");
        String messageType = "";
        for (String item : elements) {
            if (item.contains("messageType")) {
                messageType = item;
                break;
            }
        }
        messageType = messageType.split(":")[1];
        messageType = messageType.substring(1, messageType.length() - 1);
        MessageType type = MessageType.valueOf(messageType);
        switch (type) {
            case FriendRequest_Message:
                return FriendRequestMessage.deserialize(serialized);
            case Disconnecting_Message:
                return DisconnectingMessage.deserialize(serialized);
            case Identification_Message:
                return IdentificationMessage.deserialize(serialized);
            case Location_Message:
                return LocationMessage.deserialize(serialized);
        }
        return null;
    }
}
