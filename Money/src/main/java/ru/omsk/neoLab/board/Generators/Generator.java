package ru.omsk.neoLab.board.Generators;


import ru.omsk.neoLab.board.Generators.Calls.Call.*;

public class Generator implements IGenerator {

    @Override
    public ACall[] generate(int length) {
        ACall[] board = new ACall[length];
        for (int i = 0; i < length; i += 4) {
            board[i] = new Earth();
            board[i + 1] = new Mushrooms();
            board[i + 2] = new Mounted();
            board[i + 3] = new Water();
        }
        return board;
    }
}
