package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.PlayerService;

import java.util.Random;

public class Answer {

    @JsonIgnore
    protected final Random random = new Random();
    @JsonIgnore
    private final PlayerService service = PlayerService.GetInstance();
    @JsonProperty("board")
    private Board board;
    @JsonProperty("type")
    private Answer type;

    public Answer(final Board board) {
        this.board = board;
    }

    public Cell getRandomRegionToCapture(Board board) {
        Object[] objects = service.possibleCellsCapture.toArray();
        return (Cell) objects[random.nextInt(service.possibleCellsCapture.size())];
    }
}
