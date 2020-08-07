package ru.omsk.neoLab.ServerClient;

import ru.omsk.neoLab.Answer.ResponseCell;
import ru.omsk.neoLab.Answer.ResponseRace;
import ru.omsk.neoLab.Answer.Serialize.AnswerDeserialize;
import ru.omsk.neoLab.LoggerGame;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Serializer.BoardSerializer;
import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.Player.Player;
import ru.omsk.neoLab.Player.PlayerService;

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
                addServerLobby(socket,arrayList.get(i));
                System.out.println("Connected host: " +  socket);
                i++;
                if(serverClient.size() == MAX_CLIENTS) {
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
        ServerLobby serverLobby = new ServerLobby(socket,player);
        serverClient.add(serverLobby);
        LoggerGame.logNickSelection(serverLobby);
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
            LoggerGame.logOutputBoard(board);
            Player firstPlayer = serverClient.element().getPlayer();
            Player currentPlayer = serverClient.element().getPlayer();
            while (round < 11) {
                this.socket = serverClient.element().getSocketClient();
                LoggerGame.logPlayerRoundStart(currentPlayer, round);
                if (currentPlayer.isDecline() || round == 1) {
                    LoggerGame.logStartPhaseRaceChoice();
                    board.changePhase(Phases.RACE_CHOICE);
                    while (Phases.RACE_CHOICE.equals(board.getPhase())) {
                        LoggerGame.logWhatRacesCanIChoose(PlayerService.getRacesPool());
                        try {
                            out.writeUTF(BoardSerializer.serialize(board));
                            ResponseRace race = (ResponseRace) AnswerDeserialize.deserialize(in.readUTF());
                            currentPlayer.changeRace(race.getRace());
                            LoggerGame.logChooseRaceTrue(currentPlayer);
                            board.changePhase(Phases.CAPTURE_OF_REGIONS);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (Phases.PICK_UP_TOKENS.equals(board.getPhase())) {
                    LoggerGame.logStartPhasePickUpTokens();
                    while (Phases.PICK_UP_TOKENS.equals(board.getPhase())) {
                        for (Cell cell : currentPlayer.getLocationCell()) {
                            if (cell.getCountTokens() >= 1) {
                                currentPlayer.collectTokens();
                            }
                        }
                        LoggerGame.logGetTokens(currentPlayer);
                        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, currentPlayer);
                        if (possibleCellsCapture.isEmpty()) {
                            board.changePhase(Phases.GO_INTO_DECLINE);
                        } else {
                            board.changePhase(Phases.CAPTURE_OF_REGIONS);
                        }
                    }
                }
                if (Phases.GO_INTO_DECLINE.equals(board.getPhase())) {
                    if (round != 1) {
                        currentPlayer.goIntoDecline();
                        LoggerGame.logRaceInDecline(currentPlayer);
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
                }
                LoggerGame.logStartPhaseCaptureOfRegions();
                while (Phases.CAPTURE_OF_REGIONS.equals(board.getPhase())) {
                    LoggerGame.logGetTokens(currentPlayer);
                    if (currentPlayer.getLocationCell().isEmpty()) {
                        possibleCellsCapture = playerService.findOutWherePlayerCanGoAtFirst(board,currentPlayer);
                        LoggerGame.logWhereToGo(possibleCellsCapture);
                    } else {
                        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, currentPlayer);
                        LoggerGame.logWhereToGo(possibleCellsCapture);
                    }
                    if (!possibleCellsCapture.isEmpty()) {
                        try {
                            out.flush();
                            out.writeUTF(BoardSerializer.serialize(board));
                            ResponseCell answer = (ResponseCell) AnswerDeserialize.deserialize(in.readUTF());
                            playerService.regionCapture(answer.getCell(), currentPlayer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        LoggerGame.logRedistributionOfTokens(currentPlayer);
                        currentPlayer.shufflingTokens(new Cell());
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
                }
                while (Phases.GETTING_COINS.equals(board.getPhase())) {
                    LoggerGame.logStartPhaseGetCoins();
                    for (ServerLobby server : serverClient) {
                        server.getPlayer().collectAllCoins();
                        LoggerGame.logGetCoins(server.getPlayer());
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
