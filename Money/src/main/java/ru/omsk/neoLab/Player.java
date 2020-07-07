package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Generators.Calls.ListCall;
import ru.omsk.neoLab.race.RaceContainer;

public class Player {
    private String name;
    private int money;
    private RaceContainer race;
    private ListCall location;

    public Player(String name) {
        this.name = name;
        money = 0;
    }
    
}
