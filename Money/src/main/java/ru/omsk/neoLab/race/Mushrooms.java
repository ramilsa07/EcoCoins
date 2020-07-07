package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Mushrooms extends ARace {

    public Mushrooms() {
        nameRace = "Mushrooms";
        countUnit = 6;
    }

    @Override
    public int getMoney(int money, ACall call) {
        if(call.getType().equals("Грибы"))
            return money + 1;
        return money;
    }
}
