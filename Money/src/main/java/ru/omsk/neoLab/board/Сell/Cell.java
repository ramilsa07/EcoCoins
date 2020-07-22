package ru.omsk.neoLab.board.Сell;

import ru.omsk.neoLab.player.Player;

public class Cell {

    private int x;
    private int y;

    private TypeCell type;
    private int coin = 1;
    private boolean abilityCapture = true;

    private Player belongs = new Player("Gore");

    private int countTokens = 0;

    public void regionCapture(final Player player) {
        this.belongs = player;
    }

    public int getTokensForCapture() {
        return TypeCell.toType(type);
    }

    public void putToken(final int countTokens) {
        this.countTokens += countTokens;
    }

    public int getToken(final int countTokens) {
        this.countTokens -= countTokens;
        return countTokens;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
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

    public TypeCell getType() {
        return type;
    }

    public void setType(TypeCell type) {
        this.type = type;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public boolean isAbilityCapture() {
        return abilityCapture;
    }

    public void setAbilityCapture(boolean abilityCapture) {
        this.abilityCapture = abilityCapture;
    }

    public Player getBelongs() {
        return belongs;
    }

    public void setBelongs(Player belongs) {
        this.belongs = belongs;
    }

    public int getCountTokens() {
        return countTokens;
    }

    public void setCountTokens(int countTokens) {
        this.countTokens = countTokens;
    }
}
