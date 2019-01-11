package com.ruben.woldhuis.androideindopdrachtapp.Services.Conn;

import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.Listeners.TcpErrorListener;
import com.ruben.woldhuis.androideindopdrachtapp.Listeners.TcpMessageReceiverListener;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Utils.MessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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

     */
    private TcpManagerService() {
        this.connectionService = new TcpConnectionService();
        CompletableFuture.runAsync(this.connectionService.createConnection());
    }


    /**
     * @return
     */
    public static TcpManagerService getInstance() {
        if (instance == null)
            instance = new TcpManagerService();
        return instance;
    }

    /**
     * @param message
     */
    public CompletableFuture submitMessage(IMessage message) {
        return CompletableFuture.runAsync(this.connectionService.writeMessageToServer(message));
    }

    /**
     * @param tcpErrorListener
     */
    public void subscribeToErrorEvents(TcpErrorListener tcpErrorListener) {
        this.connectionService.addErrorListener(tcpErrorListener);
    }

    /**
     * @param tcpErrorListener
     */
    public void unsubscribeFromErrorEvents(TcpErrorListener tcpErrorListener) {
        this.connectionService.removeErrorListener(tcpErrorListener);
    }

    /**
     * @param tcpMessageReceiverListener
     */
    public void subscribeToMessageEvents(TcpMessageReceiverListener tcpMessageReceiverListener) {
        this.connectionService.addMessageReceiverListener(tcpMessageReceiverListener);
    }

    /**
     * @param tcpMessageReceiverListener
     */
    public void unsubscribeFromMessageEvents(TcpMessageReceiverListener tcpMessageReceiverListener) {
        this.connectionService.removeMessageReceiverListener(tcpMessageReceiverListener);
    }

    private class TcpConnectionService {
        /**
         * TcpMessageReceiverListener variable has a callback to send the decoded message back to the app.
         */
        private ArrayList<TcpMessageReceiverListener> messageReceiverListeners;
        /**
         * TcpErrorListener variable has a callback that get's called when something breaks in this class.
         */
        private ArrayList<TcpErrorListener> errorListeners;
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
         */
        private TcpConnectionService() {
            messageReceiverListeners = new ArrayList<>();
            errorListeners = new ArrayList<>();
        }


        /**
         * Initializes the connection with the server and starts listening to messages from the server.
         *
         * @throws IOException thrown when the socket disconnects during the DataOutputStream flushing.
         */
        private Runnable createConnection() {
            return () -> {
                try {
                    socket = new Socket(Constants.SERVER_HOSTNAME, Constants.SERVER_PORT);
                    toServer = new DataOutputStream(socket.getOutputStream());
                    toServer.flush();
                    fromServer = new DataInputStream(socket.getInputStream());
                    setRunning(true);
                    startMessageReceiver();
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
                    Log.d("SENDING_TAG", "writeMessageToServer: " + message.toJson());
                    toServer.write(buffer, 0, buffer.length);
                    toServer.flush();
                } catch (IOException e) {
                    errorListeners.forEach(listener -> listener.onTcpError(new Error(e)));
                }

            };
        }

        /**
         * @param errorListener
         */
        private void addErrorListener(TcpErrorListener errorListener) {
            this.errorListeners.add(errorListener);
        }

        /**
         * @param errorListener
         */
        private void removeErrorListener(TcpErrorListener errorListener) {
            this.errorListeners.remove(errorListener);
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
                    IMessage message = getMessage();
                    Log.d("RECEIVING_TAG", "in receiveMessage");
                    Log.d("RECEIVING_TAG", "GOT: " + message.toJson());

                    if (messageReceiverListeners.isEmpty()) {
                        errorListeners.forEach(listener -> listener.onTcpError(new Error("No messageReceiverListener available")));
                    } else {
                        if (message == null)
                            errorListeners.forEach(listener -> listener.onTcpError(new Error("Couldn't decode a message from the server.")));
                        else
                            messageReceiverListeners.forEach(listener -> listener.onMessageReceived(message));
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
            ByteBuffer wrapped = ByteBuffer.wrap(data);
            return wrapped.getInt();
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
                    if (bytesRead < 0)
                        return null;
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
                    if (bytesRead < 0)
                        return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Got data");
            String data = null;
/*
            try {
                data = CompressionUtil.decompress(compressedData);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
*/
            String ser = "";
            try {
                ser = new String(compressedData, 0, compressedData.length, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return MessageSerializer.deserialize(compressedData, false);
        }

        /**
         * safely disconnects the TcpConnectionService from the server
         */
        private void disconnect() {
            setRunning(false);
        }

        /**
         * Setter
         *
         * @param messageReceiverListener sets the TcpMessageReceiverListener
         */
        private void addMessageReceiverListener(TcpMessageReceiverListener messageReceiverListener) {
            this.messageReceiverListeners.add(messageReceiverListener);
        }

        /**
         * @param messageReceiverListener
         */
        private void removeMessageReceiverListener(TcpMessageReceiverListener messageReceiverListener) {
            this.messageReceiverListeners.remove(messageReceiverListener);
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
