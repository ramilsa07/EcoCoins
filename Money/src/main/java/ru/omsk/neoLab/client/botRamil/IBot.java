package ru.omsk.neoLab.client.botRamil;


import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.player.Player;

public interface IBot {
    @FunctionalInterface
    interface IBotFactory {
        IBot createBot(final Player player);
    }

    Answer getAnswer(final Board board);

}
