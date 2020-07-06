package ru.omsk.neoLab.race;

public class Mushrooms extends ARace {

    public Mushrooms() {
        setNameRace("Mushrooms");
        setQuantity(6);
    }

    // каждый захваченный регион с грибами приносит в конце хода призовую монетку
    @Override
    public void receiveAdvantage() {

    }
}
