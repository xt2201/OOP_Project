package com.example.OOP_Project.ClientHandler;

import java.io.*;
import java.net.*;

public class SocketClient implements Runnable {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Socket socket;
    private String ip;
    private int port;
    private DataInputStream din;
    private DataOutputStream dout;
    private String nickname;

    @Override
    public void run() {
        try {
            socket = new Socket(ip, port);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            setNickname();
            Thread receive_thread = new Thread(new Receiver());
            receive_thread.start();
            Thread write_thread = new Thread(new Writer());
            write_thread.start();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public SocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private void setNickname() throws IOException {
        String newNickname;
        while (true) {
            System.out.print("Your name: ");
            newNickname = reader.readLine();
            if (newNickname.indexOf(":") >= 0) {
                System.out.println("Name must not contain ':'");
                continue;
            }
            nickname = newNickname;
            break;
        }
    }

    public void sendMessage(String msg) throws IOException {
        dout.writeUTF(msg);
        dout.flush();
    }

    public String receiveMessage() throws IOException {
        String msg = din.readUTF();
        return msg;
    }

    public void close() {
        try {
            reader.close();
            dout.close();
            din.close();
            socket.close();
        } catch (IOException e) {
            // Ignore
        }
    }

    class Receiver implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    String message = receiveMessage();
                    if (message.equals("-nick")) {
                        sendMessage(nickname);
                    } else {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println(e);
                    break;
                }
            }
        }
    }

    class Writer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String client_input = reader.readLine();
                    String message = nickname + ": " + client_input;
                    sendMessage(message);
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 8888);
        client.run();
    }
}