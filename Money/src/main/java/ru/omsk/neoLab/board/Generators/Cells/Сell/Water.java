package ru.omsk.neoLab.board.Generators.Cells.Сell;

import ru.omsk.neoLab.race.ARace;

public class Water extends ACell {

    public Water() {
        type = "Water";
        abilityCapture = false;
    }

    @Override
    public boolean getAbilityCapture(ARace race) {
        return race.getNameRace().equals("Amphibia") || abilityCapture;
    }
}
