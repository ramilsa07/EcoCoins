package ru.omsk.neoLab.board.Generators.Calls.Call;

public class Mounted  extends ACall {

    public Mounted() {
        type = "Mounted";
    }

    @Override
    public int getRequirementsForCapture() {
        return 3;
    }
}
