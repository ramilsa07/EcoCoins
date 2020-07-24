package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Serializer.BoardDeserializer;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;
import ru.omsk.neoLab.player.Serializer.PlayerSerializer;
import ru.omsk.neoLab.race.ARace;
import ru.omsk.neoLab.race.Serializer.RaceSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public final class Client {

    private static final String IP = "127.0.0.1";//"localhost";
    private static final int PORT = Server.PORT;

    private final String ip; // ip адрес клиента
    private final int port; // порт соединения

    private DataInputStream in;
    private DataOutputStream out;

    private Socket socket = null;
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
        new SimpleBot().start();
    }

    private void pressNickname() {
        System.out.print("Press your nick: ");
        Player player = new Player("Garen");
        try {
            out.writeUTF(PlayerSerializer.serialize(player));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (final IOException ignored) {
        }
    }

    private class SimpleBot extends Thread {

        private final Random random = new Random();
        private final PlayerService service = PlayerService.GetInstance();

        public ARace getRandomRace(Board board) {
            return PlayerService.getRacesPool().get(random.nextInt(PlayerService.getRacesPool().size()));
        }

        public Cell getRandomRegionToCapture(Board board) {
            //return (Cell) cells[random.nextInt(cells.length)];
            return new Cell();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    out.writeUTF(RaceSerializer.serialize(getRandomRace(BoardDeserializer.deserialize(in.readUTF()))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(final String[] args) {
        final Client client = new Client(IP, PORT);
        client.startClient();
    }
}
