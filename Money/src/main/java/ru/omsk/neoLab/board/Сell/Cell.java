package ru.omsk.neoLab.board.Сell;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.player.Player;

@JsonAutoDetect
public class Cell {

    private int x;
    private int y;

    private TypeCell type;
    private int coin = 1;
    private boolean abilityCapture = true;

    private Player belongs = new Player("Garen");

    private int countTokens = 0;

    public Cell(@JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("type") TypeCell type,
                @JsonProperty("coin") int coin, @JsonProperty("abilityCapture") boolean abilityCapture,
                @JsonProperty("belongs") Player belongs, @JsonProperty("countTokens") int countTokens) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.coin = coin;
        this.abilityCapture = abilityCapture;
        this.belongs = belongs;
        this.countTokens = countTokens;
    }

    public Cell() {

    }

    public Cell(Cell cell) {
        this(cell.getX(), cell.getY(), cell.getType(), cell.getCoin(), cell.isAbilityCapture(),
                cell.getBelongs(), cell.getCountTokens());
    }

    public void regionCapture(final Player player) {
        this.belongs = player;
    }

    @JsonIgnore
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

    public Cell getClone(){
        return new Cell(this);
    }
}
