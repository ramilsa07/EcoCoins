package ru.omsk.neoLab.board.Generators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Generators.Calls.Call.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator implements IGenerator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private static int countWater;

    private final static List<ACall> listCall = new ArrayList<ACall>();
    private final Random random = new Random();

    static {
        listCall.add(new Earth());
        listCall.add(new Mushrooms());
        listCall.add(new Mounted());
        listCall.add(new Water());
    }

    @Override
    public ACall[][] generate(int height, int width) {
        long start = System.currentTimeMillis();
        log.info("Генерация началась");
        ACall[][] board = new ACall[height][width];
        //private ACall[][] board;

        countWater = 0;
        int maxWater = Math.round((float) (height * width) / 3);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = toRandom(maxWater);
            }
        }

        long finish = System.currentTimeMillis();
        log.info("Генерация закончилась");
        long timeConsumedMillis = finish - start;
        System.out.println("Время выполнения: "  + timeConsumedMillis);
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

    private ACall toRandom(int maxWater){

        ACall call = listCall.get(random.nextInt(4));
        if ((call.getType().equals("Water")) && (countWater < maxWater)) {
            countWater++;
            log.info("Кол - во воды - " + countWater);
            return call;
        } else if (call.getType().equals("Water")) {
            log.info("Попалась снова вода");
        }
        return listCall.get(random.nextInt(3));
    }

    private static void toFind(ACall call){


    }
}
