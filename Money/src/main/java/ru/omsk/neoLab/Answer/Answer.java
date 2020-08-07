package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.omsk.neoLab.board.Board;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Answer {

    @JsonProperty("board")
    private Board board;

    @JsonCreator
    public Answer(@JsonProperty("board") final Board board){
        this.board = board;
    }

    public Answer selectingResponse(){
        switch (board.getPhase()){
            case RACE_CHOICE: return new ResponseRace(board);
            case CAPTURE_OF_REGIONS: return new ResponseCell(board);
            default: throw new IllegalStateException("Unexpected value: " + board.getPhase());
        }
    }
}
