package ru.omsk.neoLab.board.Generators.Calls.Call;

public class Water extends ACall {

    public Water() {
        type = "Вода";
    }

    @Override
    public boolean getAbilityCapture() {
        return false;
    }

    @Override
    public int getRequirementsForCapture() {
        return 1;
    }
}
