package ru.omsk.neoLab.board.Generators;

import ru.omsk.neoLab.board.Generators.Calls.ACall;

public interface IGenerator {
    ACall[] generate(int length);
}
