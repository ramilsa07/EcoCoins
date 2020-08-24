package ru.omsk.neoLab.client.botevgekii;

import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.board.Board;

public interface IBot {

    Answer getAnswer(final Board board);

}
