package ru.omsk.neoLab;

public class Validator {

    public static boolean isCheckingOutputFromArray(int x, int y, int height, int width) {
        return x > 0 && y > 0 && x < width && y < height;
    }

}
