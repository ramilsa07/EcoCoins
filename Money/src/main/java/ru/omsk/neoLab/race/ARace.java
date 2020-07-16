package ru.omsk.neoLab.race;

import lombok.Data;
import ru.omsk.neoLab.board.Сell.ACell;

@Data
public abstract class ARace {

    protected String nameRace;
    protected int countTokens = 6;

    protected boolean alive;

    public int getAdvantageCoin(final ACell cell) {
        return cell.getCoin();
    }

    public int getAdvantageCaptureCell(final ACell cell) {
        return cell.getTokensCapture();
    }

    public int getAdvantageDefendCell(final ACell cell) {
        return cell.getCountTokens();
    }

    public boolean getAdvantageOpportunityCaptureCell(final ACell cell) {
        return cell.isAbilityCapture();
    }

}
