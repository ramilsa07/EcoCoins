package ru.omsk.neoLab.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.race.*;

import java.util.ArrayList;

public final class Board implements IBoard {
    private static final Logger log = LoggerFactory.getLogger(Board.class);

    @JsonProperty("board")
    private Cell[][] board;
    @JsonProperty("phase")
    private Phases phase;
    @JsonProperty("currentPlayer")
    private Player currentPlayer;
    @JsonProperty("racesPool")
    private ArrayList<ARace> racesPool = new ArrayList<>();

    private final int height;
    private final int width;

    @JsonCreator
    public Board(@JsonProperty("height") final int height, @JsonProperty("width") final int width) {
        this.height = height;
        this.width = width;
    }

    public Board(final Board board) {
        this(board.getBoard(), board.getPhase(), board.getCurrentPlayer(), board.getHeight(), board.getWidth());
    }

    public Board(Cell[][] board, Phases phase, Player currentPlayer, int height, int width) {
        this.board = board;
        this.phase = phase;
        this.currentPlayer = currentPlayer;
        this.height = height;
        this.width = width;
    }

    @Override
    public Cell[][] generate() {
        IGenerator generator = new Generator();
        board = generator.generate(height, width);
        racesPool = collectRacePool(racesPool);
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

    private ArrayList<ARace> collectRacePool(ArrayList<ARace> racesPool) {
        racesPool.add(new Amphibia());
        racesPool.add(new Dwarfs());
        racesPool.add(new Elfs());
        racesPool.add(new Mushrooms());
        racesPool.add(new Orcs());
        racesPool.add(new Undead());
        return racesPool;
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

    public ArrayList<ARace> getRacesPool() {
        return racesPool;
    }
}
