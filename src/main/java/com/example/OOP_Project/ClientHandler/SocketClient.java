package com.example.OOP_Project.ClientHandler;

import java.io.*;
import java.net.*;
import java.util.Map;

public class SocketClient {
    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;
    private String nickname = getComputerName();

    private String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else if (env.containsKey("HOSTNAME"))
            return env.get("HOSTNAME");
        else
            return "Unknown";
    }

    public SocketClient(Socket socket) {
        try {
            this.socket = socket;
            this.din = new DataInputStream(socket.getInputStream());
            this.dout = new DataOutputStream(socket.getOutputStream());
            receiveMessageFromServer();
        } catch (IOException e) {
            System.out.println("Error creating client");
            e.printStackTrace();
            close();
        }

    }

    public void close() {
        try {
            if (din != null) {
                din.close();
            }
            if (dout != null) {
                dout.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            // Ignore
        }
    }

    public void sendMessageToServer(String messageToServer) {
        try {
            dout.writeUTF(messageToServer);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to server");
            close();
        }
    }

    public void receiveMessageFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromServer = din.readUTF();
                        if (messageFromServer.equals("-nick")) {
                            sendMessageToServer(nickname);
                        } else {
                            System.out.println(messageFromServer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message from server");
                        close();
                        break;
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        try {
            SocketClient client = new SocketClient(new Socket("127.0.0.1", 8888));
        } catch (Exception e) {
        }
    }
}