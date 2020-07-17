package ru.omsk.neoLab.selfplay;

import lombok.extern.slf4j.Slf4j;
import ru.omsk.neoLab.LoggerGame;
import ru.omsk.neoLab.Player;
import ru.omsk.neoLab.PlayerService;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.board.Сell.Cell;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@Slf4j
public class SelfPlay {

    private final Random random = new Random();

    private final Board board = Board.GetInstance(); // ЭТО ЧЕЕЕЕЕ???
    private final PlayerService playerService = PlayerService.GetInstance();

    private final Queue<Player> players = new LinkedList<Player>();

    private String phase;
    private int round = 1;

    private HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();

    enum Phases {
        RACE_CHOICE("race choice"), // Выбор расы
        CAPTURE_OF_REGIONS("capture of regions"), // захват регионов
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

    //TODO:Поправить логи
    public void Game() {
        generateBoard();
        LoggerGame.logOutputBoard(board);
        Player firstPlayer = players.element();
        LoggerGame.logNickSelection(firstPlayer);
        Player currentPlayer = players.element();
        LoggerGame.logNickSelection(currentPlayer);
        while (round <= 11) {
            LoggerGame.logRoundNumber(round);
            //TODO:Добавить параметр для логичного упадка
            if (currentPlayer.isDecline() || round == 1) {
                log.info("Началась фаза выбора расы");
                phase = "race choice";
                while (Phases.RACE_CHOICE.equalPhase(phase)) {
                    LoggerGame.logWhatRacesCanIChoose(PlayerService.getRacesPool()); // Почему PlayerService как класс, а не экземпляр?
                    currentPlayer.changeRace(PlayerService.getRacesPool().get((random.nextInt(PlayerService.getRacesPool().size()))));
                    log.info("{} выбрал расу {}", currentPlayer.getNickName(), currentPlayer.getRace().getNameRace());
                    phase = "capture of regions";
                }
            }
            LoggerGame.logWhatRacesCanIChoose(PlayerService.getRacesPool()); // Чисто посмотреть удаляется ли раса
            log.info("Началась фаза захвата территории");
            while (Phases.CAPTURE_OF_REGIONS.equalPhase(phase)) {
                for (Cell cell : currentPlayer.getLocationCell()) {
                    if (cell.getCountTokens() >= 1) {
                        currentPlayer.collectTokens();
                    }
                }
                //TODO:Сколько сейчас жетонов
                if (round != 1 && currentPlayer.getCountTokens() == 0) {
                    currentPlayer.goIntoDecline();
                    log.info("{} решил уйти в упадок", currentPlayer.getNickName());
                    changeCourse(currentPlayer);
                    currentPlayer = players.element();
                    break;
                }
                if (currentPlayer.getLocationCell().isEmpty()) {
                    possibleCellsCapture = playerService.findOutWherePlayerCanGo(board.getBoard());
                    LoggerGame.logWhereToGo(possibleCellsCapture);
                } else {
                    possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, currentPlayer);
                    LoggerGame.logWhereToGo(possibleCellsCapture);
                }
                Object[] cells = possibleCellsCapture.toArray();
                if (!possibleCellsCapture.isEmpty())
                    playerService.regionCapture((Cell) cells[random.nextInt(cells.length)], currentPlayer);
                else {
                    log.info("Начинаем перераспределять");
                    currentPlayer.shufflingTokens();
                    changeCourse(currentPlayer);
                    currentPlayer = players.element();
                    if (currentPlayer.equals(firstPlayer)) {
                        phase = "getting coins";
                    } else
                        break;
                }
            }
            while (Phases.GETTING_COINS.equalPhase(phase)) {
                log.info("Началась фаза c Сбор Монет");
                for (Player player : players) {
                    player.collectAllCoins();
                    log.info("Теперь у {} монет {}", player.getNickName(), player.getCountCoin());
                }
                round++;
                phase = "capture of regions";
            }
            if (round == 10) {
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
    }

    public void toEndGame() {
        for (Player player : players) {
            log.info("Info {} coins {}", player.getCountCoin(), player.getNickName());
        }
        System.exit(0);
    }
}
