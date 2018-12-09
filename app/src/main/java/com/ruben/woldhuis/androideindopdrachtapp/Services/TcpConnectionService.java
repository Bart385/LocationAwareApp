package com.ruben.woldhuis.androideindopdrachtapp.Services;

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
     * Static instance for singleton pattern
     */
    private static TcpConnectionService instance;
    /**
     * TcpMessageReceiverListener variable has a callback to send the decoded message back to the app.
     */
    private TcpMessageReceiverListener messageReceiverListener;
    /**
     * TcpErrorListener variable has a callback that get's called when something breaks in this class.
     */
    private TcpErrorListener errorListener;

    /**
     * TcpSocket
     */
    private Socket socket;
    /**
     * Sends data to the server
     */
    private DataOutputStream toServer;
    /**
     * Receives data from the server.
     */
    private DataInputStream fromServer;
    /**
     * Thread used for listening on the background for incoming messages
     */
    private Thread communicationListeningThread;
    /**
     * boolean variable indicating whether this class is running.
     */
    private boolean running;

    /**
     * TcpConnectionService constructor
     * Creates the connection when it's called
     *
     * @param errorListener sets the TcpErrorListener
     */
    private TcpConnectionService(TcpErrorListener errorListener) {
        this.errorListener = errorListener;
        try {
            createConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Singleton getInstance method invoking the constructor once.
     *
     * @param errorListener sets the TcpErrorListener
     * @return a single instance of the TcpConnectionService class.
     */
    public static TcpConnectionService getInstance(TcpErrorListener errorListener) {
        if (instance == null)
            instance = new TcpConnectionService(errorListener);
        return instance;
    }

    /**
     * Initializes the connection with the server and starts listening to messages from the server.
     *
     * @throws IOException thrown when the socket disconnects during the DataOutputStream flushing.
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
     * Method for sending messages to the server.
     *
     * @param message The message that will be send.
     */
    public void writeMessageToServer(IMessage message) {
        byte[] buffer = MessageSerializer.serialize(message);
        try {
            toServer.write(buffer, 0, buffer.length);
            toServer.flush();
        } catch (IOException e) {
            errorListener.onTcpError(new Error(e));
        }
    }

    /**
     * Starts the communicationListeningThread for listening to the server for messages.
     */
    private void startMessageReceiver() {
        communicationListeningThread = new Thread(receiveMessageFromServer());
        communicationListeningThread.start();
    }

    /**
     * @return runnable listening object
     */
    private Runnable receiveMessageFromServer() {
        return () -> {
            while (running) {
                if (messageReceiverListener == null) {
                    errorListener.onTcpError(new Error("No messageReceiverListener available"));
                } else {
                    IMessage message = getMessage();
                    if (message == null)
                        errorListener.onTcpError(new Error("Couldn't decode a message from the server."));
                    else messageReceiverListener.onMessageReceived(message);
                }
            }
        };
    }

    /**
     * Cuz java's a bitch
     *
     * @param data A byte array of 4 bytes containing the length of the prefix.
     * @return an integer value converted from the byte array
     */
    private int byteArrayToInt(byte[] data) {
        ByteBuffer intShifter = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).order(ByteOrder.LITTLE_ENDIAN);
        intShifter.clear();
        intShifter.put(data, 0, Integer.SIZE / Byte.SIZE);
        intShifter.flip();
        return intShifter.getInt();
    }

    /**
     * Receives a message
     *
     * @return a decoded IMessage
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
        data = new byte[byteArrayToInt(prefix)];

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
     * safely disconnects the TcpConnectionService from the server
     */
    public void disconnect() {
        writeMessageToServer(new DisconnectingMessage(Constants.USERNAME, LocalDateTime.now(), "Disconnecting..."));
        setRunning(false);
    }

    /**
     * Setter
     *
     * @param messageReceiverListener sets the TcpMessageReceiverListener
     */
    public void setMessageReceiverListener(TcpMessageReceiverListener messageReceiverListener) {
        this.messageReceiverListener = messageReceiverListener;
    }

    /**
     * setter
     *
     * @param running sets the running variable
     */
    private void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * getter
     *
     * @return CommunicationListeningThread
     */
    public Thread getCommunicationListeningThread() {
        return communicationListeningThread;
    }
}
