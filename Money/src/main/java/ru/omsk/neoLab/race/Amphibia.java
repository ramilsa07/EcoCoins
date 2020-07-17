package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Сell.Cell;

public class Amphibia extends ARace {

    public Amphibia() {
        nameRace = "Amphibia";
    }

    public boolean getAdvantageOpportunityCaptureCell(final Cell cell) {
        return cell.getType().equals("Water") || cell.isAbilityCapture();
    }

}
