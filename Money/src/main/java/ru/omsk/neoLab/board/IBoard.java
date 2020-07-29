package ru.omsk.neoLab.board;

import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;

public interface IBoard {

    Cell[][] generate();

    Cell getCell(int x, int y);

    void changePhase(Phases phase);
}
