package ru.omsk.neoLab.board.Generators.Cells.Сell;

public final class Mushrooms extends ACell {

    public Mushrooms() {
        type = "Mushrooms";
    }

    @Override
    public int getCoin() {
        return race.getNameRace().equals("Mushrooms") ? 2 : coin;
    }
}
