package ru.omsk.neoLab.Race;

import ru.omsk.neoLab.board.Сell.Cell;

public final class Amphibia extends ARace {

    public Amphibia() {
        nameRace = "Amphibia";
        countTokens = 6;
    }

    @Override
    public boolean isAdvantageOpportunityCaptureCell(final Cell cell) {
        return true;
    }
}