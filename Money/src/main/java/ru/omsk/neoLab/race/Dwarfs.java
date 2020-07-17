package ru.omsk.neoLab.race;


import ru.omsk.neoLab.board.Сell.Cell;

public class Dwarfs extends ARace {

    public Dwarfs() {
        nameRace = "Dwarfs";
        countTokens = 5;
    }

    @Override
    public int getAdvantageDefendCell(final Cell cell) {
        return cell.getCountTokens() + 1;
    }
}
