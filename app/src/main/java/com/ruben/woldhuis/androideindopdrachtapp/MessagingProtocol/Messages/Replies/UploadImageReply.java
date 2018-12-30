package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;

public class UploadImageReply implements IMessage {
    private MessageType messageType = MessageType.UploadImageReply_Message;
    private String fireBaseToken;
    private String imageUrl;

    public UploadImageReply(String fireBaseToken, String imageUrl) {
        this.fireBaseToken = fireBaseToken;
        this.imageUrl = imageUrl;
    }

    public static UploadImageReply fromJson(String json) {
        return Constants.GSON.fromJson(json, UploadImageReply.class);
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
