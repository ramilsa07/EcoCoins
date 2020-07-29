package ru.omsk.neoLab.selfplay;

import ru.omsk.neoLab.LoggerGame;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;
import ru.omsk.neoLab.simpleBot.SimpleBot;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public final class SelfPlay {

    private final SimpleBot bot = new SimpleBot();

    private final Board board = new Board(4, 3);
    private final PlayerService playerService = PlayerService.GetInstance();

    public Queue<Player> getPlayers() {
        return players;
    }

    private final Queue<Player> players = new LinkedList<Player>();

    private String phase;
    private int round = 1;

    private HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();


    enum Phases {
        RACE_CHOICE("race choice"), // Выбор расы
        CAPTURE_OF_REGIONS("capture of regions"), // захват регионов
        PICK_UP_TOKENS("pick up tokens"), // В начале раунда берем жетоны на руки
        GO_INTO_DECLINE("go into decline"), // уйти в  ̶з̶а̶п̶о̶й̶ упадок
        GETTING_COINS("getting coins"); // Получение монет

        private final String phasesName;

        Phases(final String phasesName) {
            this.phasesName = phasesName;
        }

        boolean equalPhase(final String phase) {
            return phasesName.equals(phase);
        }
    }

    public void Game() {
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
                    currentPlayer.changeRace(bot.getRandomRace());
                    LoggerGame.logChooseRaceTrue(currentPlayer);
                    phase = "capture of regions";
                }
            }
            if (Phases.PICK_UP_TOKENS.equalPhase(phase)) {
                LoggerGame.logStartPhasePickUpTokens();
                while (Phases.PICK_UP_TOKENS.equalPhase(phase)) {
                    for (Cell cell : currentPlayer.getLocationCell()) {
                        if (cell.getCountTokens() > 1) {
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
                if (!possibleCellsCapture.isEmpty())
                    playerService.regionCapture(bot.getRandomRegionToCapture(cells), currentPlayer);
                else {
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
    }

    private void changeCourse(Player player) {
        players.poll();
        players.add(player);
    }

    public void createNewPlayer(Player player) {
        players.add(player);
        LoggerGame.logNickSelection(player);
    }

    public void toEndGame() {
        for (Player player : players) {
            LoggerGame.logGetCoins(player);
        }
        LoggerGame.logEndGame();
        System.exit(0);
    }
}
