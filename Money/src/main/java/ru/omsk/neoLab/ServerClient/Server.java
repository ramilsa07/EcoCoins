package ru.omsk.neoLab.ServerClient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.LoggerGame;
import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.answer.Serialize.AnswerDeserialize;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Serializer.BoardSerializer;
import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(LoggerGame.class);
    private static final int MAX_CLIENTS = 2;

    static final int PORT = 8081;
    private final ConcurrentLinkedQueue<ServerLobby> serverClient = new ConcurrentLinkedQueue<>();
    private Board board;
    private ArrayList<String> arrayList = new ArrayList<>();
    private Player currentPlayer;

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
                    try {
                        new Game(this).start();
                    } catch (final IOException e) {
                        socket.close();
                    }
                }
            }
        } catch (final BindException e) {
            e.printStackTrace();
        }
    }

    public void addServerLobby(Socket socket, String nickName) {
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

        public HashSet<Cell> possibleCellsCapture = new HashSet<>();
        private int round = 1;


        private Game(final Server server) throws IOException {
            this.server = server;
            this.socket = serverClient.element().getSocketClient();

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }

        @Override
        public void run() {
            generateBoard();
            Player firstPlayer = serverClient.element().getPlayer();
            currentPlayer = serverClient.element().getPlayer();
            board.setCurrentPlayer(currentPlayer);
            while (round < 11) {
                this.socket = serverClient.element().getSocketClient();
                log.info("Player {} starts {} round", currentPlayer.getNickName(), round);
                if (currentPlayer.isDecline() || round == 1) {
                    log.info("Началась фаза выбора расы");
                    board.changePhase(Phases.RACE_CHOICE);
                    while (Phases.RACE_CHOICE.equals(board.getPhase())) {
                        try {
                            out.writeUTF(BoardSerializer.serialize(board));
                            Answer race = AnswerDeserialize.deserialize(in.readUTF());
                            currentPlayer.changeRace(race.getRace());
                            log.info("{} chose a race of {}", currentPlayer.getNickName(), currentPlayer.getRace().getNameRace());
                            board.changePhase(Phases.CAPTURE_OF_REGIONS);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (Phases.PICK_UP_TOKENS.equals(board.getPhase())) {
                    log.info("Началась фаза взятия жетонов в руки");
                    while (Phases.PICK_UP_TOKENS.equals(board.getPhase())) {
                        for (Cell cell : currentPlayer.getLocationCell()) {
                            if (cell.getCountTokens() >= 1) {
                                currentPlayer.collectTokens();
                            }
                        }
                        log.info("{} has {} tokens", currentPlayer.getNickName(), currentPlayer.getCountTokens());
                        if (possibleCellsCapture.isEmpty()) {
                            board.changePhase(Phases.GO_INTO_DECLINE);
                        } else {
                            board.changePhase(Phases.CAPTURE_OF_REGIONS);
                        }
                    }
                }
                if (Phases.GO_INTO_DECLINE.equals(board.getPhase())) {
                    if (round != 1) {
                        try {
                            out.flush();
                            out.writeUTF(BoardSerializer.serialize(board));
                            Answer decline = AnswerDeserialize.deserialize(in.readUTF());
                            if (decline.isDecline()) {
                                currentPlayer.goIntoDecline();
                                log.info("{} turned the race {} into decline", currentPlayer.getNickName(), currentPlayer.getRaceDecline().getNameRace());
                                changeCourse(serverClient.element());
                            }
                            log.info("{} turned the race {} into decline", currentPlayer.getNickName(), currentPlayer.getRaceDecline().getNameRace());
                            changeCourse(serverClient.element());
                            currentPlayer = serverClient.element().getPlayer();
                            if (currentPlayer.equals(firstPlayer)) {
                                board.changePhase(Phases.GETTING_COINS);
                            } else if (currentPlayer.isDecline()) {
                                board.changePhase(Phases.RACE_CHOICE);
                            } else {
                                board.changePhase(Phases.PICK_UP_TOKENS);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                log.info("Началась фаза захвата территории");
                while (Phases.CAPTURE_OF_REGIONS.equals(board.getPhase())) {
                    log.info("{} has {} tokens", currentPlayer.getNickName(), currentPlayer.getCountTokens());
                    try {
                        out.flush();
                        out.writeUTF(BoardSerializer.serialize(board));
                        Answer answer = AnswerDeserialize.deserialize(in.readUTF());
//                        for (Cell cell : answer.getCells()) {
//                            playerService.regionCapture(cell, currentPlayer);
//                            LoggerGame.logCaptureBot(cell, currentPlayer);
//                        }
                        board.changePhase(Phases.SHUFFLING_TOKENS);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                while (Phases.SHUFFLING_TOKENS.equals(board.getPhase())) {
                    try {
                        out.flush();
                        out.writeUTF(BoardSerializer.serialize(board));
                        Answer cell = AnswerDeserialize.deserialize(in.readUTF());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentPlayer.shufflingTokens(new Cell());
                    log.info("{} begins the redistribution of tokens", currentPlayer.getNickName());
                    serverClient.element().setPlayer(currentPlayer);
                    changeCourse(serverClient.element());
                    currentPlayer = serverClient.element().getPlayer();
                    if (currentPlayer.equals(firstPlayer)) {
                        board.changePhase(Phases.GETTING_COINS);
                    } else if (currentPlayer.isDecline()) {
                        board.changePhase(Phases.RACE_CHOICE);
                    } else {
                        board.changePhase(Phases.PICK_UP_TOKENS);
                    }
                }
                while (Phases.GETTING_COINS.equals(board.getPhase())) {
                    log.info("Началась фаза cбора Монет");
                    for (ServerLobby server : serverClient) {
                        server.getPlayer().collectAllCoins();
                        log.info("{} has {} coins", server.getPlayer().getNickName(), server.getPlayer().getCountCoin());
                    }
                    round++;
                    if (currentPlayer.isDecline()) {
                        board.changePhase(Phases.RACE_CHOICE);
                    } else {
                        board.changePhase(Phases.PICK_UP_TOKENS);
                    }

                }
                if (isEndGame()) {
                    downService();
                    break;
                }
            }
        }

        public void generateBoard() {
            board = new Board(4, 3);
            board.generate();
        }

        private void changeCourse(final ServerLobby serverLobby) {
            serverClient.poll();
            serverClient.add(serverLobby);
            this.socket = serverClient.element().getSocketClient();
            board.setCurrentPlayer(serverLobby.getPlayer());
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
