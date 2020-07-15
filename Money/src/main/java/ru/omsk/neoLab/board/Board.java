package ru.omsk.neoLab.board;

import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;

/*
 * Класс, хранящий доску, на которой будет проходить игра.
 * */

public class Board {

    private static volatile Board instance;
    protected ACell[][] board;

    private static int height;
    private static int width;

    private Board() {
    }

    public static synchronized Board GetInstance() {
        if (instance == null) {
            instance = new Board();
            height = 4;
            width = 3;
            instance.generateBoard();
        }
        return instance;
    }

    private void generateBoard() {
        IGenerator generator = new Generator();
        board = generator.generate(height, width);
    }

    public ACell[][] getBoard() {
        return board;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
