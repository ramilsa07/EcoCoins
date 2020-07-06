package ru.omsk.neoLab.race;

public class Undead extends ARace {

    public Undead() {
        setNameRace("Undead");
        setQuantity(11);
    }

    @Override
    public void receiveAdvantage() {
        // особенность - число юнитов. Тут просто заглушка
    }

}
