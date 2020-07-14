package ru.omsk.neoLab.board.Generators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Generators.Cells.Сell.*;

import java.util.Random;

public final class Generator implements IGenerator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private static int countWater;

    private final Random random = new Random();


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
                log.info(board[i][j].getType() + "-- Ячейка " + board[i][j].getX() + " - " + board[i][j].getY());
            }
        }

        long finish = System.currentTimeMillis();
        log.info("Генерация закончилась");
        return board;
    }

  /*  public static void main(String[] args) {
        Generator generator = new Generator();
        board = generator.generate(4, 3);
        for (int i = 0; i < 4; i++) {
            System.out.println();
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j].getType() + " ");
            }
        }
    }*/

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
            log.info("Кол - во воды - " + countWater);
            return cell;
        } else if (cell.getType().equals("Water")) {
            log.info("Попалась снова вода");
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

    private static void toFind(ACell call) {


    }
}
