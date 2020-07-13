package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Generators.Cells.ListCell;
import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.race.ARace;

public class Player {

    private final String nickName;
    private int countCoin;
    private int countTokens;

    private ARace race;
    private ListCell location = new ListCell();

    private ARace raceDecline;
    private ListCell locationDecline;

    public Player(String nickName) {
        this.nickName = nickName;
        this.countCoin = 0;
    }

    public void takeRaceFromPool(ARace race) {
        this.race = race;
        countTokens = race.getCountTokens();
    }

    public void addTerritory(ACell cell) {
        if (cell.getAbilityCapture()) {
            countTokens -= cell.getCountUnits() + getCountTokens();
            location.add(cell);
        }
        if (cell.getBelongs() != this) {
            countTokens -= cell.getCountUnits() + 1;
        }
    }

    public void PickUpUnits(int countUnits) {
        for (ACell call : location.getCells()) {
            this.countTokens += call.getCountUnits() - 1;
        }
    }

    public void makeCoinCount() {
        for (ACell call : location.getCells()) {
            //if(call.getRace().equals("Elfs"))
            // TODO: Придумать условие получения монеток для эльфов
            countCoin += call.getCoin();
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

    public void setRaceDecline(ARace raceDecline) {
        this.raceDecline = raceDecline;
    }

    public void setRace(ARace race) {
        this.race = race;
    }

    public void setLocation(ListCell location) {
        this.location = location;
    }
}
