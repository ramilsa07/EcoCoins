package ru.omsk.neoLab.board.Generators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Сell.*;

import java.util.Random;

/*
 * Класс для генерации игровой доски по правилам из тз
 * */

public final class Generator implements IGenerator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private final Random random = new Random();
    private static int countWater;

    @Override
    public ACell[][] generate(int height, int width) {
        log.info("Генерация началась");
        ACell[][] board = new ACell[height][width];

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

    private ACell toRandom(int maxWater) {

        ACell cell = null;
        switch (random.nextInt(4)) {
            case 0:
                cell = new Earth();
                break;
            case 1:
                cell = new Mushrooms();
                break;
            case 2:
                cell = new Mounted();
                break;
            case 3:
                cell = new Water();
                break;
        }
        if ((cell.getType().equals("Water")) && (countWater < maxWater)) {
            countWater++;
            return cell;
        } else if (cell.getType().equals("Water")) {
        }
        switch (random.nextInt(3)) {
            case 0:
                cell = new Earth();
                break;
            case 1:
                cell = new Mushrooms();
                break;
            case 2:
                cell = new Mounted();
                break;
        }
        return cell;
    }
}
