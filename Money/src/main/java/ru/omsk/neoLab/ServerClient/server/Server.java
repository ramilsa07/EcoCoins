package ru.omsk.neoLab.ServerClient.server;

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

    public static final int PORT = 8080;
    private final ConcurrentLinkedQueue<ServerLobby> serverClient = new ConcurrentLinkedQueue<>();
    private ArrayList<String> arrayList = new ArrayList<>();

    private void startServer() throws IOException {
        int i = 0;
        arrayList.add("SimpleBotRamil");
        arrayList.add("SimpleBotEvgekii");
        System.out.println(String.format("Server started, port: %d", PORT));
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
            int endGame = 0;
            while (endGame == 0) {
                if (!(serverClient.size() == MAX_CLIENTS)) {
                    Socket socket = serverSocket.accept();
                    addServerLobby(socket, arrayList.get(i));
                    System.out.println("Connected host: " + socket);
                    i++;
                } else {
                    //for (int j = 0; j < 2; j++) {
                    Game game = new Game(this, serverClient.element().getSocketClient(), serverClient);
                    game.start();
                    try {
                        game.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //}
                    endGame = 1;
                    downService();
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

    private void downService() {
        try {
            for (ServerLobby serverLobby : this.serverClient) {
                if (!serverLobby.getSocketClient().isClosed()) {
                    serverLobby.getSocketClient().close();
                    serverClient.remove(this);
                }
            }
        } catch (final IOException ignored) {
        }
    }

    private class Game extends Thread {

        private Server server;
        private Socket socket;

        private DataInputStream in;
        private DataOutputStream out;

        private final PlayerService playerService = PlayerService.GetInstance();

        private Player firstPlayer;
        private Player currentPlayer;
        private Board board;

        private ConcurrentLinkedQueue<ServerLobby> serverClientGame = new ConcurrentLinkedQueue<>();
        private int round = 1;


        private Game(final Server server, final Socket socket, final ConcurrentLinkedQueue<ServerLobby> serverClient) {
            this.server = server;
            this.socket = socket;
            this.serverClientGame = serverClient;

            in = serverClient.element().getIn();
            out = serverClient.element().getOut();
        }

        @Override
        public void run() {
            generateBoard();
            firstPlayer = this.serverClientGame.element().getPlayer();
            currentPlayer = this.serverClientGame.element().getPlayer();
            board.changePhase(Phases.RACE_CHOICE);
            System.out.println("Текущий раунд - " + round);
            board.setCurrentPlayer(currentPlayer);
            changeCourse(serverClientGame.element());
            changeCourse(serverClientGame.element());
            while (round < 11) {
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
            System.out.println("Конец игры");
            serverClientGame.clear();
        }

        public synchronized void generateBoard() {
            board = new Board(4, 3);
            board.generate();
        }

        private synchronized void changeCourse(final ServerLobby serverLobby) {
            this.serverClientGame.poll();
            socket = this.serverClientGame.element().getSocketClient();
            in = this.serverClientGame.element().getIn();
            out = this.serverClientGame.element().getOut();
            currentPlayer = this.serverClientGame.element().getPlayer();
            board.setCurrentPlayer(currentPlayer);
            this.serverClientGame.add(serverLobby);
        }

        private synchronized void choiceRace(final Player player) {
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

        private synchronized void goIntoDecline(final Player player) {
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                DeclineAnswer decline = (DeclineAnswer) AnswerDeserialize.deserialize(in.readUTF());
                if (decline.isDecline()) {
                    player.goIntoDecline();
                    log.info("{} уходит в упадок", player.getNickName());
                    changeCourse(this.serverClientGame.element());
                    if (currentPlayer.equals(firstPlayer)) {
                        board.changePhase(Phases.GETTING_COINS);
                        log.info("Переход в сбор монет");
                    } else if (currentPlayer.isDecline()) {
                        board.changePhase(Phases.RACE_CHOICE);
                        log.info("Переход в смену расы, так как я ушел в упадок");
                    } else {
                        board.changePhase(Phases.GO_INTO_DECLINE);
                        log.info("Ушел в упадок передаю ход игроку {}", currentPlayer);
                    }
                } else {
                    board.changePhase(Phases.PICK_UP_TOKENS);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private synchronized void pickUpTokensPhase(final Player currentPlayer) {
            log.info("The phase of picking up tokens has begun");
            for (Cell cell : currentPlayer.getLocationCell()) {
                if (cell.getCountTokens() >= 1) {
                    currentPlayer.collectTokens(cell);
                }
            }
            log.info("{} has {} tokens", currentPlayer.getNickName(), currentPlayer.getCountTokens());
            board.changePhase(Phases.CAPTURE_OF_REGIONS);
        }

        private void captureRegions(final Player currentPlayer) {
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                CellAnswer answer = (CellAnswer) AnswerDeserialize.deserialize(in.readUTF());
                log.info("Territory capture phase has begun");
                for (Point point : answer.getCells()) {
                    playerService.regionCapture(board.getCell(point.x, point.y), currentPlayer);
                }
                board.changePhase(Phases.SHUFFLING_TOKENS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private synchronized void shufflingTokensPhase(final Player player) {
            try {
                out.flush();
                out.writeUTF(BoardSerializer.serialize(board));
                CellAnswer cell = (CellAnswer) AnswerDeserialize.deserialize(in.readUTF());
                for (Point point : cell.getCells()) {
                    player.shufflingTokens(board.getCell(point.x, point.y));
                }
                log.info("{} begins the redistribution of tokens", player.getNickName());
                this.serverClientGame.element().setPlayer(player);
                changeCourse(this.serverClientGame.element());
                if (currentPlayer.equals(firstPlayer)) {
                    board.changePhase(Phases.GETTING_COINS);
                } else if (player.isDecline()) {
                    board.changePhase(Phases.RACE_CHOICE);
                } else {
                    board.changePhase(Phases.GO_INTO_DECLINE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private synchronized void collectCoins(final Player player) {
            log.info("The phase of collecting Coins has begun");
            for (ServerLobby server : this.serverClientGame) {
                server.getPlayer().collectAllCoins();
                log.info("{} has {} coins", server.getPlayer().getNickName(), server.getPlayer().getCountCoin());
            }
            round++;
            System.out.println("Текущий раунд - " + round);
            if (player.isDecline()) {
                board.changePhase(Phases.RACE_CHOICE);
            } else {
                board.changePhase(Phases.GO_INTO_DECLINE);
            }
        }

        public synchronized boolean isEndGame() {
            return round == 11;
        }
    }

    public static void main(final String[] args) throws IOException {
        final Server server = new Server();
        server.startServer();
    }
}
