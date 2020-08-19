package ru.omsk.neoLab.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.LoggerGame;
import ru.omsk.neoLab.Validator;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.race.*;

import java.util.ArrayList;
import java.util.HashSet;

public class PlayerService {
    private static final Logger log = LoggerFactory.getLogger(LoggerGame.class);
    private static PlayerService instance;

    // Массивы, для вычисления допустимых ходов
    private final int[] motionAreaX = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] motionAreaY = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    private static final ArrayList<ARace> racesPool = new ArrayList<ARace>();

    public HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();


    static {
        racesPool.add(new Amphibia());
        racesPool.add(new Dwarfs());
        racesPool.add(new Elfs());
        racesPool.add(new Mushrooms());
        racesPool.add(new Orcs());
        racesPool.add(new Undead());
    }

    private PlayerService() {
    }

    public static PlayerService GetInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public final HashSet<Cell> findOutWherePlayerCanGoAtFirst(final Board board, Player player) {
        possibleCellsCapture.clear();
        for (int i = 0; i < board.getBoard()[0].length; i++) {
            if (Validator.isCheckingOutputOverBoard(0, i, board.getHeight(), board.getWidth()))
                if (!Validator.isCheckingBelongsCell(player, board.getBoard()[0][i]))
                    if (Validator.isCheckingCapture(board.getBoard()[0][i], player)) {
                        possibleCellsCapture.add(board.getBoard()[0][i]);
                        possibleCellsCapture.add(board.getBoard()[board.getBoard().length - 1][i]);
                    }
        }
        for (int i = 1; i < board.getBoard().length - 1; i++) {
            if (Validator.isCheckingOutputOverBoard(i, 0, board.getHeight(), board.getWidth()))
                if (!Validator.isCheckingBelongsCell(player, board.getBoard()[i][0]))
                    if (Validator.isCheckingCapture(board.getBoard()[i][0], player)) {
                        possibleCellsCapture.add(board.getBoard()[i][0]);
                        possibleCellsCapture.add(board.getBoard()[i][board.getBoard()[0].length - 1]);
                    }
        }
        return possibleCellsCapture;
    }

    public final HashSet<Cell> findOutWherePlayerCanGo(final Board board, final Player player) {
        possibleCellsCapture.clear();
        for (Cell cell : player.getLocationCell()) {
            for (int i = 0; i < 8; i++) {
                int x = cell.getX() + motionAreaX[i];
                int y = cell.getY() + motionAreaY[i];
                if (Validator.isCheckingOutputOverBoard(x, y, board.getHeight(), board.getWidth()))
                    if (!Validator.isCheckingBelongsCell(player, board.getBoard()[x][y]))
                        if (Validator.isCheckingCapture(board.getBoard()[x][y], player))
                            possibleCellsCapture.add(board.getBoard()[x][y]);
            }
        }
        return possibleCellsCapture;
    }

    public void regionCapture(Cell cell, Player player) {
        player.regionCapture(cell);
        log.info("The cell {} [{}, {}] is captured by the player {}", cell.getType(), cell.getX(),
                cell.getY(), player.getNickName());
    }

    public static ArrayList<ARace> getRacesPool() {
        logWhatRacesCanIChoose(racesPool);
        return racesPool;
    }

    public static void logWhatRacesCanIChoose(ArrayList<ARace> freeRacesPool) { // Выводим на экран свободные расы
        log.info("Can choose: ");
        for (ARace race : freeRacesPool) {
            log.info(race.getNameRace());
        }
    }
}
