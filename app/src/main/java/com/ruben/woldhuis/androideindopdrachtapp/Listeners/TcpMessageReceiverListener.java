package com.ruben.woldhuis.androideindopdrachtapp.Listeners;


import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;

public interface TcpMessageReceiverListener {
    void onMessageReceived(IMessage message);
}
