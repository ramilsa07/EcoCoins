package ru.omsk.neoLab.board.Generators;

import ru.omsk.neoLab.board.Generators.Cells.Сell.Cell;

public interface IGenerator {
    Cell[][] generate(int height, int width);
}
