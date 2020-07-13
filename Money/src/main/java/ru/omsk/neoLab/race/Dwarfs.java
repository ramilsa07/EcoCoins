package ru.omsk.neoLab.race;

public class Dwarfs extends ARace {

    public Dwarfs() {
        nameRace = "Dwarfs";
        countTokens = 5;
    }

    @Override
    public int toDefend(int countUnit) {
        return countUnit + 1;
    }
}
