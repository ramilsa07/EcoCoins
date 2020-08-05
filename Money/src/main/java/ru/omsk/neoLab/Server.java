package ru.omsk.neoLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.Answer.AnswerDeserializer;
import ru.omsk.neoLab.Answer.CellAnswer;
import ru.omsk.neoLab.Answer.RaceAnswer;
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
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

    private static Logger log = LoggerFactory.getLogger(Server.class);

    static final int PORT = 8081;
    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private final ConcurrentLinkedQueue<Player> serverPlayers = new ConcurrentLinkedQueue<>();
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

    public class Game extends Thread {

        private Server server;
        private Socket socket;

        private DataInputStream in;
        private DataOutputStream out;

        private Board board = new Board(4, 3);
        private final PlayerService playerService = PlayerService.GetInstance();

        private int round = 1;

        public HashSet<Cell> getPossibleCellsCapture() {
            return possibleCellsCapture;
        }

        public void setPossibleCellsCapture(HashSet<Cell> possibleCellsCapture) {
            this.possibleCellsCapture = possibleCellsCapture;
        }

        public HashSet<Cell> possibleCellsCapture = new HashSet<>();


        private Game(final Server server, final Socket socket) throws IOException {
            this.server = server;
            this.socket = socket;

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }

        @Override
        public void run() {
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            generateBoard();
            LoggerGame.logOutputBoard(board);
            Player firstPlayer = serverPlayers.element();
            Player currentPlayer = serverPlayers.element();
            while (round < 11) {
                LoggerGame.logPlayerRoundStart(currentPlayer, round);
                if (currentPlayer.isDecline() || round == 1) {
                    LoggerGame.logStartPhaseRaceChoice();
                    board.changePhase(Phases.RACE_CHOICE);
                    while (Phases.RACE_CHOICE.equals(board.getPhase())) {
                        LoggerGame.logWhatRacesCanIChoose(PlayerService.getRacesPool());
                        try {
                            out.writeUTF(BoardSerializer.serialize(board));
                            RaceAnswer race = (RaceAnswer) AnswerDeserializer.deserialize(in.readUTF());
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
                        changeCourse(currentPlayer);
                        currentPlayer = serverPlayers.element();
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
                        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board.getBoard());
                        LoggerGame.logWhereToGo(possibleCellsCapture);
                    } else {
                        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, currentPlayer);
                        LoggerGame.logWhereToGo(possibleCellsCapture);
                    }
                    if (!possibleCellsCapture.isEmpty()) {
                        try {
                            out.flush();
                            out.writeUTF(BoardSerializer.serialize(board));
                            CellAnswer answer = (CellAnswer) AnswerDeserializer.deserialize(in.readUTF());
                            playerService.regionCapture(answer.getCell(), currentPlayer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        LoggerGame.logRedistributionOfTokens(currentPlayer);
                        currentPlayer.shufflingTokens();
                        changeCourse(currentPlayer);
                        currentPlayer = serverPlayers.element();
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
                    for (Player player : serverPlayers) {
                        player.collectAllCoins();
                        LoggerGame.logGetCoins(player);
                    }
                    round++;
                    if (currentPlayer.isDecline()) {
                        board.changePhase(Phases.RACE_CHOICE);
                    } else {
                        board.changePhase(Phases.PICK_UP_TOKENS);
                    }

                }
                if (round == 11) {
                    toEndGame();
                }
            }
        }

        public void generateBoard() {
            board = new Board(4, 3);
            board.generate();
        }

        private void changeCourse(Player player) {
            serverPlayers.poll();
            serverPlayers.add(player);
        }

        public void createNewPlayer(Client client) {
            serverPlayers.add(client);
            LoggerGame.logNickSelection(client);
        }

        private void downService() {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                    server.serverPlayers.remove(this);
                }
            } catch (final IOException ignored) {
            }
        }

        public void toEndGame() {
            for (Player player : serverPlayers) {
                LoggerGame.logGetCoins(player);
            }
            LoggerGame.logEndGame();
            System.exit(0);
        }
    }


    @SuppressWarnings("InfiniteLoopStatement")
    private void startServer() throws IOException {
        System.out.println(String.format("Server started, port: %d", PORT));

        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                Player player = new Player();
                serverPlayers.add(player);
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
