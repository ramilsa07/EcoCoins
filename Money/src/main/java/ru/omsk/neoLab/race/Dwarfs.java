package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Сell.ACell;

public class Dwarfs extends ARace {

    public Dwarfs() {
        nameRace = "Dwarfs";
        countTokens = 5;
    }

    @Override
    public int getAdvantageDefendCell(final ACell cell) {
        return cell.getCountTokens() + 1;
    }
}
