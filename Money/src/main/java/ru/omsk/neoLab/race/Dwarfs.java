package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Dwarfs extends ARace {

    public Dwarfs() {
        nameRace = "Dwarfs";
        countUnit = 5;
    }

    @Override
    public int toDefend(int countUnit) {
        return countUnit + 1;
    }
}
