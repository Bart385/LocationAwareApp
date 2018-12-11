package com.ruben.woldhuis.androideindopdrachtapp.Services.Conn;

import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.Interfaces.TcpErrorListener;
import com.ruben.woldhuis.androideindopdrachtapp.Interfaces.TcpMessageReceiverListener;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.DisconnectingMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Utils.CompressionUtil;
import com.ruben.woldhuis.androideindopdrachtapp.Utils.MessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.zip.DataFormatException;

public class TcpManagerService {
    /**
     *
     */
    private volatile static TcpManagerService instance;
    /**
     *
     */
    private TcpConnectionService connectionService;

    /**
     * @param tcpErrorListener
     * @param messageReceiverListener
     */
    private TcpManagerService(TcpErrorListener tcpErrorListener, TcpMessageReceiverListener messageReceiverListener) {
        this.connectionService = new TcpConnectionService(tcpErrorListener, messageReceiverListener);
        new Thread(this.connectionService.createConnection(this.connectionService)).start();
    }

    /**
     * @param tcpErrorListener
     * @param messageReceiverListener
     * @return
     */
    public static TcpManagerService getInstance(TcpErrorListener tcpErrorListener, TcpMessageReceiverListener messageReceiverListener) {
        if (instance == null)
            instance = new TcpManagerService(tcpErrorListener, messageReceiverListener);
        return instance;
    }

    /**
     * @param message
     */
    public void submitMessage(IMessage message) {
        CompletableFuture.runAsync(this.connectionService.writeMessageToServer(message));
    }

    /**
     * @param tcpErrorListener
     */
    public void changeTcpErrorListener(TcpErrorListener tcpErrorListener) {
        this.connectionService.setErrorListener(tcpErrorListener);
    }

    /**
     * @param tcpMessageReceiverListener
     */
    public void changeTcpMessageReceiverListener(TcpMessageReceiverListener tcpMessageReceiverListener) {
        this.connectionService.setMessageReceiverListener(tcpMessageReceiverListener);
    }

    private class TcpConnectionService {
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
        private TcpConnectionService(TcpErrorListener errorListener, TcpMessageReceiverListener messageReceiverListener) {
            this.errorListener = errorListener;
            this.messageReceiverListener = messageReceiverListener;
        }


        /**
         * Initializes the connection with the server and starts listening to messages from the server.
         *
         * @throws IOException thrown when the socket disconnects during the DataOutputStream flushing.
         */
        private Runnable createConnection(TcpConnectionService tcpConnectionService) {
            return () -> {
                try {
                    tcpConnectionService.socket = new Socket(Constants.SERVER_HOSTNAME, Constants.SERVER_PORT);
                    tcpConnectionService.toServer = new DataOutputStream(socket.getOutputStream());
                    tcpConnectionService.toServer.flush();

                    tcpConnectionService.fromServer = new DataInputStream(socket.getInputStream());
                    tcpConnectionService.setRunning(true);
                    tcpConnectionService.startMessageReceiver();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }

        /**
         * Method for sending messages to the server.
         *
         * @param message The message that will be send.
         */
        private Runnable writeMessageToServer(IMessage message) {
            return () -> {
                byte[] buffer = MessageSerializer.serialize(message);
                try {
                    toServer.write(buffer, 0, buffer.length);
                    toServer.flush();
                } catch (IOException e) {
                    errorListener.onTcpError(new Error(e));
                }

            };
        }

        /**
         * @param errorListener
         */
        private void setErrorListener(TcpErrorListener errorListener) {
            this.errorListener = errorListener;
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
                    Log.d("RECEIVING_TAG", "in receiveMessage");
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
         * @return
         */
        private IMessage getMessage() {
            byte[] prefix = new byte[4];
            int bytesRead = 0;

            while (bytesRead < prefix.length) {
                try {
                    bytesRead += fromServer.read(prefix, bytesRead, prefix.length - bytesRead);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Got prefix");
            bytesRead = 0;
            byte[] compressedData = new byte[byteArrayToInt(prefix)];
            while (bytesRead < compressedData.length) {
                try {
                    bytesRead += fromServer.read(compressedData, bytesRead, compressedData.length - bytesRead);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Got data");
            String data = null;

            try {
                data = CompressionUtil.decompress(compressedData);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DataFormatException e) {
                e.printStackTrace();
            }

            return MessageSerializer.deserialize(data);
        }

        /**
         * safely disconnects the TcpConnectionService from the server
         */
        private void disconnect() {
            writeMessageToServer(new DisconnectingMessage(Constants.USERNAME, LocalDateTime.now(), "Disconnecting..."));
            setRunning(false);
        }

        /**
         * Setter
         *
         * @param messageReceiverListener sets the TcpMessageReceiverListener
         */
        private void setMessageReceiverListener(TcpMessageReceiverListener messageReceiverListener) {
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
        private Thread getCommunicationListeningThread() {
            return communicationListeningThread;
        }
    }
}
