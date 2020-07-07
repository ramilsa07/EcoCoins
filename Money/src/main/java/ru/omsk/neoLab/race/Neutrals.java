package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Neutrals extends ARace {

    public Neutrals(int countUnit) {
        nameRace = "Neutrals";
        this.countUnit = countUnit;
    }

    @Override
    public int getMoney(int money, ACall call) {
        return money;
    }
}
