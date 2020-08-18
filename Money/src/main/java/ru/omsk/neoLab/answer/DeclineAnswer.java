package ru.omsk.neoLab.answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeclineAnswer extends Answer {
    @JsonProperty("decline")
    private boolean decline;

    @JsonCreator
    public DeclineAnswer(boolean decline) {
        super(decline);
        this.decline = decline;
    }

    @Override
    public String toString() {
        return "[decline=" + decline + ']';
    }
}
