package ru.omsk.neoLab.answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.race.ARace;


public class RaceAnswer extends Answer {

    @JsonProperty("race")
    private ARace race;

    @JsonCreator
    public RaceAnswer(@JsonProperty("race") final ARace race) {
        super(race);
        this.race = race;
    }

    @Override
    public String toString() {
        return "[race=" + race + ']';
    }
}
