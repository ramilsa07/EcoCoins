package ru.omsk.neoLab.race;

public abstract class ARace {
    protected String nameRace;
    protected int countUnit = 6;
    protected boolean alive;

    public String getNameRace() {
        return nameRace;
    }

    public int getCountTokens() {
        return countUnit;
    }

    //TODO:Подумуть над(Оставить только getter'ы)

    public int toDefend(int countUnit) {
        return countUnit;
    } // Сколько нужно для защиты

    public void toDecline() {
        alive = false;
    }
}
