package ru.omsk.neoLab.client.Bot;

import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.answer.CellAnswer;
import ru.omsk.neoLab.answer.DeclineAnswer;
import ru.omsk.neoLab.answer.RaceAnswer;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;
import ru.omsk.neoLab.race.ARace;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SimpleBot implements IBot {

    private Random random = new Random();

    private PlayerService playerService = PlayerService.GetInstance();

    private List<Point> cells = new ArrayList<>();

    @Override
    public Answer getAnswer(final Board board) {
        switch (board.getPhase()) {
            case RACE_CHOICE:
                return new RaceAnswer(findingBestRace(board));
            case GO_INTO_DECLINE:
                return new DeclineAnswer(isFindDecline(board));
            case CAPTURE_OF_REGIONS:
                return new CellAnswer(findingBestPath(board));
            case SHUFFLING_TOKENS:
                return new CellAnswer(findingBestShufflingTokens(board));
            default:
                throw new IllegalStateException("Unexpected value: " + board.getPhase());
        }
    }

    private ARace findingBestRace(final Board board) {
        Board boardClone = new Board(board);
        System.out.println(boardClone.getRacesPool().get(random.nextInt(boardClone.getRacesPool().size())));
        return boardClone.getRacesPool().get(random.nextInt(boardClone.getRacesPool().size()));
    }

    private boolean isFindDecline(final Board board) {
        Board boardClone = new Board(board);
        Player playerClone = boardClone.getCurrentPlayer();
        return isDecline(boardClone, playerClone);
    }

    private boolean isDecline(final Board board, final Player player) {
        cells.clear();
        for (Cell cell : player.getLocationCell()) {
            if (cell.getCountTokens() >= 1) {
                //player.collectTokens();
            }
        }
        toDetermineCell(player, board);
        return cells.size() == 0; //cells.size() == 0;
    }

    private List<Point> findingBestPath(final Board board) {
        cells.clear();
        Board boardClone = new Board(board);
        Player playerClone = new Player(boardClone.getCurrentPlayer());
        return toDetermineCell(playerClone, boardClone);
    }

    private List<Point> toDetermineCell(final Player player, final Board board) {
        int maxCellBefore = 0;
        int maxCellCurrent = 0;
        Point maxCell = new Point();
        HashSet<Cell> possibleCellsCapture;
        if (player.getLocationCell().isEmpty()) {
            possibleCellsCapture = playerService.findOutWherePlayerCanGoFirst(board, player);
        } else {
            possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
        }
        Object[] posCells = possibleCellsCapture.toArray();
        for (Object cell : posCells) {
            maxCellCurrent = calculatingValueCell(player, (Cell) cell);
            if (maxCellBefore < maxCellCurrent) {
                maxCellBefore = maxCellCurrent;
                maxCell.x = ((Cell) cell).getX();
                maxCell.y = ((Cell) cell).getY();
            }
        }
        if (posCells.length != 0) {
            playerService.regionCapture(board.getCell(maxCell.x, maxCell.y), player);
            cells.add(maxCell);
        } else {
            return cells;
        }
        return toDetermineCell(player, board);
    }

    private int calculatingValueCell(final Player player, final Cell cell) {
        return (player.getRace().getAdvantageCaptureCell(cell) - cell.getBelongs().getRace().getAdvantageDefendCell(cell)) * player.getRace().getAdvantageCoin(cell);
    }

    private List<Point> findingBestShufflingTokens(final Board board) {
        cells.clear();
        Player playerClone = new Player(board.getCurrentPlayer());
        Board boardClone = new Board(board);
        Point minCell = null;
        int minCellBefore = 0;
        int minCellCurrent = 0;
        int currentj = 0;
        for (int i = 0; i < playerClone.getCountTokens(); i++) {
            for (int j = 0; j < playerClone.getLocationCell().size(); j++) {
                minCellCurrent = calculatingDefendValueCell(playerClone.getLocationCell().get(j));
                if (minCellBefore > minCellCurrent) {
                    minCellBefore = minCellCurrent;
                    currentj = j;
                    minCell.x = playerClone.getLocationCell().get(currentj).getX();
                    minCell.y = playerClone.getLocationCell().get(currentj).getY();
                }
            }
            if (minCell != null) {
                cells.add(minCell);
                playerClone.shufflingTokens(boardClone.getCell(minCell.x, minCell.y));
            }
        }
        return cells;
    }

    private int calculatingDefendValueCell(final Cell cell) {
        return cell.getBelongs().getRace().getAdvantageDefendCell(cell);
    }
}
