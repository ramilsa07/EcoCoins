package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;
import ru.omsk.neoLab.board.Generators.Calls.ListCall;
import ru.omsk.neoLab.race.ARace;

public class Player {
    private final String nickName;
    private int countCoin;
    private ARace race;
    private ARace raceDecline;
    private ListCall location;
    private ListCall locationDecline;
    private int countUnits;

    public Player(String nickName) {
        this.nickName = nickName;
        this.countCoin = 0;
    }

    public void takeRaceFromPool(ARace race) {
        this.race = race;
        countUnits = race.getCountUnit();
    }

    public void addTerritory(ACall call) {
        if (call.isAbilityCapture(this.race) && call.getBelongs() != this && call.getBelongs() != null) {
            countUnits -= call.getRequirementsForCapture() + getCountUnits();
            location.addCall(call);
            call.setRace(race);
            call.setCountUnits(call.getRequirementsForCapture() + getCountUnits());
            call.setBelongs(this);
        }
        if (call.getBelongs() == null) {
            countUnits -= call.getCountUnits() + 1;
            location.addCall(call);
            call.setRace(race);
            call.setCountUnits(call.getRequirementsForCapture() + getCountUnits());
            call.setBelongs(this);
        }
    }

    public void pickUpUnits() {
        for (ACall call : location.getCalls()) {
            countUnits += call.getCountUnits() - 1;
        }
    }

    public void makeCoinCount() {
        for (ACall call : location.getCalls()) {
            //if(call.getRace().equals("Elfs"))
            // TODO: Придумать условие получения монеток для эльфов
            countCoin += call.getMoney();
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

    public ListCall getLocation() {
        return location;
    }

    public int getCountUnits() {
        return countUnits;
    }

    public void setRaceDecline() {
        raceDecline = race;
        race = null;
    }
}
