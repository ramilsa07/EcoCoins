package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Orcs extends ARace {

    public Orcs() {
        nameRace = "Orcs";
        countUnit = 5;
    }

    @Override
    public int getRequirementsForCapture(ACall call){
        return call.getRequirementsForCapture() - 1;
    }
}
