package ru.omsk.neoLab.ServerClient.aibot;


import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.board.Board;

interface IBot {

    Answer getAnswer(final Board board);

}
