package ru.omsk.neoLab.board.Generators;


import ru.omsk.neoLab.board.Сell.Cell;

public interface IGenerator {
    Cell[][] generate(int height, int width);
}
