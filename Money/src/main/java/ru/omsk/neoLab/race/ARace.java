package ru.omsk.neoLab.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.omsk.neoLab.board.Сell.Cell;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class ARace {

    @JsonProperty("nameRace")
    protected String nameRace;
    @JsonProperty("countTokens")
    protected int countTokens;

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

    public final String getNameRace() {
        return nameRace;
    }

    public int getCountTokens() {
        return countTokens;
    }

    public void clearCells(){

    }
}
