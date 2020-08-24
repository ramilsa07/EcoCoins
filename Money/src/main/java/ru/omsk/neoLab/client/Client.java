package ru.omsk.neoLab.client;

import ru.omsk.neoLab.answer.Serialize.AnswerSerialize;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Serializer.BoardDeserializer;
import ru.omsk.neoLab.client.Bot.SimpleBotGreat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static final String IP = "127.0.0.1";//"127.0.0.1";//135.181.85.225
    private static final int PORT = 8080;

    private final String ip; // ip адрес клиента
    private final int port; // порт соединения

    private DataInputStream in;
    private DataOutputStream out;

    private Socket socket = null;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private void startClient() {
        try {
            socket = new Socket(this.ip, this.port);
        } catch (final IOException e) {
            System.err.println("Socket failed");
            return;
        }
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Client started, ip: %s, port: %d", ip, port));
        SimpleBotGreat simpleBotGreat = new SimpleBotGreat();
        while (isDisconnect()) {
            try {
                out.flush();
                Board board = BoardDeserializer.deserialize(in.readUTF());
                out.writeUTF(AnswerSerialize.serialize(simpleBotGreat.getAnswer(board)));
            } catch (Exception e) {
                downService();
            }
        }
    }

    private boolean isDisconnect(){
        return !socket.isClosed();
    }

    private void downService() {
        try {
            if (isDisconnect()) {
                socket.close();
            }
        } catch (final IOException ignored) {
        }
    }


    public static void main(final String[] args) {
        final Client client = new Client(IP, PORT);
        client.startClient();
    }
}