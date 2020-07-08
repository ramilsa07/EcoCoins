package ru.omsk.neoLab.board.Generators;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

public interface IGenerator {
    ACall[][] generate(int height,int width);
}
