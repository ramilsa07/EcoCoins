package ru.omsk.neoLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.board.Serializer.BoardSerializer;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;
import ru.omsk.neoLab.player.Serializer.PlayerDeserializer;
import ru.omsk.neoLab.race.Serializer.RaceDeserializer;

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

    enum Phases {
        RACE_CHOICE("race choice"), // Выбор расы
        CAPTURE_OF_REGIONS("capture of regions"), // захват регионов
        PICK_UP_TOKENS("pick up tokens"), // В начале раунда берем жетоны на руки
        GO_INTO_DECLINE("go into decline"), // уйти в  ̶з̶а̶п̶о̶й̶ упадок
        GETTING_COINS("getting coins"), // Получение монет
        ;

        private final String phasesName;

        Phases(final String phasesName) {
            this.phasesName = phasesName;
        }

        boolean equalPhase(final String phase) {
            return phasesName.equals(phase);
        }
    }

    private final ConcurrentLinkedQueue<Player> players = new ConcurrentLinkedQueue<>();

    private class Game extends Thread {

        private Server server;
        private Socket socket;

        private DataInputStream in;
        private DataOutputStream out;

        private final Board board = Board.GetInstance();
        private final PlayerService playerService = PlayerService.GetInstance();

        private String phase;
        private int round = 1;

        private HashSet<Cell> possibleCellsCapture = new HashSet<>();


        private Game(final Server server, final Socket socket) throws IOException {
            this.server = server;
            this.socket = socket;

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }

        @Override
        public void run() {
            try {
                players.add(PlayerDeserializer.deserialize(in.readUTF()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            generateBoard();
            LoggerGame.logOutputBoard(board);
            Player firstPlayer = players.element();
            Player currentPlayer = players.element();
            while (round < 11) {
                LoggerGame.logPlayerRoundStart(currentPlayer, round);
                if (currentPlayer.isDecline() || round == 1) {
                    LoggerGame.logStartPhaseRaceChoice();
                    phase = "race choice";
                    while (Phases.RACE_CHOICE.equalPhase(phase)) {
                        LoggerGame.logWhatRacesCanIChoose(PlayerService.getRacesPool());
                        try {
                            out.writeUTF(BoardSerializer.serialize(board));
                            currentPlayer.changeRace(RaceDeserializer.deserialize(in.readUTF()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LoggerGame.logChooseRaceTrue(currentPlayer);
                        phase = "capture of regions";
                    }
                }
                if (Phases.PICK_UP_TOKENS.equalPhase(phase)) {
                    LoggerGame.logStartPhasePickUpTokens();
                    while (Phases.PICK_UP_TOKENS.equalPhase(phase)) {
                        for (Cell cell : currentPlayer.getLocationCell()) {
                            if (cell.getCountTokens() >= 1) {
                                currentPlayer.collectTokens();
                            }
                        }
                        LoggerGame.logGetTokens(currentPlayer);
                        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, currentPlayer);
                        if (possibleCellsCapture.isEmpty()) {
                            phase = "go into decline";
                        } else {
                            phase = "capture of regions";
                        }
                    }
                }
                if (Phases.GO_INTO_DECLINE.equalPhase(phase)) {
                    if (round != 1) {
                        currentPlayer.goIntoDecline();
                        LoggerGame.logRaceInDecline(currentPlayer);
                        changeCourse(currentPlayer);
                        currentPlayer = players.element();
                        if (currentPlayer.equals(firstPlayer)) {
                            phase = "getting coins";
                        } else if (currentPlayer.isDecline()) {
                            phase = "race choice";
                        } else {
                            phase = "pick up tokens";
                        }
                    }
                }
                LoggerGame.logStartPhaseCaptureOfRegions();
                while (Phases.CAPTURE_OF_REGIONS.equalPhase(phase)) {
                    LoggerGame.logGetTokens(currentPlayer);
                    if (currentPlayer.getLocationCell().isEmpty()) {
                        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board.getBoard());
                        LoggerGame.logWhereToGo(possibleCellsCapture);
                    } else {
                        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, currentPlayer);
                        LoggerGame.logWhereToGo(possibleCellsCapture);
                    }
                    Object[] cells = possibleCellsCapture.toArray();
                    if (!possibleCellsCapture.isEmpty()) {
                        //bot.getRandomRegionToCapture(playerService, cells, currentPlayer);
                    } else {
                        LoggerGame.logRedistributionOfTokens(currentPlayer);
                        currentPlayer.shufflingTokens();
                        changeCourse(currentPlayer);
                        currentPlayer = players.element();
                        if (currentPlayer.equals(firstPlayer)) {
                            phase = "getting coins";
                            break;
                        } else if (currentPlayer.isDecline()) {
                            phase = "race choice";
                            break;
                        } else {
                            phase = "pick up tokens";
                            break;
                        }
                    }
                }
                while (Phases.GETTING_COINS.equalPhase(phase)) {
                    LoggerGame.logStartPhaseGetCoins();
                    for (Player player : players) {
                        player.collectAllCoins();
                        LoggerGame.logGetCoins(player);
                    }
                    round++;
                    if (currentPlayer.isDecline()) {
                        phase = "race choice";
                    } else {
                        phase = "pick up tokens";
                    }

                }
                if (round == 11) {
                    toEndGame();
                }
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
                    server.players.remove(this);
                }
            } catch (final IOException ignored) {
            }
        }

        public void toEndGame() {
            for (Player player : players) {
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
