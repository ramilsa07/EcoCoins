package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.Race.ARace;
import ru.omsk.neoLab.Race.Amphibia;

public class ResponseRace extends Answer {

    @JsonProperty("race")
    private ARace race;

    @JsonCreator
    public ResponseRace(@JsonProperty("board") Board board) {
        super(board);
        race = new Amphibia();
    }

    public ARace getRace(){
        return race;
    }
}
