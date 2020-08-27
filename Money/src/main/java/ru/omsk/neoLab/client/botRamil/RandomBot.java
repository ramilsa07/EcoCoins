package ru.omsk.neoLab.client.botRamil;


import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.answer.CellAnswer;
import ru.omsk.neoLab.answer.DeclineAnswer;
import ru.omsk.neoLab.answer.RaceAnswer;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class RandomBot extends ABot {
    public static class RandomBotFactory extends BaseBotFactory {
        @Override
        public RandomBot createBot(final Player player) {
            return new RandomBot();
        }
    }

    public static final IBot.IBotFactory factory = new RandomBotFactory();
    private final Random random = new Random();
    private final PlayerService playerService = PlayerService.GetInstance();
    private HashSet<Cell> possibleCellsCapture = new HashSet<>();
    List<Point> responseCells = new ArrayList<>();

    public RandomBot() {
    }

    private CellAnswer getCellAnswer(final Board board, final Player player) {
        if (player.getLocationCell().isEmpty()) {
            possibleCellsCapture = playerService.findOutWherePlayerCanGoFirst(board, player);
        } else {
            possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
        }
        Object[] cells = possibleCellsCapture.toArray();

        if (cells.length != 0) {
            Cell move = (Cell) cells[random.nextInt(cells.length)];
            player.regionCapture(move);
            responseCells.add(responseCells.size(), new Point(move.getX(), move.getY()));
            Player testPlayer = new Player(player);
            Board testBoard = new Board(board);
            getCellAnswer(testBoard, testPlayer);
        }
        return new CellAnswer(responseCells);
    }

    private DeclineAnswer getDeclineAnswer(final Board board, final Player player) {
        if (player.getLocationCell().isEmpty()) {
            possibleCellsCapture = playerService.findOutWherePlayerCanGoFirst(board, player);
        } else {
            possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
        }
        if (possibleCellsCapture.isEmpty()) {
            return new DeclineAnswer(true);
        } else {
            return new DeclineAnswer(false);
        }
    }

    private RaceAnswer getRaceAnswer(Board board) {
        return new RaceAnswer(board.getRacesPool().get(random.nextInt(board.getRacesPool().size())));
    }

    private CellAnswer getShufflingAnswer(Player player) {
        for (int i = 0; i < player.getCountTokens(); i++) {
            Cell cell = player.getLocationCell().get(random.nextInt(player.getLocationCell().size()));
            responseCells.add(new Point(cell.getX(), cell.getY()));
        }
        return new CellAnswer(responseCells);
    }

    @Override
    public Answer getAnswer(final Board board) {
        Player playerClone = new Player(board.getCurrentPlayer());
        Board boardClone = new Board(board);
        switch (board.getPhase()) {
            case RACE_CHOICE:
                return getRaceAnswer(board);
            case CAPTURE_OF_REGIONS:
                responseCells.clear();
                return getCellAnswer(boardClone, playerClone);
            case GO_INTO_DECLINE:
                return getDeclineAnswer(boardClone, playerClone);
            case SHUFFLING_TOKENS:
                responseCells.clear();
                return getShufflingAnswer(playerClone);
            default:
                throw new IllegalStateException("Unexpected value: " + boardClone);
        }
    }
}
