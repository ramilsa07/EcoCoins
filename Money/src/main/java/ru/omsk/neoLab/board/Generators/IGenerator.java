package ru.omsk.neoLab.board.Generators;

import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;

public interface IGenerator {
    ACell[][] generate(int height, int width);
}
