package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Сell.ACell;

import java.util.ArrayList;

public class Elfs extends ARace {

    private ArrayList<String> cells = new ArrayList<String>();

    public Elfs() {
        nameRace = "Elfs";
        countTokens = 6;
    }

    @Override
    public int getAdvantageCoin(final ACell cell) {
        if (!cells.contains(cell.getType())) {
            cells.add(cell.getType());
            return cell.getCoin() + 1;
        }
        return cell.getCoin();
    }
}
