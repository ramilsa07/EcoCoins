package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public abstract class ARace {
    protected String nameRace;
    protected int countUnit; // Число юнитов определенной рассы

    public String getNameRace() {
        return nameRace;
    }

    public int getCountUnit() {
        return countUnit;
    }

    public abstract int getMoney(int money, ACall call);
}
