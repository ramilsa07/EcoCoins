package ru.omsk.neoLab.board.Generators.Cells.Сell;

public class Water extends ACell {

    public Water() {
        type = "Water";
        abilityCapture = false;
    }

    @Override
    public boolean getAbilityCapture() {
        return race.getNameRace().equals("Amphibia") || abilityCapture;
    }
}
