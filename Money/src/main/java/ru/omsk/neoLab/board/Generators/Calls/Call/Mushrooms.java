package ru.omsk.neoLab.board.Generators.Calls.Call;

public class Mushrooms extends ACall {

    public Mushrooms() {
        type = "Mushrooms";
    }

    @Override
    public int getMoney() {
        if (race.getNameRace().equals("Mushrooms"))
            return 2;
        return 1;
    }
}
