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

public class SimpleBotRam extends ABot {
    public static class MinMaxBotFactory extends ABot.BaseBotFactory {
        @Override
        public SimpleBotRam createBot(final Player player) {
            return new SimpleBotRam();
        }
    }

    public static final IBot.IBotFactory factory = new MinMaxBotFactory();
    private final Random random = new Random();
    private HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();
    private final PlayerService playerService = PlayerService.GetInstance();
    List<Point> responseCells = new ArrayList<>();

    public SimpleBotRam() {

    }

    @Override
    public Answer getAnswer(final Board board) {
        Player playerClone = new Player(board.getCurrentPlayer());
        Board boardClone = new Board(board);
        switch (board.getPhase()) {
            case RACE_CHOICE:
                return getRaceAnswer(boardClone);
            case CAPTURE_OF_REGIONS:
                responseCells.clear();
                return getCellAnswer(boardClone, playerClone);
            case GO_INTO_DECLINE:
                return getDeclineAnswer(boardClone, playerClone);
            case SHUFFLING_TOKENS:
                responseCells.clear();
                return getShufflingAnswer(boardClone, playerClone);
            default:
                throw new IllegalStateException("Unexpected value: " + boardClone);
        }
    }

    private CellAnswer getShufflingAnswer(Board board, Player player) {
        Point bestCell = new Point();
        int bestAssessment = Integer.MAX_VALUE;
        int nodeAssessment;
        for (int i = 0; i < player.getCountTokens(); i++) {
            for (int j = 0; j < player.getLocationCell().size(); j++) {
                nodeAssessment = assessmentForShuffling(player.getLocationCell().get(j));
                if (bestAssessment > nodeAssessment) {
                    bestAssessment = nodeAssessment;
                    bestCell.x = player.getLocationCell().get(j).getX();
                    bestCell.y = player.getLocationCell().get(j).getY();
                }
            }
            responseCells.add(bestCell);
            player.shufflingTokens(board.getCell(bestCell.x, bestCell.y));
        }
        return new CellAnswer(responseCells);
    }

    private int assessmentForShuffling(Cell cell) {
        return cell.getBelongs().getRace().getAdvantageCaptureCell(cell) +
                cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1;
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

    private CellAnswer getCellAnswer(Board board, Player player) {
        Cell bestCell = null;
        int bestAssessment = Integer.MIN_VALUE;
        int nodeAssessment;
        if (player.getLocationCell().isEmpty()) {
            possibleCellsCapture = playerService.findOutWherePlayerCanGoFirst(board, player);
        } else {
            possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
        }
        Object[] cells = possibleCellsCapture.toArray();
        for (Object cell : cells) {
            nodeAssessment = assessmentForCapture(player, (Cell) cell);
            if (bestAssessment < nodeAssessment) {
                bestAssessment = nodeAssessment;
                bestCell = (Cell) cell;
            }
        }
        if (cells.length != 0) {
            player.regionCapture(bestCell);
            responseCells.add(responseCells.size(), new Point(bestCell.getX(), bestCell.getY()));
            Player testPlayer = new Player(player);
            Board testBoard = new Board(board);
            getCellAnswer(testBoard, testPlayer);
        }
        return new CellAnswer(responseCells);
    }


    private RaceAnswer getRaceAnswer(Board board) {
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
    private int assessmentForCapture(final Player player, final Cell cell) {
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
