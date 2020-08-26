package ru.omsk.neoLab.client.botevgekii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class SimpleBotGreat implements IBot {

    Logger log = LoggerFactory.getLogger(SimpleBotGreat.class);

    private Random random = new Random();

    private PlayerService playerService = PlayerService.GetInstance();

    private List<Point> cells = new ArrayList<>();
    private List<Point> cellsOpponent = new ArrayList<>();

    @Override
    public synchronized Answer getAnswer(final Board board) {
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

    private synchronized ARace findingBestRace(final Board board) {
        Board boardClone = new Board(board);
        return boardClone.getRacesPool().get(random.nextInt(boardClone.getRacesPool().size()));
    }

    private synchronized boolean isFindDecline(final Board board) {
        Board boardClone = new Board(board);
        Player playerClone = boardClone.getCurrentPlayer();
        return isDecline(boardClone, playerClone);
    }

    private synchronized boolean isDecline(final Board board, final Player player) {
        cells.clear();
        log.info("Определяем можем ли мы пойти в упадок?");
        for (Cell cell : player.getLocationCell()) {
            if (cell.getCountTokens() > 1) {
                log.info("У {} было {} ", player.getNickName(), player.getCountTokens());
                player.collectTokens(cell);
                log.info("Стало {}", player.getCountTokens());
            }
        }
        toDetermineCell(player, board);
        return cells.size() == 0;
    }

    private synchronized List<Point> findingBestPath(final Board board) {
        cells.clear();
        Board boardClone = new Board(board);
        Player playerClone = new Player(boardClone.getCurrentPlayer());
        log.info("Ищем лучший путь захвата?");
        findingBestPathOpponent(board);
        return toDetermineCell(playerClone, boardClone);
    }

    private synchronized List<Point> toDetermineCell(final Player player, final Board board) {
        int maxCellBefore = 0;
        int maxCellCurrent = 0;
        Point maxCell = new Point();
        HashSet<Cell> possibleCellsCapture;
        boolean isOpponentCell = false;
        if (player.getLocationCell().isEmpty()) {
            possibleCellsCapture = playerService.findOutWherePlayerCanGoFirst(board, player);
        } else {
            possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
        }
        Object[] posCells = possibleCellsCapture.toArray();
        for (Object cell : posCells) {
            maxCellCurrent = calculatingValueCell(player, (Cell) cell);
            for (Point point : cellsOpponent) {
                if (((Cell) cell).getX() == point.x && ((Cell) cell).getY() == point.y) {
                    isOpponentCell = true;
                    break;
                }
            }
            if ((maxCellBefore <= maxCellCurrent) || isOpponentCell) {
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

    private synchronized void findingBestPathOpponent(final Board board) {
        Board boardClone = new Board(board);
        toDetermineOpponent(boardClone, boardClone.getOpponentPlayer());
    }

    private synchronized void toDetermineOpponent(final Board board, final Player opponent) {
        cellsOpponent.clear();
        if (opponent.getRace() == null) {
            List<Point> cellsCurrent = new ArrayList<>();
            int maxCoinsBefore = 0;
            int maxCoinsCurrent = 0;
            for (int i = 0; i < board.getRacesPool().size(); i++) {
                opponent.changeRace(board.getRacesPool().get(i), board);
                cellsCurrent.addAll(toDetermineCell(opponent, board));
                for (Point point : cellsCurrent) {
                    maxCoinsCurrent += opponent.getRace().getAdvantageCoin(board.getCell(point.x, point.y));
                }
                if (maxCoinsBefore <= maxCoinsCurrent) {
                    cellsOpponent.clear();
                    cellsOpponent.addAll(cellsCurrent);
                }
                board.getRacesPool().add(opponent.getRace());
                cells.clear();
            }
        }
    }

    private synchronized int calculatingValueCell(final Player player, final Cell cell) {
        return (player.getRace().getAdvantageCaptureCell(cell) - cell.getBelongs().getRace().getAdvantageDefendCell(cell)) * player.getRace().getAdvantageCoin(cell);
    }

    private synchronized List<Point> findingBestShufflingTokens(final Board board) {
        cells.clear();
        Board boardClone = new Board(board);
        Player playerClone = board.getCurrentPlayer();
        Point minCell = new Point();
        int minCellBefore = Integer.MAX_VALUE;
        int minCellCurrent = 0;
        for (int i = 0; i < playerClone.getCountTokens(); i++) {
            for (int j = 0; j < playerClone.getLocationCell().size(); j++) {
                minCellCurrent = calculatingDefendValueCell(playerClone.getLocationCell().get(j));
                if (minCellBefore > minCellCurrent) {
                    minCellBefore = minCellCurrent;
                    minCell.x = playerClone.getLocationCell().get(j).getX();
                    minCell.y = playerClone.getLocationCell().get(j).getY();
                }
            }
            cells.add(minCell);
            playerClone.shufflingTokens(boardClone.getCell(minCell.x, minCell.y));
        }
        return cells;
    }

    private synchronized int calculatingDefendValueCell(final Cell cell) {
        return cell.getBelongs().getRace().getAdvantageDefendCell(cell);
    }
}
