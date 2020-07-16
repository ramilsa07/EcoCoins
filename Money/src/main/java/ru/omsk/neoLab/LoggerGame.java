package ru.omsk.neoLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;

public class LoggerGame {

    private static final Logger log = LoggerFactory.getLogger(LoggerGame.class);

    public static void logChooseRaceTrue(Player player) {
        log.info(player.getNickName() + " - выбрал расу - " + player.getRace().getNameRace());
    }

    public static void logChooseRaceFalse() {
        log.info("Такая раса уже в игре");
    }

    public static void logAvailableRaces(ArrayList<ARace> setRaceAvailable) {
        StringBuilder races = new StringBuilder();
        for (ARace race : setRaceAvailable) {
            races.append(race.getNameRace()).append(" ");
        }
        log.info("Доступные расы: " + races);
    }


    public static void logRegionCaptureTrue(Player player) {
        log.info("Территория захвачена игроком - " + player.getNickName());
    }

    public static void logRegionCaptureFalse(Player player) {
        log.info("Территорию нельзя захватить");
    }

    public static void logRegionCapturePlayer(Player player) {
        log.info("Территория захвачена игроком - " + player.getNickName());
    }
}
