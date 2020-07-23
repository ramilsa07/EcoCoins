package ru.omsk.neoLab.board;

/*
 * Класс, хранящий доску, на которой будет проходить игра.
 * */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Сell.Cell;

@JsonAutoDetect
public final class Board {

    private static Board instance;

    @JsonProperty
    private Cell[][] board;

    private int height;
    private int width;

    private Board() {
    }

    public static Board GetInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public final Cell[][] getBoard() {
        return board;
    }

    public final Cell getBoardElements(int i, int j){
        return board[i][j];
    }

    public final void setBoard(Cell[][] board) {
        this.board = board;
    }

    public final int getHeight() {
        return height;
    }

    public final void setHeight(int height) {
        this.height = height;
    }

    public final int getWidth() {
        return width;
    }

    public final void setWidth(int width) {
        this.width = width;
    }
}
