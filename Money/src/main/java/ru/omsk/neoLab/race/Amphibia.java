package ru.omsk.neoLab.race;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.omsk.neoLab.board.Сell.Cell;

public final class Amphibia extends ARace {

    @JsonCreator
    public Amphibia() {
        nameRace = "Amphibia";
        countTokens = 6;
    }

    @Override
    public boolean isAdvantageOpportunityCaptureCell(final Cell cell) {
        return true;
    }
}