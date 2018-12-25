package com.ruben.woldhuis.androideindopdrachtapp.Services.Utils;

import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.AudioMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.ImageMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.ImageUploadedMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.LocationUpdateMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.SignOutMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.TextMessage;

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
        String msg = message.toJson();
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
     * @param json
     * @return
     */
    public static IMessage deserialize(String json) {
        System.out.println(json);
        String[] elements = json.split(",");
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
            case Identification_Message:
                return IdentificationMessage.deserialize(json);
            case Text_Message:
                return TextMessage.deserialize(json);
            case Audio_Message:
                return AudioMessage.deserialize(json);
            case Image_Message:
                return ImageMessage.deserialize(json);
            case SignOut_Message:
                return SignOutMessage.deserialize(json);
            case ImageUploaded_Message:
                return ImageUploadedMessage.deserialize(json);
            case LocationUpdate_Message:
                return LocationUpdateMessage.deserialize(json);
        }
        return null;
    }
}
