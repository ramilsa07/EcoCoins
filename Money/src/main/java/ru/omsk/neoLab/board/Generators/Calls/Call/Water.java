package ru.omsk.neoLab.board.Generators.Calls.Call;

import ru.omsk.neoLab.race.ARace;

public class Water extends ACall {

    public Water() {
        type = "Water";
    }

    @Override
    public boolean isAbilityCapture(ARace race) {
        return race.getNameRace().equals("Amphibia");
    }

    @Override
    public int getRequirementsForCapture() {
        return 1;
    }
}
