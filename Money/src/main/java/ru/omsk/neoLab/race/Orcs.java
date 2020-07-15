package ru.omsk.neoLab.race;

public final class Orcs extends ARace {

    public Orcs() {
        nameRace = "Orcs";
        countTokens = 5;
        alive = true;
    }

    /*@Override
    public int getRequirementsForCapture(ACell call){
        return call.getRequirementsForCapture() - 1;
    }*/
}
