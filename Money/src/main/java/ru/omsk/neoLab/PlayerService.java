package ru.omsk.neoLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.ACell;
import ru.omsk.neoLab.race.*;

import java.util.ArrayList;
import java.util.HashSet;

public class PlayerService {

    public static Logger log = LoggerFactory.getLogger(PlayerService.class);

    private static PlayerService instance;

    // Массивы, для вычисления допустимых ходов
    private final int[] motionAreaX = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] motionAreaY = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    private static final ArrayList<ARace> racesPool = new ArrayList<>();
    private final HashSet<ACell> possibleCellsCapture = new HashSet<>();

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

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public HashSet<ACell> findOutWherePlayerCanGo(final ACell[][] board) {
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

    public HashSet<ACell> findOutWherePlayerCanGo(final Board board, final Player player) {
        possibleCellsCapture.clear();
        for (ACell cell : player.getLocationCell()) {
            for (int i = 0; i < 8; i++) {
                int x = cell.getX() + motionAreaX[i];
                int y = cell.getY() + motionAreaY[i];
                if (Validator.isCheckingOutputOverBoard(x, y, board.getHeight(), board.getWidth())) {
                    if (!Validator.isCheckingBelongsCell(player, board.getBoard()[x][y])) {
                        if (player.getCountTokens() - board.getBoard()[x][y].getTokensCapture() >= 0)
                            possibleCellsCapture.add(board.getBoard()[x][y]);
                    }
                }
            }
        }
        return possibleCellsCapture;
    }

    public void regionCapture(ACell cell, Player player) {
        LoggerGame.logRegionCaptureTrue(player, cell);
        player.getLocationCell().add(cell);
        cell.setRace(player.getRace());
        log.info("Осталось жетонов у игрока {} - {} от территории {}  и потратили жетонов {}", player.getNickName(),
                player.getCountTokens(), cell.getType(), cell.getTokensCapture());
        player.setCountTokens(player.getCountTokens() - cell.getTokensCapture());
        cell.setCountTokens(cell.getTokensCapture());
        cell.setBelongs(player);
    }

    public void getToken(final ACell cell, final int tokens) {
        cell.getToken(tokens);
    }

    public void putToken(final ACell cell, final int tokens) {
        cell.putToken(tokens);
    }

    public static ArrayList<ARace> getRacesPool() {
        return racesPool;
    }

}
