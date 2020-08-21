package ru.omsk.neoLab.ServerClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.LoggerGame;
import ru.omsk.neoLab.answer.CellAnswer;
import ru.omsk.neoLab.answer.DeclineAnswer;
import ru.omsk.neoLab.answer.RaceAnswer;
import ru.omsk.neoLab.answer.Serialize.AnswerDeserialize;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Serializer.BoardSerializer;
import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private static final int MAX_CLIENTS = 2;

    static final int PORT = 8081;
    private final ConcurrentLinkedQueue<ServerLobby> serverClient = new ConcurrentLinkedQueue<>();
    private Board board;
    private ArrayList<String> arrayList = new ArrayList<>();

    private void startServer() throws IOException {
        int i = 0;
        arrayList.add("SimpleBot1");
        arrayList.add("SimpleBot2");
        System.out.println(String.format("Server started, port: %d", PORT));
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                addServerLobby(socket, arrayList.get(i));
                System.out.println("Connected host: " + socket);
                i++;
                if (serverClient.size() == MAX_CLIENTS) {
                    new Game(this, serverClient.element().getSocketClient()).start();
                }
            }
        } catch (final BindException e) {
            e.printStackTrace();
        }
    }

    public void addServerLobby(Socket socket, String nickName) throws IOException {
        Player player = new Player(nickName);
        ServerLobby serverLobby = new ServerLobby(socket, player);
        serverClient.add(serverLobby);
        log.info("{} joined the game", serverLobby.getPlayer().getNickName());
    }

    private class Game extends Thread {

        private Server server;
        private Socket socket;

        private DataInputStream in;
        private DataOutputStream out;

        private final PlayerService playerService = PlayerService.GetInstance();

        private Player firstPlayer;
        private Player currentPlayer;

        private int round = 1;


        private Game(final Server server, final Socket socket) {
            this.server = server;
            this.socket = socket;

            in = serverClient.element().getIn();
            out = serverClient.element().getOut();
        }

        @Override
        public void run() {
            generateBoard();
            //LoggerGame.logOutputBoard(board);
            firstPlayer = serverClient.element().getPlayer();
            currentPlayer = serverClient.element().getPlayer();
            board.changePhase(Phases.RACE_CHOICE);
            while (round < 11) {
                System.out.println("Текущий раунд - " + round);
                board.setCurrentPlayer(currentPlayer);
                log.info("Player {} starts {} round", currentPlayer.getNickName(), round);
                if (round == 1 || currentPlayer.isDecline()) {
                    board.changePhase(Phases.RACE_CHOICE);
                    while (Phases.RACE_CHOICE.equals(board.getPhase())) {
                        choiceRace(currentPlayer);
                    }
                }
                while (Phases.GO_INTO_DECLINE.equals(board.getPhase())) {
                    goIntoDecline(currentPlayer);
                }
                while (Phases.PICK_UP_TOKENS.equals(board.getPhase())) {
                    pickUpTokensPhase(currentPlayer);
                }
                while (Phases.CAPTURE_OF_REGIONS.equals(board.getPhase())) {
                    captureRegions(currentPlayer);
                }
                while (Phases.SHUFFLING_TOKENS.equals(board.getPhase())) {
                    shufflingTokensPhase(currentPlayer);
                }
                while (Phases.GETTING_COINS.equals(board.getPhase())) {
                    collectCoins(currentPlayer);
                }
            }
            if (isEndGame()) {
                System.out.println("Конец игры");
                downService();
            }
        }

        public void generateBoard() {
            board = new Board(4, 3);
            board.generate();
        }

        private void changeCourse(final ServerLobby serverLobby) {
            serverClient.poll();
            socket = serverClient.element().getSocketClient();
            in = serverClient.element().getIn();
            out = serverClient.element().getOut();
            currentPlayer = serverClient.element().getPlayer();
            serverClient.add(serverLobby);
        }

        private void choiceRace(final Player player) {
            log.info("Race selection phase has begun");
            LoggerGame.logWhatRacesCanIChoose(board.getRacesPool());
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                RaceAnswer race = (RaceAnswer) AnswerDeserialize.deserialize(in.readUTF());
                player.changeRace(race.getRace(), board);
                board.changePhase(Phases.GO_INTO_DECLINE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void goIntoDecline(final Player player) {
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                DeclineAnswer decline = (DeclineAnswer) AnswerDeserialize.deserialize(in.readUTF());
                if (decline.isDecline()) {
                    player.goIntoDecline();
                    log.info("{} turned the race {} into decline", player.getNickName(), player.getRaceDecline().getNameRace());
                    changeCourse(serverClient.element());
                    if (currentPlayer.equals(firstPlayer)) {
                        board.changePhase(Phases.GETTING_COINS);
                    } else {
                        board.changePhase(Phases.GO_INTO_DECLINE);
                    }
                } else {
                    board.changePhase(Phases.PICK_UP_TOKENS);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void pickUpTokensPhase(final Player currentPlayer) {
            log.info("The phase of picking up tokens has begun");
            for (Cell cell : currentPlayer.getLocationCell()) {
                if (cell.getCountTokens() >= 1) {
                    currentPlayer.collectTokens();
                }
            }
            log.info("{} has {} tokens", currentPlayer.getNickName(), currentPlayer.getCountTokens());
            board.changePhase(Phases.CAPTURE_OF_REGIONS);
        }

        private void captureRegions(final Player currentPlayer) {
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                log.info("Territory capture phase has begun");
                CellAnswer answer = (CellAnswer) AnswerDeserialize.deserialize(in.readUTF());
                for (Point point : answer.getCells()) {
                    playerService.regionCapture(board.getCell(point.x, point.y), currentPlayer);
//                    LoggerGame.logCaptureBot(board.getCell(point.x, point.y), currentPlayer);
                }
                board.changePhase(Phases.SHUFFLING_TOKENS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void shufflingTokensPhase(final Player player) {
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                CellAnswer cell = (CellAnswer) AnswerDeserialize.deserialize(in.readUTF());
                for (Point point : cell.getCells()) {
                    player.shufflingTokens(board.getCell(point.x, point.y));
                }
                log.info("{} begins the redistribution of tokens", player.getNickName());
                serverClient.element().setPlayer(player);
                changeCourse(serverClient.element());
                if (currentPlayer.equals(firstPlayer)) {
                    board.changePhase(Phases.GETTING_COINS);
                } else if (player.isDecline()) {
                    board.changePhase(Phases.RACE_CHOICE);
                } else {
                    board.changePhase(Phases.PICK_UP_TOKENS);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void collectCoins(final Player player) {
            log.info("The phase of collecting Coins has begun");
            for (ServerLobby server : serverClient) {
                server.getPlayer().collectAllCoins();
                log.info("{} has {} coins", server.getPlayer().getNickName(), server.getPlayer().getCountCoin());
            }
            round++;
            if (player.isDecline()) {
                board.changePhase(Phases.RACE_CHOICE);
            } else {
                board.changePhase(Phases.GO_INTO_DECLINE);
            }
        }

        public boolean isEndGame() {
            return round == 10;
        }

        private void downService() {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                    server.serverClient.remove(this);
                }
            } catch (final IOException ignored) {
            }
        }
    }

    public static void main(final String[] args) throws IOException {
        final Server server = new Server();
        server.startServer();
    }
}
