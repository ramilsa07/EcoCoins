package ru.omsk.neoLab.board;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;

public class Board {

    private static volatile Board instance;
    protected ACall[][] board;

    private Board() {
    }

    public static synchronized Board GetInstance() {
        if (instance == null) {
            instance = new Board();
            instance.generateBoard(4, 3);
        }
        return instance;
    }

    private void generateBoard(int height, int width) {
        IGenerator generator = new Generator();
        board = generator.generate(height, width);
    }

    public ACall[][] getBoard() {
        return board;
    }

    public ACall getCall(int x, int y){
        return board[x][y];
    }
}
