package ru.omsk.neoLab;

import ru.omsk.neoLab.Answer.Answer;
import ru.omsk.neoLab.Answer.AnswerSerializer;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Serializer.BoardDeserializer;
import ru.omsk.neoLab.player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public final class Client extends Player {

    private static final String IP = "127.0.0.1";//"localhost";
    private static final int PORT = Server.PORT;

    private final String ip; // ip адрес клиента
    private final int port; // порт соединения

    private DataInputStream in;
    private DataOutputStream out;

    private Socket socket = null;

    private Board board;
    private String nickname = null;

    private Client(final String ip, final int port) {
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
        pressNickname();
        SimpleBot bot = new SimpleBot();
        while (true) {
            try {
                out.flush();
                board = BoardDeserializer.deserialize(in.readUTF());
                out.writeUTF(AnswerSerializer.serialize(bot.getAnswer(board)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void pressNickname() {
        System.out.print("Press your nick: ");
        Player player = new Player("Garen");
        /*try {
            //out.writeUTF(PlayerSerializer.serialize(player));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (final IOException ignored) {
        }
    }

    private class SimpleBot {

        public Answer getAnswer(Board board) {
            Answer answer = new Answer(board);
            return answer.takeAnswer();
        }

    }

    public static void main(final String[] args) {
        final Client client = new Client(IP, PORT);
        client.startClient();
    }
}
