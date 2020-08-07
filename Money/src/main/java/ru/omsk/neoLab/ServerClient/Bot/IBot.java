package ru.omsk.neoLab.ServerClient.Bot;


import ru.omsk.neoLab.Answer.Answer;
import ru.omsk.neoLab.board.Board;

interface IBot {

    Answer getAnswer(final Board board);

}
