package ru.omsk.neoLab.ServerClient.aibot;

import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.answer.CellAnswer;
import ru.omsk.neoLab.answer.DeclineAnswer;
import ru.omsk.neoLab.answer.RaceAnswer;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.player.Player;

public class SimpleBot implements IBot {
//    @Override
//    public Answer getAnswer(Board board) {
//        return null;
//    }

    @Override
    public Answer getAnswer(Board board) {
        return null;
    }

//    @Override
//    public Player getBotPlayer() {
//        return null;
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }

//    @Override
//    public Answer getAnswer(Board board) {
//        switch (board.getPhase()) {
//            case RACE_CHOICE:
//                return new RaceAnswer(findingBestRace(board));
//            case GO_INTO_DECLINE:
//                return new DeclineAnswer(isFindDecline(board));
//            case CAPTURE_OF_REGIONS:
//                return new CellAnswer(findingBestPath(board));
//            case SHUFFLING_TOKENS:
//                return new CellAnswer(findingBestShufflingTokens(board));
//            default:
//                throw new IllegalStateException("Unexpected value: " + board.getPhase());
//        }
//    }
}
