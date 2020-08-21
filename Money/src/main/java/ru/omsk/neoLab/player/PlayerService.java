package ru.omsk.neoLab.player;


import ru.omsk.neoLab.Validator;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class PlayerService {
    private static PlayerService instance;

    // Массивы, для вычисления допустимых ходов
    private final int[] motionAreaX = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] motionAreaY = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    private ArrayList<ARace> racesPool = new ArrayList<>();

    public HashSet<Cell> possibleCellsCapture = new HashSet<>();

    private PlayerService() {
    }

    public static PlayerService GetInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public final HashSet<Cell> findOutWherePlayerCanGoFirst(final Board board, final Player player) {
        possibleCellsCapture.clear();
        for (int i = 0; i < board.getBoard()[0].length; i++) {
            if (Validator.isCheckingOutputOverBoard(0, i, board.getHeight(), board.getWidth()))
                if (Validator.isCheckingCapture(board.getBoard()[0][i], player)) {
                    possibleCellsCapture.add(board.getBoard()[0][i]);
                    possibleCellsCapture.add(board.getBoard()[board.getBoard().length - 1][i]);
                    //LoggerGame.logAttackCell(player,board.getBoard()[0][i]);
                }
        }
        for (int i = 1; i < board.getBoard().length - 1; i++) {
            if (Validator.isCheckingOutputOverBoard(i, 0, board.getHeight(), board.getWidth()))
                if (Validator.isCheckingCapture(board.getBoard()[i][0], player)) {
                    possibleCellsCapture.add(board.getBoard()[i][0]);
                    possibleCellsCapture.add(board.getBoard()[i][board.getBoard()[0].length - 1]);
                    //LoggerGame.logAttackCell(player, board.getBoard()[i][0]);
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
                if (Validator.isCheckingOutputOverBoard(x, y, board.getHeight(), board.getWidth())) {
                    if (!Validator.isCheckingBelongsCell(player, board.getBoard()[x][y])) {
                        if (Validator.isCheckingCapture(board.getBoard()[x][y], player)) {
                            possibleCellsCapture.add(board.getBoard()[x][y]);
                            //LoggerGame.logAttackCell(player, board.getBoard()[x][y]);
                        }
                    }
                }
            }
        }
        return possibleCellsCapture;
    }

    public void regionCapture(Cell cell, Player player) {
        player.regionCapture(cell);
        //LoggerGame.logRegionCaptureTrue(player, cell);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(possibleCellsCapture);
        result = 31 * result + Arrays.hashCode(motionAreaX);
        result = 31 * result + Arrays.hashCode(motionAreaY);
        return result;
    }
}
