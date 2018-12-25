package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;

public class ImageUploadedMessage implements IMessage {
    private MessageType messageType = MessageType.ImageUploaded_Message;
    private String fireBaseToken;
    private String imageUrl;

    public ImageUploadedMessage(String fireBaseToken, String imageUrl) {
        this.fireBaseToken = fireBaseToken;
        this.imageUrl = imageUrl;
    }

    public static ImageUploadedMessage deserialize(String json) {
        return Constants.GSON.fromJson(json, ImageUploadedMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
