package ru.omsk.neoLab.race;

public class Orcs extends ARace {

    public Orcs() {
        nameRace = "Orcs";
        countUnit = 5;
        alive = true;
    }

    /*@Override
    public int getRequirementsForCapture(ACell call){
        return call.getRequirementsForCapture() - 1;
    }*/
}
