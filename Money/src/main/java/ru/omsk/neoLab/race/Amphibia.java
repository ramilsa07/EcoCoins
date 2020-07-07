package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Amphibia extends ARace {

    public Amphibia() {
        nameRace = "Amphibia";
        countUnit = 6;
    }

    @Override
    public int getMoney(int money, ACall call) {
          return money;
    }
}
