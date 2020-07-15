package ru.omsk.neoLab.board.Generators.Cells.Сell;

import ru.omsk.neoLab.Player;
import ru.omsk.neoLab.race.ARace;

/*
* Абстрактный класс ACell описывает поля и свойства ячеек на доске.
* От него образованы классы Earth, Mounted, Mushrooms, Water.
* Пристутствуют поля, которые описывают ячейку(х и у - расположение на доске, тип ячейки,
* число монеток, получаемых за территорию, число жетонов, нужных для захвата и сама возможность захвата)
* А также поля отвечающие за игрока, который стоит на ячейке(Ссылку на игрока, число его жетонов на ячейке,
* за какую расу играет)
* */

public abstract class ACell {

    protected int x;
    protected int y;

    protected String type;
    protected int coin = 1;
    protected int tokensCapture = 2;
    protected boolean abilityCapture = true;

    //Информация по принадлежности к игроку
    protected Player belongs;
    protected int countTokens;
    protected ARace race;

    public String getType() {
        return type;
    }

    public int getCoin() {
        return coin;
    }

    public int getTokensCapture() {
        return tokensCapture;
    }

    public boolean getAbilityCapture(ARace race) {
        return abilityCapture;
    }

    //TODO: Сделать ниже модули более логичными и убрать повторения

    public Player getBelongs() {
        return belongs;
    }

    public int getCountTokens() {
        return countTokens;
    }

    public ARace getRace() {
        return race;
    }

    public void setBelongs(Player belongs) {
        this.belongs = belongs;
    }

    public void setCountTokens(int countTokens) {
        this.countTokens = countTokens;
    }

    public void setRace(ARace race) {
        this.race = race;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
