package ru.omsk.neoLab.client.botRamil;

import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.answer.CellAnswer;
import ru.omsk.neoLab.answer.DeclineAnswer;
import ru.omsk.neoLab.answer.RaceAnswer;
import ru.omsk.neoLab.answer.Serialize.AnswerDeserialize;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class BotWithOpponentMove extends ABot {
    public static class BotWithOpponentMoveFactory extends ABot.BaseBotFactory {
        @Override
        public SimpleBotRam createBot(final Player player) {
            return new SimpleBotRam();
        }
    }

    public static final IBot.IBotFactory factory = new SimpleBotRam.MinMaxBotFactory();
    private final Random random = new Random();
    private HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();
    private final PlayerService playerService = PlayerService.GetInstance();
    List<Point> winList = new ArrayList<>();

    public BotWithOpponentMove() {

    }

    @Override
    public Answer getAnswer(final Board board) {
        Player playerClone = new Player(board.getCurrentPlayer());
        Player opponentClone = new Player(board.getOpponentPlayer());
        Board boardClone = new Board(board);
        switch (board.getPhase()) {
            case RACE_CHOICE:
                return getRaceAnswer(boardClone);
            case CAPTURE_OF_REGIONS:
                winList.clear();
                return getCellAnswer(boardClone, playerClone, opponentClone);
            case GO_INTO_DECLINE:
                return getDeclineAnswer(boardClone, playerClone);
            case SHUFFLING_TOKENS:
                return getShufflingAnswer(playerClone);
            default:
                throw new IllegalStateException("Unexpected value: " + boardClone);
        }
    }

    private CellAnswer getShufflingAnswer(Player player) {
        ArrayList<Point> points = new ArrayList<>();
        Cell cell = player.getLocationCell().get(random.nextInt(player.getLocationCell().size()));
        points.add(new Point(cell.getX(), cell.getY()));
        return new CellAnswer(points);
    }

    private DeclineAnswer getDeclineAnswer(Board board, Player player) {
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

    private CellAnswer getCellAnswer(Board board, Player player, Player opponent){
        winList.clear();
        CellAnswer opponentAnswer = findBestMove(board, opponent);
        for (Point point : opponentAnswer.getCells()) {
            playerService.regionCapture(board.getCell(point.x, point.y), opponent);
        }
        winList.clear();
        return findBestMove(board, player);
    }

    private CellAnswer findBestMove(Board board, Player player) {
        int bestAssessment = Integer.MIN_VALUE;
        Cell bestCell = null;
        int nodeAssessment;
        if (player.getLocationCell().isEmpty()) {
            possibleCellsCapture = playerService.findOutWherePlayerCanGoFirst(board, player);
        } else {
            possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
        }
        Object[] cells = possibleCellsCapture.toArray();
        for (Object cell : cells) {
            nodeAssessment = assessment(player, (Cell) cell);
            if (bestAssessment < nodeAssessment) {
                bestAssessment = nodeAssessment;
                bestCell = (Cell) cell;
            }
        }
        if (cells.length != 0) {
            player.regionCapture(bestCell);
            winList.add(winList.size(), new Point(bestCell.getX(), bestCell.getY()));
            Player testPlayer = new Player(player);
            Board testBoard = new Board(board);
            findBestMove(testBoard, testPlayer);
        }
        return new CellAnswer(winList);
    }


    private RaceAnswer getRaceAnswer(Board board) { // Просто рандомная хрень
        return new RaceAnswer(board.getRacesPool().get(random.nextInt(board.getRacesPool().size())));
    }

    /**
     * Метод вычисляет оценку определенного захвата. Его задача вернуть произведение оставшихся токенов после захвата и
     * числа монет, которые игрок получит с этой территории. Чем больше эта оценка тем лучше ход.
     *
     * @param player
     * @param cell
     * @return оценка удачного захвата
     */
    private int assessment(final Player player, final Cell cell) {
        int count = 0;
        if (cell.getCountTokens() == 0) {
            count -= player.getRace().getAdvantageCaptureCell(cell);
        } else {
            count -= player.getRace().getAdvantageCaptureCell(cell) +
                    cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1;
        }
        if (player.getRace().isAdvantageOpportunityCaptureCell(cell)) {
            count = (count + player.getCountTokens()) * player.getRace().getAdvantageCoin(cell);
        } else {
            count = 0;
        }
        if (player.getRace().getNameRace().equals("Elfs")) {
            player.getRace().clearCells();
        }
        return count;
    }
}
