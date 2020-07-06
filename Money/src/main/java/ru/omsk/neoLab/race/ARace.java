package ru.omsk.neoLab.race;

public abstract class ARace {
    private String nameRace;
    private int quantity; // Число юнитов определенной рассы

    public ARace() {
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNameRace() {
        return nameRace;
    }

    public void setNameRace(String nameRace) {
        this.nameRace = nameRace;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public abstract void receiveAdvantage();
}
