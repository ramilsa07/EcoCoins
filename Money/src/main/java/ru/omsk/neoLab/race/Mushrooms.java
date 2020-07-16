package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;

public class Mushrooms extends ARace {

    public Mushrooms() {
        nameRace = "Mushrooms";
        countTokens = 6;
    }

    @Override
    public int getAdvantageCoin(final ACell cell) {
        return cell.getType().equals("Mushrooms") ? 2 : cell.getCoin();
    }
}
