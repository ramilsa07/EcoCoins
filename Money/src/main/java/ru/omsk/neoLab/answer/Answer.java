package ru.omsk.neoLab.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.race.ARace;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Answer {

    @JsonProperty("cell")
    private Cell cell;
    @JsonProperty("race")
    private ARace race;
    @JsonProperty("decline")
    private boolean decline;

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

    public boolean isDecline() {
        return decline;
    }
}
