package ru.omsk.neoLab.selfplay;

import org.junit.Test;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.IBoard;

public class SelfPlayTest {


    @Test
    public void Game() {

        SelfPlay selfPlay = new SelfPlay();
        IBoard board = new Board(4, 3);
    }

}