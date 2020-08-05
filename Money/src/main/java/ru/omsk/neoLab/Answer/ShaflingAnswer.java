package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;

public class ShaflingAnswer extends Answer {

    @JsonProperty("cell")
    private Cell cell;

    @JsonCreator
    public ShaflingAnswer(@JsonProperty("board") final Board board) {
        super(board);
    }

    public Cell getCell() {
        return cell;
    }
}
