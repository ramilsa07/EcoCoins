package ru.omsk.neoLab.race;

import com.fasterxml.jackson.annotation.JsonCreator;

public final class Undead extends ARace {
    @JsonCreator
    public Undead() {
        nameRace = "Undead";
        countTokens = 11;
    }
}
