package com.ruben.woldhuis.androideindopdrachtapp.Services.Utils;

import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadAudioMessageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadImageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.UploadImageRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.LocationUpdateMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.SignOutMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
     * @param data
     * @param compressed
     * @return
     */
    public static IMessage deserialize(byte[] data, boolean compressed) {
        String json;
        try {
            json = new String(data, 0, data.length, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
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
                return IdentificationMessage.fromJson(json);
            case Text_Message:
                return TextMessage.fromJson(json);
            case UploadAudioReply_Message:
                return UploadAudioMessageReply.fromJson(json);
            case UploadImageRequest_Message:
                return UploadImageRequest.fromJson(json);
            case SignOut_Message:
                return SignOutMessage.fromJson(json);
            case UploadImageReply_Message:
                return UploadImageReply.fromJson(json);
            case LocationUpdate_Message:
                return LocationUpdateMessage.fromJson(json);
            case UploadAudioRequest_Message:
                break;
            case FriendsRequest_Message:
                break;
            case FriendRequest_Message:
                break;
            case FriendsReply_Message:
                break;
            case FriendReply_Message:
                break;
        }
        return null;
    }
}
