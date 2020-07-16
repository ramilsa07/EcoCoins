package ru.omsk.neoLab.board.Generators.Cells.Сell;

import lombok.Data;
import ru.omsk.neoLab.Player;
import ru.omsk.neoLab.race.ARace;

@Data
public abstract class ACell {

    protected int x;
    protected int y;

    protected String type;
    protected int coin = 1;
    protected int tokensCapture = 2;
    protected boolean abilityCapture = true;

    //Информация по принадлежности к игроку
    protected Player belongs = null;

    protected int countTokens = 0;
    protected ARace race;

    public void regionCapture(final Player player) {
        this.race = player.getRace();
        this.
    }

    public void putToken(final int countTokens) {
        this.countTokens += countTokens;
    }

    public int getToken(final int countTokens) {
        this.countTokens -= countTokens;
        return countTokens;
    }

}
