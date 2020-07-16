package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Cells.Сell.Cell;

public class Orcs extends ARace {

    public Orcs() {
        nameRace = "Orcs";
        countTokens = 5;
    }

    @Override
    public int getAdvantageCaptureCell(final Cell cell) {
        return cell.getTokensForCapture() - 1;
    }
}
