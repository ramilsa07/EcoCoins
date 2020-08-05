package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.omsk.neoLab.board.Board;

import java.util.Random;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Answer {

    @JsonIgnore
    protected final Random random = new Random();
    @JsonProperty("board")
    private Board board;
    @JsonProperty("board")
    private Answer type;

    public Answer(@JsonProperty("board") final Board board) {
        this.board = board;
    }

    public Answer takeAnswer() {
        switch (board.getPhase()) {
            case RACE_CHOICE:
                return new RaceAnswer(board);
            case CAPTURE_OF_REGIONS:
                return new RaceAnswer(board);
            case PICK_UP_TOKENS:
                return new RaceAnswer(board);
            default:
                throw new IllegalStateException("Unexpected value: " + board);
        }
    }
}
