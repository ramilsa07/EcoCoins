package ru.omsk.neoLab.board;

import ru.omsk.neoLab.board.Generators.Cells.Сell.Cell;

/*
 * Класс, хранящий доску, на которой будет проходить игра.
 * */

public class Board {

    private static Board instance;

    protected Cell[][] board;

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
