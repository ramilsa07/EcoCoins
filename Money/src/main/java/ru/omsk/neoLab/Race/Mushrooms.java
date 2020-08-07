package ru.omsk.neoLab.Race;


import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.board.Сell.Terrain;

public final class Mushrooms extends ARace {

    public Mushrooms() {
        nameRace = "Mushrooms";
        countTokens = 6;
    }

    @Override
    public int getAdvantageCoin(final Cell cell) {
        return cell.getType().equals(Terrain.MUSHROOMS) ? 2 : cell.getCoin();
    }
}
