package ru.omsk.neoLab.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.race.ARace;

import java.awt.*;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Answer {

    @JsonProperty("cell")
    private Cell cell;
    @JsonProperty("cells")
    private List<Point> cells;
    @JsonProperty("race")
    private ARace race;
    @JsonProperty("decline")
    private boolean decline;

    public Answer(@JsonProperty("cells") final List<Point> cells) {
        this.cells = cells;
    }

    public Answer(@JsonProperty("cell") final Cell cell) {
        this.cell = cell;
    }

    public Answer(@JsonProperty("race") final ARace race) {
        this.race = race;
    }

    public Answer(boolean decline) {
        this.decline = decline;
    }

    public Cell getCell() {
        return cell;
    }

    public ARace getRace() {
        return race;
    }

    public List<Point> getCells() {
        return cells;
    }

    public boolean isDecline() {
        return decline;
    }
}
