package ru.omsk.neoLab.board.Generators;

import ru.omsk.neoLab.board.Сell.ACell;

public interface IGenerator {
    ACell[][] generate(int height, int width);
}
