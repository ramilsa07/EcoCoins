package ru.omsk.neoLab.board;

public class Board extends ABoard {

    private static volatile ABoard instance;

    private Board() {
    }

    public static synchronized ABoard GetInstance() {
        if (instance == null) {
            instance = new Board();
            instance.generateBoard(4, 3);
        }
        return instance;
    }

}
