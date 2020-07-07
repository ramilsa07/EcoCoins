package ru.omsk.neoLab.board.Generators.Calls.Call;

public class Mushrooms extends ACall {

    public Mushrooms() {
        type = "Грибы";
    }

    @Override
    public boolean getAbilityCapture() {
        return true;
    }

    @Override
    public int getRequirementsForCapture() {
        return 2;
    }
}
