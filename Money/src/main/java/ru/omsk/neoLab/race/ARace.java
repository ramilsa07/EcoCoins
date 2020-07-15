package ru.omsk.neoLab.race;

/*
 * Абстрактный класс ARace описывает поля и свойства игровых рас.
 * От него образованы классы Amphibia, Dwarfs, Elfs, Mushrooms, Orcs, Undead.
 * Поля класса хранят имя расы, количество жетонов, которое выдается игроку когда он начинает игру за эту расу
 * и флаг отвечающий за жизнеспособность расы(либо она в упадке)
 * */

public abstract class ARace {
    protected String nameRace;
    protected int countTokens = 6;
    protected boolean alive;

    public String getNameRace() {
        return nameRace;
    }

    public int getCountTokens() {
        return countTokens;
    }

    //TODO:Подумуть над(Оставить только getter'ы)

    public int toDefend(int countUnit) {
        return countUnit;
    } // Сколько нужно для защиты

    public void toDecline() {
        alive = false;
    }
}
