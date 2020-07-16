package ru.omsk.neoLab;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;

public final class Player {

    private final String nickName;
    private int countCoin = 0;
    private int countTokens = 0;

    private ARace race;
    private ArrayList<ACell> locationCell = new ArrayList<ACell>();

    private ARace raceDecline = null;
    private ArrayList<ACell> locationDeclineCell = new ArrayList<ACell>();

    private PlayerService service = PlayerService.GetInstance();

    private boolean decline = false;

    public Player(String nickName) {
        this.nickName = nickName;
    }

    public void changeRace(final ARace race) {
        if (decline) {
            if (raceDecline != null) {
                PlayerService.getRacesPool().add(raceDecline);
            }
            this.raceDecline = this.race;
            this.locationDeclineCell = locationCell;
            this.decline = false;
            locationCell.clear();
        }
        this.race = race;
        PlayerService.getRacesPool().remove(race);
    }

    public void pickUpTokens() {
        for (ACell cell : location.getCells()) {
            this.countTokens += cell.getCountTokens() - 1;
            cell.setCountTokens(1);
        }
        LoggerGame.logPickUpTokens(this);
        LoggerGame.logNumberOfFreeTokens(this);
    }

    public void makeCoinCount() {
        // Сбор монет с действующих рас
        if (race.getNameRace().equals("Elfs")) {
            if (location.getCells().contains(new Earth())) {
                countCoin += 1;
            }
            if (location.getCells().contains(new Mounted())) {
                countCoin += 1;
            }
            if (location.getCells().contains(new Mushrooms())) {
                countCoin += 1;
            }
        }
        for (ACell cell : location.getCells()) {
            countCoin += cell.getCoin();
        }

        // Сбор монет с упадших рас
        if (locationDecline != null && raceDecline != null) {
            if (raceDecline.getNameRace().equals("Elfs")) {
                if (locationDecline.getCells().contains(new Earth())) {
                    countCoin += 1;
                }
                if (locationDecline.getCells().contains(new Mounted())) {
                    countCoin += 1;
                }
                if (locationDecline.getCells().contains(new Mushrooms())) {
                    countCoin += 1;
                }
            }
            for (ACell cell : locationDecline.getCells()) {
                countCoin += cell.getCoin();
            }
        }
    }

    public String getNickName() {
        return nickName;
    }

    public int getCountCoin() {
        return countCoin;
    }

    public ARace getRace() {
        return race;
    }

    public ListCell getLocation() {
        return location;
    }

    public int getCountTokens() {
        return countTokens;
    }

    public void setRaceDecline() {
        raceDecline = race;
        race = null;
        countTokens = 0;
    }
}
