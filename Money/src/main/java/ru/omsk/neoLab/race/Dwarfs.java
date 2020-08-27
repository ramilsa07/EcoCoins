package ru.omsk.neoLab.race;


import com.fasterxml.jackson.annotation.JsonCreator;
import ru.omsk.neoLab.board.Сell.Cell;

public final class Dwarfs extends ARace {

    @JsonCreator
    public Dwarfs() {
        nameRace = "Dwarfs";
        countTokens = 5;
    }

    @Override
    public int getAdvantageDefendCell(final Cell cell) {
        return cell.getCountTokens() + 1;
    }
}
