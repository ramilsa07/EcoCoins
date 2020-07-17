package ru.omsk.neoLab.board.Сell;

import lombok.Data;
import ru.omsk.neoLab.Player;

@Data
public class Cell {

    private int x;
    private int y;

    private TypeCell type;
    private int coin;
    private int tokensForCapture = 2;
    private boolean abilityCapture = true;

    //Информация по принадлежности к игроку
    private Player belongs = new Player("Gore");

    private int countTokens = 0;

    public void regionCapture(final Player player) {
        this.belongs = player;
        this.countTokens = 0;
    }



    /*public int getTokensForCapture(){
        if(type.equals(TypeCell.Mounted)){
            return 3;
        }
        else if(type.equals(TypeCell.Water)){
            return 1;
        }
        else
            return tokensForCapture;
    }*/

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
}
