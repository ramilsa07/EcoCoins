package ru.omsk.neoLab.board.Сell;

public enum TypeCell {
    EARTH,
    MUSHROOMS,
    MOUNTED,
    WATER;

    public static int toType(TypeCell type) {
        switch (type) {
            case MOUNTED:
                return 3;
            case WATER:
                return 1;
            default:
                return 2;
        }
    }
}
