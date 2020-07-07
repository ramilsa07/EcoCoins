package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Undead extends ARace {

    public Undead() {
        nameRace = "Undead";
        countUnit = 11;
    }

    @Override
    public int getMoney(int money, ACall call) {
        return money;
    }
}
