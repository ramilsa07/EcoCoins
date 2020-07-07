package ru.omsk.neoLab.board;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;

public class Board implements IBoard {

    private int length;

    private ACall[] board;
    private int[] locationUnit;
    private int[] accessoryTerritory;
    private int[] countsUnit;

    public Board(int height, int width) {
        this.length = height * width;
    }

    @Override
    public void generateBoard() {
        IGenerator generator = new Generator();
        board = generator.generate(length);

        locationUnit = new int[length];
        accessoryTerritory = new int[length];
        countsUnit = new int[length];

        for (int i = 0; i < length; i++) {
            locationUnit[i] = 0;
            accessoryTerritory[i] = 0;
            countsUnit[i] = 0;
        }
    }

    public ACall[] getBoard() {
        return board;
    }

    public int[] getLocationUnit() {
        return locationUnit;
    }

    public int[] getAccessoryTerritory() {
        return accessoryTerritory;
    }

    public int[] getCountsUnit() {
        return countsUnit;
    }
}
