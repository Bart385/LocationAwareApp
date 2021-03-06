package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class FriendReply implements IMessage {
    private MessageType messageType = MessageType.FriendReply_Message;
    private String fireBaseToken;
    private User sender;
    private User friend;
    private boolean approved;

    public FriendReply(String fireBaseToken, User sender, User friend, boolean approved) {
        this.fireBaseToken = fireBaseToken;
        this.sender = sender;
        this.friend = friend;
        this.approved = approved;
    }

    public static FriendReply fromJson(String json) {
        return Constants.GSON.fromJson(json, FriendReply.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    @Override
    public User getSender() {
        return sender;
    }

    public User getFriend() {
        return friend;
    }

    public boolean isApproved() {
        return approved;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
