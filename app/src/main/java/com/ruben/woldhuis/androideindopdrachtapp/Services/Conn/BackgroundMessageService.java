package com.ruben.woldhuis.androideindopdrachtapp.Services.Conn;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.ruben.woldhuis.androideindopdrachtapp.Listeners.TcpErrorListener;
import com.ruben.woldhuis.androideindopdrachtapp.Listeners.TcpMessageReceiverListener;

/**
 * Handles the notifications
 */
public class BackgroundMessageService extends IntentService {
    public static TcpErrorListener errorListener;
    public static TcpMessageReceiverListener messageReceiverListener;
    private static TcpManagerService tcpManagerService;

    public BackgroundMessageService(String name) {
        super(name);
        tcpManagerService = TcpManagerService.getInstance(errorListener, messageReceiverListener);
    }

    public static void setErrorListener(TcpErrorListener tcpErrorListener) {
        tcpManagerService.changeTcpErrorListener(tcpErrorListener);
    }

    public static void setMessageReceiverListener(TcpMessageReceiverListener tcpMessageReceiverListener) {
        tcpManagerService.changeTcpMessageReceiverListener(tcpMessageReceiverListener);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }


}
