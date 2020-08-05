package ru.omsk.neoLab.board;

/*
 * Класс, хранящий доску, на которой будет проходить игра.
 * */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;

import java.util.HashSet;

@JsonAutoDetect
public final class Board implements IBoard {

    @JsonProperty("board")
    private Cell[][] board;
    @JsonProperty("phase")
    private Phases phase;

    private int height;
    private int width;

    @JsonProperty("possibleCells")
    public HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();

    @JsonCreator
    public Board(@JsonProperty("height") final int height, @JsonProperty("width") final int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public Cell[][] generate() {
        IGenerator generator = new Generator();
        board = generator.generate(height, width);
        return board;
    }

    @Override
    public Cell getCell(final int x, final int y) {
        return board[x][y];
    }

    @Override
    public void changePhase(final Phases phase) {
        this.phase = phase;
    }

    public final Cell[][] getBoard() {
        return board;
    }

    public final int getHeight() {
        return height;
    }

    public final int getWidth() {
        return width;
    }

    public Phases getPhase() {
        return phase;
    }
}