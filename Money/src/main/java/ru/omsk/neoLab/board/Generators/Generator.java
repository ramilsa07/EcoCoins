package ru.omsk.neoLab.board.Generators;

import ru.omsk.neoLab.board.Generators.Calls.ACall;
import ru.omsk.neoLab.board.Generators.Calls.Call;

public class Generator implements IGenerator {

    @Override
    public ACall[] generate(int length) {
        ACall[] board = new Call[length];
        for (int i = 0; i < length; i += 4) {
            board[i] = new Call("Земля");
            board[i + 1] = new Call("Грибы");
            board[i + 2] = new Call("Горы");
            board[i + 3] = new Call("Вода");
        }
        return board;
    }
}
