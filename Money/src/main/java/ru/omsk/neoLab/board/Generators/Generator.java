package ru.omsk.neoLab.board.Generators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.board.Сell.TypeCell;

import java.util.Random;

/*
 * Класс для генерации игровой доски по правилам из тз
 * */

public final class Generator implements IGenerator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private final Random random = new Random();
    private static int countWater;

    @Override
    public Cell[][] generate(int height, int width) {
        log.info("Генерация началась");
        Cell[][] board = new Cell[height][width];

        countWater = 0;
        int maxWater = Math.round((float) (height * width) / 3);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = toRandom(board[i][j], maxWater);
                board[i][j].setX(i);
                board[i][j].setY(j);
            }
        }
        log.info("Генерация закончилась");
        return board;
    }

    private Cell toRandom(Cell cell, int maxWater) {

        cell = new Cell();
        switch (random.nextInt(4)) {
            case 0:
                cell.setType(TypeCell.Earth);
                break;
            case 1:
                cell.setType(TypeCell.Mushrooms);
                break;
            case 2:
                cell.setType(TypeCell.Mounted);
                break;
            case 3:
                cell.setType(TypeCell.Water);
                break;
        }
        if (cell.getType().equals(TypeCell.Water) && (countWater < maxWater)) {
            countWater++;
            return cell;
        }
        switch (random.nextInt(3)) {
            case 0:
                cell.setType(TypeCell.Earth);
                break;
            case 1:
                cell.setType(TypeCell.Mushrooms);
                break;
            case 2:
                cell.setType(TypeCell.Mounted);
                break;
        }
        return cell;
    }
}
