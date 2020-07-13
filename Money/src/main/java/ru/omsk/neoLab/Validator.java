package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.race.ARace;

import java.util.HashSet;

public class Validator {

    public static boolean isCheckingBelongsCell(Player player, ACell cell) {
        return cell.getBelongs().equals(player);
    }

    public static boolean isCheckingOutputOverBoard(int x, int y, int height, int width) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public static boolean isCheckRaceInGame(ARace race, HashSet<ARace> raceSet) {
        return raceSet.contains(race);
    }

}
