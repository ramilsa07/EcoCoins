package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Generators.Cells.Сell.Cell;
import ru.omsk.neoLab.board.Generators.Cells.Сell.TypeCell;

import java.util.ArrayList;

public class Elfs extends ARace {

    private ArrayList<TypeCell> cells = new ArrayList<TypeCell>();

    public Elfs() {
        nameRace = "Elfs";
        countTokens = 6;
    }

    @Override
    public int getAdvantageCoin(final Cell cell) {
        if (!cells.contains(cell.getType())) {
            cells.add(cell.getType());
            return cell.getCoin() + 1;
        }
        return cell.getCoin();
    }
}
