package ru.omsk.neoLab.board.Сell;

import com.fasterxml.jackson.annotation.*;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerNeutral;
import ru.omsk.neoLab.race.Undead;

@JsonAutoDetect
public class Cell {

    private int x;
    private int y;
    @JsonProperty("type")
    private Terrain type;
    @JsonIgnore
    private int coin = 1;
    @JsonIgnore
    private boolean abilityCapture = true;
    @JsonIgnoreProperties("locationCell")
    private Player belongs = new PlayerNeutral("Neutral", new Undead());
    @JsonProperty("countTokens")
    private int countTokens = 0;

    @JsonCreator
    public Cell(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    @JsonIgnore
    public Cell() {

    }

    public void regionCapture(final Player player) {
        this.belongs = player;
    }

    @JsonIgnore
    public int getTokensForCapture() {
        return Terrain.toType(type);
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

    public Terrain getType() {
        return type;
    }

    public void setType(Terrain type) {
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
