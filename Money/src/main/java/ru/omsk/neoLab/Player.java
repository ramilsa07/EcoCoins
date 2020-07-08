package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;
import ru.omsk.neoLab.board.Generators.Calls.ListCall;
import ru.omsk.neoLab.race.ARace;
import ru.omsk.neoLab.race.RaceContainer;

public class Player {
    private final String name;
    private int money;
    private RaceContainer race; // Набор рас, которые в игре у данного игрока
    private ARace raceThisTurn; // Раса в данный ход
    private ListCall location;
    private int countUnits;

    public Player(String name) {
        this.name = name;
        money = 0;
        countUnits = 0;
    }

    public void countMoney(){
//        boolean countEarth = true;
//        boolean countMushrooms = true;
//        boolean countMounted = true;
        for(ACall call: location.getCalls()) {
            // if(call.getRace().equals("Elfs") && call.getType())
            // TODO: Придумать условия получения монеток для эльфов
            money += call.getMoney();
        }
    }

    public void setRace(ARace thisRace){
        raceThisTurn = thisRace;
        countUnits += thisRace.getCountUnit();
        race.addRace(thisRace);
    }

    public void addTerritory(ACall call){
        location.addCall(call);
        countUnits -= call.getRequirementsForCapture();
        if(call.getBelongs() != this){
            countUnits -= call.getCountUnits() + 1;
        }
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public RaceContainer getRace() {
        return race;
    }

    public ARace getRaceThisTurn() {
        return raceThisTurn;
    }

    public ListCall getLocation() {
        return location;
    }

    public int getCountUnits() {
        return countUnits;
    }

    public void PickUpUnits(int countUnits) {
        this.countUnits += countUnits;
    }
}
