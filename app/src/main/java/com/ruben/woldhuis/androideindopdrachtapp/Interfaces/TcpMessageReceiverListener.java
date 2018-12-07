package com.ruben.woldhuis.androideindopdrachtapp.Interfaces;

import com.ruben.woldhuis.androideindopdrachtapp.Messages.IMessage;

public interface TcpMessageReceiverListener {
    void onMessageReceived(IMessage message);
}
