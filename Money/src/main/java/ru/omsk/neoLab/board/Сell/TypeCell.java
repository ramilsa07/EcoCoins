package ru.omsk.neoLab.board.Сell;

public enum TypeCell {
    EARTH,
    MUSHROOMS,
    MOUNTED,
    WATER;

    public static int toType(TypeCell type) {
        return switch (type) {
            case MOUNTED -> 3;
            case WATER -> 1;
            default -> 2;
        };
    }
}
