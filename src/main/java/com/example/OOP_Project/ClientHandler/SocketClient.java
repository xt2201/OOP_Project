package com.example.OOP_Project.ClientHandler;

import java.io.*;
import java.net.*;
import java.util.Map;

import com.example.OOP_Project.Controller.Visualization.TodayController;

public class SocketClient {
    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;
    private String nickname = getComputerName();
    private boolean refresh = false;

    public void setRefreshStatus(boolean status) {
        this.refresh = status;
    }

    public boolean getRefreshStatus() {
        return this.refresh;
    }

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

    private void receiveMessageFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromServer = din.readUTF();
                        if (messageFromServer.equals("-nick")) {
                            sendMessageToServer(nickname);
                        } else if (messageFromServer.indexOf("-result") == 0) {
                            int num_results = Integer
                                    .parseInt(messageFromServer.substring(messageFromServer.indexOf(" ") + 1));
                            System.out.println("Found " + num_results);
                        } else if (messageFromServer.indexOf("-news") == 0) {
                            int s1 = messageFromServer.indexOf(" ");
                            int s2 = messageFromServer.indexOf(" ", s1 + 1);
                            int pos = Integer.parseInt(messageFromServer.substring(s1 + 1, s2));
                            String jsonString = messageFromServer.substring(s2 + 1);
                            TodayController.addSearchResult(pos, jsonString);
                        } else if (messageFromServer.indexOf("-refresh") == 0) {
                            setRefreshStatus(true);
                            System.out.println("Refresh set: " + refresh);
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