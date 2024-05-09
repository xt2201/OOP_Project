package com.example.OOP_Project.ClientHandler;

import java.io.*;
import java.net.*;

public class SocketClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String nickname;

    public SocketClient(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error creating client");
            e.printStackTrace();
            close();
        }

    }

    // private void setNickname() throws IOException {
    // // this.nickname =
    // // while (true) {
    // // System.out.print("Your name: ");
    // // newNickname = reader.readLine();
    // // if (newNickname.indexOf(":") >= 0) {
    // // System.out.println("Name must not contain ':'");
    // // continue;
    // // }
    // // nickname = newNickname;
    // // break;
    // // }
    // }

    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
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
            writer.write(messageToServer);
            writer.newLine();
            writer.flush();
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
                        String messageFromServer = reader.readLine();
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