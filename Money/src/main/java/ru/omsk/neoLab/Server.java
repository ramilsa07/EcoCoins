package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.selfplay.SelfPlay;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

    static final int PORT = 8081;
    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    static final SelfPlay selfplay = new SelfPlay();
    static final int MAX_PLAYERS = 2;

    enum Command {
        WARNING("warning"),
        STOP_CLIENT_FROM_SERVER("stop client from server"),
        STOP_CLIENT("stop client"),
        STOP_ALL_CLIENTS("stop all clients"),
        STOP_SERVER("stop server"),
        ;

        private final String commandName;

        Command(final String commandName) {
            this.commandName = commandName;
        }

        boolean equalCommand(final String message) {
            return commandName.equals(message);
        }

        static boolean isCommandMessage(final String message) {
            for (final Command command : values()) {
                if (command.equalCommand(message)) {
                    return true;
                }
            }
            return false;
        }
    }

    private final ConcurrentLinkedQueue<Game> serverList = new ConcurrentLinkedQueue<>();

    private class Game extends Thread {

        private final Server server;
        private final Socket socket;
        private final BufferedReader in;
        private final BufferedWriter out;

        private final Board board = Board.GetInstance();
        private final ConcurrentLinkedQueue<Player> players = new ConcurrentLinkedQueue<Player>();

        private Game(final Server server, final Socket socket) throws IOException {
            this.server = server;
            this.socket = socket;
            // если потоку ввода/вывода приведут к генерированию искдючения, оно проброситься дальше
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }

        @Override
        public void run() {
            try {
                do
                    selfplay.createNewPlayer(new Player(in.readLine()));
                while (MAX_PLAYERS != selfplay.getPlayers().size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void generateBoard() {
            IGenerator generator = new Generator();
            board.setBoard(generator.generate(4, 3));
            board.setHeight(4);
            board.setWidth(3);
        }

        private void changeCourse(Player player) {
            players.poll();
            players.add(player);
        }

        public void createNewPlayer(Player player) {
            players.add(player);
            LoggerGame.logNickSelection(player);
        }

        private void downService() {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                    in.close();
                    out.close();
                    server.serverList.remove(this);
                }
            } catch (final IOException ignored) {
            }
        }
    }


    @SuppressWarnings("InfiniteLoopStatement")
    private void startServer() throws IOException {
        System.out.println(String.format("Server started, port: %d", PORT));
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                try {
                    new Game(this, socket).start();
                } catch (final IOException e) {
                    socket.close();
                }
            }
        } catch (final BindException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) throws IOException {
        final Server server = new Server();
        server.startServer();
    }
}
