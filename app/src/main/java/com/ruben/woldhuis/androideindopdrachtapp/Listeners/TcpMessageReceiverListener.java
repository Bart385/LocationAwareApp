package com.ruben.woldhuis.androideindopdrachtapp.Listeners;

import com.ruben.woldhuis.androideindopdrachtapp.Messages.IMessage;

public interface TcpMessageReceiverListener {
    void onMessageReceived(IMessage message);
}
