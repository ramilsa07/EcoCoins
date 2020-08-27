package ru.omsk.neoLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;
import java.util.HashSet;

/*
 * Класс хранит все предупреждения во время игры.
 * */

public final class LoggerGame {

    private static final Logger log = LoggerFactory.getLogger(LoggerGame.class);

    public static void logWhereToGo(HashSet<Cell> possibleCellsCapture) { // Куда можно пойти
        log.info("Can go: ");
        for (Cell cell : possibleCellsCapture) {
            log.info("{} [{}, {}]", cell.getType(), cell.getX(), cell.getY());
        }
    }

    public static void logWhatRacesCanIChoose(ArrayList<ARace> freeRacesPool) { // Выводим на экран свободные расы
        log.info("Can choose: ");
        for (ARace race : freeRacesPool) {
            log.info(race.getNameRace());
        }
    }
}
