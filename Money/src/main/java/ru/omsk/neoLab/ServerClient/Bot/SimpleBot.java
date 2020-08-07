package ru.omsk.neoLab.ServerClient.Bot;

import ru.omsk.neoLab.Answer.Answer;
import ru.omsk.neoLab.board.Board;

public class SimpleBot implements IBot{

    @Override
    public Answer getAnswer(Board board) {
        final Answer answer = new Answer(board);
        return answer.selectingResponse();
    }
}
