package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Dwarfs extends ARace {

    public Dwarfs() {
        nameRace = "Dwarfs";
        countUnit = 5;
    }

    @Override
    public int getMoney(int money, ACall call) {
        return  money;
    }
}
