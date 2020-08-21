package ru.omsk.neoLab.answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Сell.Cell;

import java.awt.*;
import java.util.List;

public class CellAnswer extends Answer {

//    @JsonProperty("cell")
//    private final Cell cell;
//
//    @JsonCreator
//    public CellAnswer(@JsonProperty("cell") final Cell cell) {
//        super(cell);
//        this.cell = cell;
//    }
//
//    @Override
//    public String toString() {
//        return "[cell=" + cell + ']';
//    }
    @JsonProperty("cells")
    private final List<Point> cells;

    public CellAnswer(@JsonProperty("cells") final List<Point> cells) {
        super(cells);
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "CellAnswer{" +
                "cells=" + cells +
                '}';
    }
}
