package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Generators.Calls.ListCall;
import ru.omsk.neoLab.race.ARace;
import ru.omsk.neoLab.race.RaceContainer;

public class Player {
    private String name;
    private int money;
    private RaceContainer race; // Набор рас, которые в игре у данного игрока
    private ARace raceThisTurn; // Раса в данный ход
    private ListCall location;
    private int countUnits;

    public Player(String name) {
        this.name = name;
        money = 0;
    }

    public int countMoney(){
      //  for(int i)
        return 0;
    }
}
