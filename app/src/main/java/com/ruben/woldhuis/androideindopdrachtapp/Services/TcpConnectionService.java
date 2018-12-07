package com.ruben.woldhuis.androideindopdrachtapp.Services;

import android.content.Context;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.Interfaces.TcpErrorListener;
import com.ruben.woldhuis.androideindopdrachtapp.Interfaces.TcpMessageReceiverListener;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.DisconnectingMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.MessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;

/**
 *
 */
public class TcpConnectionService {
    /**
     *
     */
    private static TcpConnectionService instance;
    /**
     *
     */
    private TcpMessageReceiverListener messageReceiverListener;
    /**
     *
     */
    private TcpErrorListener errorListener;
    /**
     *
     */
    private Context context;
    /**
     *
     */
    private Socket socket;
    /**
     *
     */
    private DataOutputStream toServer;
    /**
     *
     */
    private DataInputStream fromServer;
    /**
     *
     */
    private Thread communicationListeningThread;
    /**
     *
     */
    private boolean running;
    /**
     * @param context
     * @param errorListener
     */
    private TcpConnectionService(Context context, TcpErrorListener errorListener) {
        this.context = context;
        this.errorListener = errorListener;
        try {
            createConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param errorListener
     * @return
     */
    public static TcpConnectionService getInstance(Context context, TcpErrorListener errorListener) {
        if (instance == null)
            instance = new TcpConnectionService(context, errorListener);
        return instance;
    }

    /**
     * @throws IOException
     */
    private void createConnection() throws IOException {
        socket = new Socket(Constants.SERVER_HOSTNAME, Constants.SERVER_PORT);
        toServer = new DataOutputStream(socket.getOutputStream());
        toServer.flush();

        fromServer = new DataInputStream(socket.getInputStream());
        setRunning(true);
        startMessageReceiver();
    }

    /**
     * @param message
     * @param errorListener
     */
    public void writeMessageToServer(IMessage message, TcpErrorListener errorListener) {
        byte[] buffer = MessageSerializer.serialize(message);
        try {
            toServer.write(buffer, 0, buffer.length);
            toServer.flush();
        } catch (IOException e) {
            errorListener.onTcpError(new Error(e));
        }
    }

    /**
     *
     */
    private void startMessageReceiver() {
        communicationListeningThread = new Thread(receiveMessageFromServer());
        communicationListeningThread.start();
    }

    /**
     *
     */
    private Runnable receiveMessageFromServer() {
        return () -> {
            while (running) {
                if (messageReceiverListener == null) {
                    errorListener.onTcpError(new Error("No messageReceiverListener available"));
                } else {
                    IMessage message = getMessage();
                    messageReceiverListener.onMessageReceived(message);
                }
            }
        };
    }

    /**
     * Cuz java's a bitch
     *
     * @param data
     * @return
     */
    private int byteToInt(byte[] data) {
        ByteBuffer intShifter = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).order(ByteOrder.LITTLE_ENDIAN);
        intShifter.clear();
        intShifter.put(data, 0, Integer.SIZE / Byte.SIZE);
        intShifter.flip();
        return intShifter.getInt();
    }

    /**
     * @return
     */
    private IMessage getMessage() {
        byte[] prefix = new byte[4];
        int bytesRead = 0;
        byte[] data;
        while (bytesRead < prefix.length) {
            try {
                bytesRead += fromServer.read(prefix, bytesRead, prefix.length - bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bytesRead = 0;
        data = new byte[byteToInt(prefix)];

        while (bytesRead < data.length) {
            try {
                bytesRead += fromServer.read(data, bytesRead, data.length - bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return MessageSerializer.deserialize(data);
    }

    /**
     *
     */
    public void disconnect() {
        writeMessageToServer(new DisconnectingMessage(Constants.USERNAME, LocalDateTime.now(), "Disconnecting..."), this.errorListener);
        setRunning(false);
    }

    /**
     * @param messageReceiverListener
     */
    public void setMessageReceiverListener(TcpMessageReceiverListener messageReceiverListener) {
        this.messageReceiverListener = messageReceiverListener;
    }

    /**
     * @param running
     */
    private void setRunning(boolean running) {
        this.running = running;
    }
}
