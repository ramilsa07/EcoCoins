package ru.omsk.neoLab.answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Сell.Cell;

public class CellAnswer extends Answer {

    @JsonProperty("cell")
    private final Cell cell;

    @JsonCreator
    public CellAnswer(@JsonProperty("cell") final Cell cell) {
        super(cell);
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "[cell=" + cell + ']';
    }
}
