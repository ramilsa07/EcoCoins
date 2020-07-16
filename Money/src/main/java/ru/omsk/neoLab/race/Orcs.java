package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Сell.ACell;

public class Orcs extends ARace {

    public Orcs() {
        nameRace = "Orcs";
        countTokens = 5;
    }

    @Override
    public int getAdvantageCaptureCell(final ACell cell) {
        return cell.getTokensCapture() - 1;
    }
}
