package ru.omsk.neoLab.board.Generators.Calls.Call;


public class Earth extends ACall {

    public Earth() {
        type = "Земля";
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
