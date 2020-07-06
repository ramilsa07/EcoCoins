package ru.omsk.neoLab.race;

public abstract class ARace {
    private String nameRace;
    private int quantity; // Число юнитов определенной рассы

    public ARace(String nameRace, int quantity) {
        this.nameRace = nameRace;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNameRace() {
        return nameRace;
    }

    public abstract void receiveAdvantage();
}
