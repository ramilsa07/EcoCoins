package ru.omsk.neoLab.selfplay;

import lombok.extern.slf4j.Slf4j;
import ru.omsk.neoLab.Player;
import ru.omsk.neoLab.PlayerService;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.ACell;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@Slf4j
public class SelfPlay {

    private final Random random = new Random();

    private Board board = Board.GetInstance();
    private PlayerService playerService = PlayerService.getInstance();

    private Queue<Player> players = new LinkedList<Player>();

    private String phase;
    private int round = 0;

    private HashSet<ACell> possibleCellsCapture = new HashSet<ACell>();

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

    public void Game() {
        generateBoard();
        Player firstPlayer = players.element();
        Player currentPlayer = players.element();
        while (true) {
            round++;
            if (currentPlayer.isDecline() || round == 1) {
                log.info("Началась фаза выбора расы");
                phase = "race choice";
                while (Phases.RACE_CHOICE.equalPhase(phase)) {
                    currentPlayer.changeRace(PlayerService.getRacesPool().get((random.nextInt(PlayerService.getRacesPool().size()))));
                    phase = "capture of regions";
                }
            }
            log.info("Началась фаза захвата территории");
            while (Phases.CAPTURE_OF_REGIONS.equalPhase(phase)) {

                if (round != 1) {
                    log.info("{} решил уйти в упадок", currentPlayer.getNickName());
                    currentPlayer.goIntoDecline();
                    changeCourse(currentPlayer);
                    currentPlayer = players.element();
                    break;
                }
                if (currentPlayer.getLocationCell().isEmpty()) {
                    possibleCellsCapture = playerService.findOutWherePlayerCanGo(board.getBoard());
                } else {
                    possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, currentPlayer);
                }
                Object[] cells = possibleCellsCapture.toArray();
                if (!possibleCellsCapture.isEmpty())
                    playerService.regionCapture((ACell) cells[random.nextInt(cells.length)], currentPlayer);
                else {
                    log.info("Начинаем перераспределять");
                    currentPlayer.shufflingTokens();
                    changeCourse(currentPlayer);
                    currentPlayer = players.element();
                    if (currentPlayer.equals(firstPlayer)) {
                        phase = "getting coins";
                    }
                }
            }
            if (phase.equals("getting coins")) {
                log.info("Началась фаза c Сбор Монет");
            }
            while (Phases.GETTING_COINS.equalPhase(phase)) {
                for (Player player : players) {
                    player.collectAllCoins();
                    log.info("Теперь у {} монет {}", player.getNickName(), player.getCountCoin());
                }
                round++;
            }
            if (round == 10) {
                toEndGame();
                System.exit(0);
            }
        }
    }

    private void generateBoard() {
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
    }
}
