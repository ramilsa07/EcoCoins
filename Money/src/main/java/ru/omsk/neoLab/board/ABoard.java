package ru.omsk.neoLab.board;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;

public class ABoard {

    protected ACall[][] board;

    protected void generateBoard(int height, int width) {
        IGenerator generator = new Generator();
        board = generator.generate(height, width);
    }

    public ACall[][] getBoard() {
        return board;
    }
}
