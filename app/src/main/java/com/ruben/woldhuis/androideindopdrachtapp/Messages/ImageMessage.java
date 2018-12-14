package com.ruben.woldhuis.androideindopdrachtapp.Messages;

import android.graphics.Bitmap;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Utils.ImageUtil;

import java.io.ByteArrayOutputStream;
import java.util.Date;


public class ImageMessage implements IMessage {
    private MessageType messageType = MessageType.Image_Message;
    private String sender;
    private String extension;
    private Date timeSend;
    private String base64EncodedString;

    public ImageMessage(String sender, String extension, Date timeSend, Bitmap image) {
        this.sender = sender;
        this.extension = extension;
        this.timeSend = timeSend;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        this.base64EncodedString = ImageUtil.toBaste64String(stream.toByteArray());
    }

    public static ImageMessage deserialize(String serialized) {
        return Constants.GSON.fromJson(serialized, ImageMessage.class);
    }

    public String getSender() {
        return sender;
    }

    public String getExtension() {
        return extension;
    }

    public Date getTimeSend() {
        return timeSend;
    }

    public String getBase64EncodedString() {
        return base64EncodedString;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String serialize() {
        return Constants.GSON.toJson(this, ImageMessage.class);
    }
}
