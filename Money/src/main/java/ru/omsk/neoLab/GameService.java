package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Generators.Cells.ListCell;
import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.race.ARace;

import java.util.HashSet;

public class GameService {

    private static volatile GameService instance;

    private volatile HashSet<ARace> racesPool = new HashSet<ARace>();

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

    public void attack(ACell cell, Player player) {

    }

    public void toPlaceTokens() {

    }

    public void regionCapture(ACell cell, Player player) {
        if (cell.getAbilityCapture(player.getRace())) {
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
