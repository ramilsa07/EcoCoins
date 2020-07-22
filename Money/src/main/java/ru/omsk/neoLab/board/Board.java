package ru.omsk.neoLab.board;


/*
 * Класс, хранящий доску, на которой будет проходить игра.
 * */

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Сell.Cell;

public class Board {

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

    public Cell[][] getBoard() {
        return board;
    }

    public Cell getBoardElements(int i, int j){
        return board[i][j];
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
