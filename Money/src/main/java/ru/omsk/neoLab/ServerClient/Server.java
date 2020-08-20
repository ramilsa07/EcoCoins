package ru.omsk.neoLab.ServerClient;

import ru.omsk.neoLab.LoggerGame;
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
        LoggerGame.logNickSelection(serverLobby);
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
                LoggerGame.logPlayerRoundStart(currentPlayer, round);
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
                    LoggerGame.logStartPhaseCaptureOfRegions();
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
            LoggerGame.logStartPhaseRaceChoice();
            LoggerGame.logWhatRacesCanIChoose(board.getRacesPool());
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                ResponseRace race = (ResponseRace) AnswerDeserialize.deserialize(in.readUTF());
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
                ResponseDecline decline = (ResponseDecline) AnswerDeserialize.deserialize(in.readUTF());
                if (decline.isDecline()) {
                    player.goIntoDecline();
                    LoggerGame.logRaceInDecline(player);
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
            LoggerGame.logStartPhasePickUpTokens();
            for (Cell cell : currentPlayer.getLocationCell()) {
                if (cell.getCountTokens() >= 1) {
                    currentPlayer.collectTokens();
                }
            }
            LoggerGame.logGetTokens(currentPlayer);
            board.changePhase(Phases.CAPTURE_OF_REGIONS);
        }

        private void captureRegions(final Player currentPlayer) {
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                ResponseCell answer = (ResponseCell) AnswerDeserialize.deserialize(in.readUTF());
                for (Point point : answer.getCells()) {
                    playerService.regionCapture(board.getCell(point.x, point.y), currentPlayer);
                    LoggerGame.logCaptureBot(board.getCell(point.x, point.y), currentPlayer);
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
                ResponseCell cell = (ResponseCell) AnswerDeserialize.deserialize(in.readUTF());
                for (Point point : cell.getCells()) {
                    player.shufflingTokens(board.getCell(point.x, point.y));
                }
                LoggerGame.logRedistributionOfTokens(player);
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
            LoggerGame.logStartPhaseGetCoins();
            for (ServerLobby server : serverClient) {
                server.getPlayer().collectAllCoins();
                LoggerGame.logGetCoins(server.getPlayer());
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
