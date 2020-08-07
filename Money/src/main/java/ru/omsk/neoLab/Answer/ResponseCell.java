package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.board.Сell.Terrain;

import java.util.ArrayList;

public class ResponseCell extends Answer{

    @JsonProperty("cell")
    private Cell cell = new Cell();
    @JsonIgnore
    private ArrayList<Cell> cells;

    @JsonCreator
    public ResponseCell(@JsonProperty("board") Board board) {
        super(board);
        cell.setType(Terrain.MUSHROOMS);
        changeCell();
    }

    public Cell getCell(){
        return cell;
    }

    private void changeCell(){
        cell.setType(Terrain.MUSHROOMS);
    }


}
