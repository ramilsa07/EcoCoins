package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Elfs extends ARace {

    public Elfs() {
        nameRace = "Elfs";
        countUnit = 6;
        alive = true;
    }

    @Override
    public int getMoney(int money, ACall call) {
        return money;
    }
}
