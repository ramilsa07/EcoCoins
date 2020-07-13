package ru.omsk.neoLab.race;

public class Dwarfs extends ARace {

    public Dwarfs() {
        nameRace = "Dwarfs";
        countUnit = 5;
        alive = true;
    }

    @Override
    public int toDefend(int countUnit) {
        return countUnit + 1;
    }
}
