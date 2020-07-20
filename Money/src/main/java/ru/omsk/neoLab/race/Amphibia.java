package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.board.Сell.TypeCell;

public class Amphibia extends ARace {

    public Amphibia() {
        nameRace = "Amphibia";
    }

    public boolean getAdvantageOpportunityCaptureCell(final Cell cell) {
        return cell.getType() == TypeCell.Water || cell.isAbilityCapture();
    }

}
