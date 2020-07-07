package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Orcs extends ARace {

    public Orcs() {
        nameRace = "Orcs";
        countUnit = 5;
    }


    @Override
    public int getMoney(int money, ACall call) {
        return money;
    }
}
