package ru.omsk.neoLab.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;

import java.util.HashSet;

public final class Board implements IBoard {

    @JsonProperty("board")
    private Cell[][] board;
    @JsonProperty("phase")
    private Phases phase;
    @JsonProperty("currentPlayer")
    private Player currentPlayer;

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

    public Cell[][] getBoard() {
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
