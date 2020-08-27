package ru.omsk.neoLab.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.race.ARace;

public class PlayerNeutral extends Player {

    @JsonCreator
    public PlayerNeutral(@JsonProperty("nickname") final String nickname, @JsonProperty("race") final ARace race) {
        super(nickname, race);
    }
}
