//package ru.omsk.neoLab.selfPlayTests;
//
//import org.junit.Assert;
//import org.junit.Test;
//import ru.omsk.neoLab.board.Board;
//import ru.omsk.neoLab.board.Generators.Generator;
//import ru.omsk.neoLab.board.Generators.IGenerator;
//import ru.omsk.neoLab.board.Сell.Cell;
//import ru.omsk.neoLab.player.Player;
//import ru.omsk.neoLab.player.PlayerService;
//import ru.omsk.neoLab.race.Amphibia;
//import ru.omsk.neoLab.selfplay.SelfPlay;
//
//import java.util.HashSet;
//
//public class SelfPlayTest {
//    private static final IGenerator generator = new Generator();
//    private static final Board board = new Board(4,3);
//    private final PlayerService playerService = PlayerService.GetInstance();
//    private final Player player = new Player();
//    private final Player current = new Player();
//
//    private static void createBoard() {
//        board = generator.createStaticBoard();
//    }
//
//    @Test
//    public void raceChoice() {
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        player.goIntoDecline();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        player.goIntoDecline();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        current.changeRace(PlayerService.getRacesPool().get(0));
//        current.goIntoDecline();
//        current.changeRace(PlayerService.getRacesPool().get(0));
//        current.goIntoDecline();
//        current.changeRace(PlayerService.getRacesPool().get(0));
//        current.goIntoDecline();
//        current.changeRace(PlayerService.getRacesPool().get(0));
//        Assert.assertTrue(current.getRace().getNameRace().equals("Amphibia"));
//    }
//
//    @Test
//    public void distributionOfTokensOnCapturedCells() {
//        createBoard();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        playerService.regionCapture(board.getCell(0, 0), player);
//        playerService.regionCapture(board.getCell(0, 1), player);
//        player.shufflingTokens(player.getLocationCell().get(0));
//        Assert.assertTrue(player.getCountTokens() == 0 && board.getCell(0, 0).getCountTokens() == 3
//                && board.getCell(0, 1).getCountTokens() == 3);
//    }
//
//    @Test
//    public void takingTokensInHand() {
//        createBoard();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        playerService.regionCapture(board.getCell(0, 0), player);
//        playerService.regionCapture(board.getCell(0, 1), player);
//        player.shufflingTokens(player.getLocationCell().get(0));
//        player.collectTokens();
//        Assert.assertTrue(player.getCountTokens() == 4 && board.getCell(0, 0).getCountTokens() == 1
//                && board.getCell(0, 1).getCountTokens() == 1);
//    }
//
//    @Test
//    public void WhereCanIGo() {
//        HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();
//        createBoard();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        playerService.regionCapture(board.getCell(0, 0), player);
//        playerService.regionCapture(board.getCell(0, 1), player);
//        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
//        Assert.assertFalse(possibleCellsCapture.isEmpty());
//    }
//    @Test
//    public void WhereCanIGo2() {
//        HashSet<Cell> possibleCellsCapture = new HashSet<Cell>();
//        createBoard();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        playerService.regionCapture(board.getCell(0, 0), player);
//        playerService.regionCapture(board.getCell(0, 1), player);
//        playerService.regionCapture(board.getCell(0, 2), player);
//        possibleCellsCapture = playerService.findOutWherePlayerCanGo(board, player);
//        Assert.assertTrue(possibleCellsCapture.isEmpty());
//    }
//}