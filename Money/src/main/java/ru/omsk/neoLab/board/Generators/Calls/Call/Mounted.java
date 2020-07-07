package ru.omsk.neoLab.board.Generators.Calls.Call;

public class Mounted  extends ACall {

    public Mounted() {
        type = "Горы";
    }

    @Override
    public boolean getAbilityCapture() {
        return true;
    }

    @Override
    public int getRequirementsForCapture() {
        return 3;
    }
}
