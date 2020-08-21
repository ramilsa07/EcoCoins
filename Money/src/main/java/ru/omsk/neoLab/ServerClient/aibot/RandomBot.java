package ru.omsk.neoLab.ServerClient.aibot;

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
import java.util.Random;

public class RandomBot extends ABot {
    public static class RandomBotFactory extends BaseBotFactory {
        @Override
        public RandomBot createBot(final Player player) {
            return new RandomBot();
        }
    }

    public static final IBotFactory factory = new RandomBotFactory();
    private final Random random = new Random();
    private final PlayerService playerService = PlayerService.GetInstance();
    private HashSet<Cell> possibleCellsCapture = new HashSet<>();

    protected RandomBot() {
    }

    private CellAnswer getCellAnswer(final Board board, final Player player) {
        if (player.getLocationCell().isEmpty()) {
            possibleCellsCapture = playerService.findOutWherePlayerCanGoAtFirst(board, player);
        } else {
            possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
        }
        Object[] cells = possibleCellsCapture.toArray();

        if (!possibleCellsCapture.isEmpty()) {
//            return new CellAnswer((Cell
//            ) cells[random.nextInt(cells.length)]);
            ArrayList<Point> points = new ArrayList<Point>();
            points.add((Point) cells[random.nextInt(cells.length)]);
            return new CellAnswer(points);
        } else {
            return null;
        }
    }

    private DeclineAnswer getDeclineAnswer(final Board board, final Player player) {
        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
        if (possibleCellsCapture.isEmpty()) {
            return new DeclineAnswer(true);
        } else {
            return new DeclineAnswer(false);
        }
    }

    private RaceAnswer getRaceAnswer() {
        return new RaceAnswer(PlayerService.getRacesPool().get(random.nextInt(PlayerService.getRacesPool().size())));
    }

    private CellAnswer getShufflingAnswer(Player player) {
        ArrayList<Point> points = new ArrayList<>();
        Cell cell = player.getLocationCell().get(random.nextInt(player.getLocationCell().size()));
        points.add(new Point(cell.getX(), cell.getY()));
//        return new CellAnswer(player.getLocationCell().get(random.nextInt(player.getLocationCell().size())));
        return new CellAnswer(points);
    }

    @Override
    public Answer getAnswer(final Board board) {
        Player playerClone = new Player(board.getCurrentPlayer());
        Board boardClone = new Board(board);
        switch (board.getPhase()) {
            case RACE_CHOICE:
                return getRaceAnswer();
            case CAPTURE_OF_REGIONS:
                return getCellAnswer(boardClone, playerClone);
            case GO_INTO_DECLINE:
                return getDeclineAnswer(boardClone, playerClone);
            case SHUFFLING_TOKENS:
                return getShufflingAnswer(playerClone);
            default:
                throw new IllegalStateException("Unexpected value: " + boardClone);
        }
    }
}
