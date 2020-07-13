package ru.omsk.neoLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Cells.ListCell;
import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.race.ARace;

import java.util.HashSet;

public class GameService {

    Logger logger = LoggerFactory.getLogger(GameService.class);

    private static volatile GameService instance;

    private volatile HashSet<ARace> racesUsed = new HashSet<ARace>();
    private volatile HashSet<ARace> racesPool = new HashSet<ARace>();

    private volatile HashSet<ACell> possibleCellsCapture = new HashSet<ACell>();

    private int[] maxi = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] maxj = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    private GameService() {
    }

    public static synchronized GameService GetInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    //TODO: Нужно сделать как будет формироваться пул рас;
    public static void createPoolRaces() {

    }

    public void chooseRace(Player player, ARace race) {
        if (Validator.isCheckRaceInGame(race, racesUsed)) {
            player.setRace(race);
            racesUsed.add(race);
            racesPool.remove(race);
            LoggerGame.logChooseRaceTrue(player);
        }
        LoggerGame.logChooseRaceFalse();
    }

    public void findOutWherePlayerCanGo(ACell[][] board) {
        possibleCellsCapture.clear();
        for (int i = 0; i < board[0].length; i++) {
            possibleCellsCapture.add(board[0][i]);
            possibleCellsCapture.add(board[board.length - 1][i]);
        }
        for (int i = 1; i < board.length - 1; i++) {
            possibleCellsCapture.add(board[i][0]);
            possibleCellsCapture.add(board[i][board.length - 1]);
        }
    }

    public HashSet<ACell> findOutWherePlayerCanGo(Board board, Player player) {
        for (ACell cell : player.getLocation().getCells()) {
            for (int i = 0; i < 8; i++) {
                int x = cell.getX() + maxi[i];
                int y = cell.getY() + maxj[i];
                logger.info(x + "-" + y);
                if (Validator.isCheckingOutputOverBoard(cell.getX() + maxi[i], cell.getY() + maxj[i], board.getHeight(), board.getWidth())) {
                    if (!Validator.isCheckingBelongsCell(player, board.getBoard()[cell.getX() + maxi[i]][cell.getY() + maxj[i]])) {
                        possibleCellsCapture.add(board.getBoard()[cell.getX() + maxi[i]][cell.getY() + maxj[i]]);
                    }
                }
            }
        }
        return possibleCellsCapture;
    }

    public void attack(ACell cell, Player player) {

    }

    public void toPlaceTokens() {

    }

    public void regionCapture(ACell cell, Player player) {
        if (cell.getAbilityCapture()) {
            if (!cell.getBelongs().equals(player)) {
                player.getLocation().add(cell);
                LoggerGame.logRegionCaptureTrue(player);
            }
        }
        LoggerGame.logRegionCaptureFalse(player);
    }

    public void unitDistribution(Player player) {

    }

    public void toCountCoins(Player player) {
        ListCell listCellPlayer = player.getLocation();
    }
}
