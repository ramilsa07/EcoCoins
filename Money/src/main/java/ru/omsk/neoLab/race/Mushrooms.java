package ru.omsk.neoLab.race;


import ru.omsk.neoLab.board.Generators.Cells.Сell.Cell;
import ru.omsk.neoLab.board.Generators.Cells.Сell.TypeCell;

public class Mushrooms extends ARace {

    public Mushrooms() {
        nameRace = "Mushrooms";
        countTokens = 6;
    }

    @Override
    public int getAdvantageCoin(final Cell cell) {
        return cell.getType().equals(TypeCell.Mushrooms) ? 2 : cell.getCoin();
    }
}
