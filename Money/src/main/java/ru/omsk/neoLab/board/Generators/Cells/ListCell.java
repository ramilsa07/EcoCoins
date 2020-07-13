package ru.omsk.neoLab.board.Generators.Cells;

import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;

import java.util.ArrayList;

public class ListCell {
    private ArrayList<ACell> cells;


    public ListCell() {
        cells = new ArrayList<ACell>();
    }

    public void add(ACell cell) {
        cells.add(cell);
    }

    public void remove(ACell cell) {
        cells.remove(cell);
    }

    public ArrayList<ACell> getCells() {
        return cells;
    }

    public boolean isEmpty() {
        return cells.isEmpty();
    }
}
