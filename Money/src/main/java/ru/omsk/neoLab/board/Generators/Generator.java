package ru.omsk.neoLab.board.Generators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.board.Сell.Terrain;

import java.util.Random;

/*
 * Класс для генерации игровой доски по правилам из тз
 * */

public final class Generator implements IGenerator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private final Random random = new Random();
    private static int countWater;

    @Override
    public final Cell[][] generate(final int height, final int width) {
        log.info("Генерация началась");
        Cell[][] board = new Cell[height][width];

        countWater = 0;
        int maxWater = Math.round((float) (height * width) / 3);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = toRandom(maxWater);
                board[i][j].setX(i);
                board[i][j].setY(j);
            }
        }
        log.info("Генерация закончилась");
        return board;
    }

    private Cell toRandom(final int maxWater) {
        Cell cell = new Cell();
        switch (random.nextInt(4)) {
            case 0:
                cell.setType(Terrain.EARTH);
                break;
            case 1:
                cell.setType(Terrain.MUSHROOMS);
                break;
            case 2:
                cell.setType(Terrain.MOUNTED);
                break;
            case 3:
                cell.setType(Terrain.WATER);
                cell.setAbilityCapture(false);
                break;
        }
        if (cell.getType().equals(Terrain.WATER) && (countWater < maxWater)) {
            countWater++;
            return cell;
        }
        switch (random.nextInt(3)) {
            case 0:
                cell.setType(Terrain.EARTH);
                break;
            case 1:
                cell.setType(Terrain.MUSHROOMS);
                break;
            case 2:
                cell.setType(Terrain.MOUNTED);
                break;
        }
        return cell;
    }

    @Override
    public final Cell[][] createStaticBoard(){
        Cell[][] board = new Cell[4][3];
        Cell cell00 = new Cell();
        cell00.setType(Terrain.WATER);
        cell00.setAbilityCapture(false);
        cell00.setX(0);
        cell00.setY(0);
        board[0][0] = cell00;

        Cell cell01 = new Cell();
        cell01.setType(Terrain.MOUNTED);
        cell01.setX(0);
        cell01.setY(1);
        board[0][1] = cell01;

        Cell cell02 = new Cell();
        cell02.setType(Terrain.MUSHROOMS);
        cell02.setX(0);
        cell02.setY(2);
        board[0][2] = cell02;

        Cell cell10 = new Cell();
        cell10.setType(Terrain.EARTH);
        cell10.setX(1);
        cell10.setY(0);
        board[1][0] = cell10;

        Cell cell11 = new Cell();
        cell11.setType(Terrain.WATER);
        cell11.setAbilityCapture(false);
        cell11.setX(1);
        cell11.setY(1);
        board[1][1] = cell11;

        Cell cell12 = new Cell();
        cell12.setType(Terrain.MUSHROOMS);
        cell12.setX(1);
        cell12.setY(2);
        board[1][2] = cell12;

        Cell cell20 = new Cell();
        cell20.setType(Terrain.MOUNTED);
        cell20.setX(2);
        cell20.setY(0);
        board[2][0] = cell20;

        Cell cell21 = new Cell();
        cell21.setType(Terrain.WATER);
        cell21.setAbilityCapture(false);
        cell21.setX(2);
        cell21.setY(1);
        board[2][1] = cell21;

        Cell cell22 = new Cell();
        cell22.setType(Terrain.EARTH);
        cell22.setX(2);
        cell22.setY(2);
        board[2][2] = cell22;

        Cell cell30 = new Cell();
        cell30.setType(Terrain.MUSHROOMS);
        cell30.setX(3);
        cell30.setY(0);
        board[3][0] = cell30;

        Cell cell31 = new Cell();
        cell31.setType(Terrain.EARTH);
        cell31.setX(3);
        cell31.setY(1);
        board[3][1] = cell31;

        Cell cell32 = new Cell();
        cell32.setType(Terrain.MOUNTED);
        cell32.setX(3);
        cell32.setY(2);
        board[3][2] = cell32;

        return board;
    }
}
