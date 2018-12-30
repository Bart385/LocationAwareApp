package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

import java.util.ArrayList;

public class FriendsReply implements IMessage {
    private MessageType messageType = MessageType.FriendsReply_Message;
    private String fireBaseToken;
    private ArrayList<User> friends;

    public FriendsReply(String fireBaseToken, ArrayList<User> friends) {
        this.fireBaseToken = fireBaseToken;
        this.friends = friends;
    }

    public static FriendsReply fromJson(String json) {
        return Constants.GSON.fromJson(json, FriendsReply.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
