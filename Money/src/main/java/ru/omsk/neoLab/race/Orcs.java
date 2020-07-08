package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public class Orcs extends ARace {

    public Orcs() {
        nameRace = "Orcs";
        countUnit = 5;
        alive = true;
    }

    @Override
    public int getRequirementsForCapture(ACall call){
        return call.getRequirementsForCapture() - 1;
    }
}
