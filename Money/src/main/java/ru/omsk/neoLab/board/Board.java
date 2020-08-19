package ru.omsk.neoLab.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.LoggerGame;
import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;

public final class Board implements IBoard {
    private static final Logger log = LoggerFactory.getLogger(LoggerGame.class);

    @JsonProperty("board")
    private Cell[][] board;
    @JsonProperty("phase")
    private Phases phase;
    @JsonProperty("currentPlayer")
    private Player currentPlayer;

    private final int height;
    private final int width;

    @JsonCreator
    public Board(@JsonProperty("height") final int height, @JsonProperty("width") final int width) {
        this.height = height;
        this.width = width;
    }

    @JsonCreator
    public Board(@JsonProperty("board") Cell[][] board, @JsonProperty("phases") final Phases phase,
                 @JsonProperty("height") final int height, @JsonProperty("width") final int width) {
        this.board = board;
        this.phase = phase;
        this.height = height;
        this.width = width;
    }

    @JsonCreator
    public Board(@JsonProperty("board") final Board board) {
        this(board.getBoard(), board.getPhase(), board.getHeight(), board.getWidth());
    }

    public Board getCopy(){
        return new Board(this);
    }

    public Board getCopy(final Answer answer, final Player player){
        final Board copy = getCopy();
        player.regionCapture(copy.getCell(answer.getCell().getX(), answer.getCell().getY()));
        return copy;
    }

    @Override
    public Cell[][] generate() {
        IGenerator generator = new Generator();
        board = generator.generate(height, width);
        logOutputBoard(this);
        return board;
    }

    @Override
    public Cell getCell(final int x, final int y) {
        return board[x][y];
    }

    @Override
    public void changePhase(final Phases phase) {
        this.phase = phase;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public final int getHeight() {
        return height;
    }

    public final int getWidth() {
        return width;
    }

    public Phases getPhase() {
        return phase;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public static void logOutputBoard(Board board) { // вывести на экран карту
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                log.info("{}", board.getCell(i, j));
            }
        }
    }
}
