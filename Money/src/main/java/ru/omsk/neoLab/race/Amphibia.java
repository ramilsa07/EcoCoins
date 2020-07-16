package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Сell.ACell;

public class Amphibia extends ARace {

    public Amphibia() {
        nameRace = "Amphibia";
        alive = true;
    }

    public boolean getAdvantageOpportunityCaptureCell(final ACell cell) {
        return cell.getType().equals("Water") || cell.isAbilityCapture();
    }

}
