package ru.omsk.neoLab;

import lombok.extern.slf4j.Slf4j;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.race.*;

import java.util.ArrayList;
import java.util.HashSet;

@Slf4j
public class PlayerService {
    // TODO: Это вообще что? Что делает instance?
    private static PlayerService instance;

    // Массивы, для вычисления допустимых ходов
    private final int[] motionAreaX = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] motionAreaY = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    private static final ArrayList<ARace> racesPool = new ArrayList<ARace>();
    private final HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();

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

    public HashSet<Cell> findOutWherePlayerCanGo(final Cell[][] board) {
        possibleCellsCapture.clear();
        for (int i = 0; i < board[0].length; i++) {
            possibleCellsCapture.add(board[0][i]);
            possibleCellsCapture.add(board[board.length - 1][i]);
        }
        for (int i = 1; i < board.length - 1; i++) {
            possibleCellsCapture.add(board[i][0]);
            possibleCellsCapture.add(board[i][board[0].length - 1]);
        }
        return possibleCellsCapture;
    }

    public HashSet<Cell> findOutWherePlayerCanGo(final Board board, final Player player) {
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
        log.info("Захвачена территория {} c координатами [{}][{}]", cell.getType(), cell.getX(), cell.getY());
        log.info("Осталось жетонов у игрока {} - {} от территории {}", player.getNickName(), player.getCountTokens(), cell.getType());
        LoggerGame.logRegionCaptureTrue(player, cell);
    }

    public void getToken(final Cell cell, final int tokens) {
        cell.getToken(tokens);
    }

    public void putToken(final Cell cell, final int tokens) {
        cell.putToken(tokens);
    }


    public static ArrayList<ARace> getRacesPool() {
        return racesPool;
    }

}
