package ru.omsk.neoLab.Race;

import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.board.Сell.Terrain;

import java.util.ArrayList;

public final class Elfs extends ARace {

    private final ArrayList<Terrain> cells = new ArrayList<Terrain>();

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

    @Override
    public void clearCells(){
        cells.clear();
    }
}
