package ru.omsk.neoLab.race;

import lombok.Data;
import ru.omsk.neoLab.board.Сell.Cell;

@Data
public abstract class ARace {

    protected String nameRace;
    protected int countTokens = 6;

    public int getAdvantageCoin(final Cell cell) {
        return cell.getCoin();
    }

    public int getAdvantageCaptureCell(final Cell cell) {
        return cell.getTokensForCapture();
    }

    public int getAdvantageDefendCell(final Cell cell) {
        return cell.getCountTokens();
    }

    public boolean isAdvantageOpportunityCaptureCell(final Cell cell) {
        return cell.isAbilityCapture();
    }

}
