package ru.omsk.neoLab.board.Сell;

import ru.omsk.neoLab.player.Player;


public final class Cell {

    private int x;
    private int y;

    private TypeCell type;
    private final int coin = 1;
    private boolean abilityCapture = true;

    //Информация по принадлежности к игроку
    private Player belongs = new Player("Gore");

    private int countTokens = 0;

    public final void regionCapture(final Player player) {
        this.belongs = player;
    }

    /**
     * Метод возвращает количество токенов требуемых для захвата ячейки определенного типа
     * Горы - 3 токена, вода - 1 токен, земля и грибы - 2 токена
     * @return
     */
    public final int getTokensForCapture() {
        if (type.equals(TypeCell.Mounted)) {
            return 3;
        } else if (type.equals(TypeCell.Water)) {
            return 1;
        } else
            return 2;
    }

    public final int getToken(final int countTokens) {
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

    public final int getX() {
        return x;
    }

    public final void setX(final int x) {
        this.x = x;
    }

    public final int getY() {
        return y;
    }

    public final void setY(final int y) {
        this.y = y;
    }

    public final TypeCell getType() {
        return type;
    }

    public final void setType(final TypeCell type) {
        this.type = type;
    }

    public final int getCoin() {
        return coin;
    }

    public final boolean isAbilityCapture() {
        return abilityCapture;
    }

    public final void setAbilityCapture(final boolean abilityCapture) {
        this.abilityCapture = abilityCapture;
    }

    public final Player getBelongs() {
        return belongs;
    }

    public final int getCountTokens() {
        return countTokens;
    }

    public final void setCountTokens(final int countTokens) {
        this.countTokens = countTokens;
    }
}
