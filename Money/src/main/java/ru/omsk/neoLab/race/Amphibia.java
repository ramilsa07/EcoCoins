package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Сell.Cell;

public class Amphibia extends ARace {

    public Amphibia() {
        nameRace = "Amphibia";
    }

    @Override
    public boolean isAdvantageOpportunityCaptureCell(final Cell cell) {
        return true;
    }

}
