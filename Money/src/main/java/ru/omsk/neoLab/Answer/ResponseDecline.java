package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDecline extends Answer {

    @JsonProperty("decline")
    private boolean decline;

    @JsonCreator
    public ResponseDecline(@JsonProperty("decline") boolean decline) {
        this.decline = decline;
    }

    public boolean isDecline() {
        return decline;
    }
}
