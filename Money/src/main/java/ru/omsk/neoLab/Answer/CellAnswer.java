package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.PlayerService;

public class CellAnswer extends Answer {

    @JsonProperty("cell")
    private Cell cell;
    @JsonIgnore
    private final PlayerService service = PlayerService.GetInstance();

    @JsonCreator
    public CellAnswer(@JsonProperty("board") final Board board) {
        super(board);
        Cell[] cells = (Cell[]) service.possibleCellsCapture.toArray();
        cell = cells[random.nextInt(cells.length - 1)];
    }

    public Cell getCell() {
        return cell;
    }
}
