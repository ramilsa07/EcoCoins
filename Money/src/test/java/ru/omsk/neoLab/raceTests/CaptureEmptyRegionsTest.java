//package ru.omsk.neoLab.raceTests;
//
//import org.junit.Assert;
//import org.junit.Test;
//import ru.omsk.neoLab.board.Board;
//import ru.omsk.neoLab.board.Generators.Generator;
//import ru.omsk.neoLab.board.Generators.IGenerator;
//import ru.omsk.neoLab.player.Player;
//import ru.omsk.neoLab.player.PlayerService;
//import ru.omsk.neoLab.race.*;
//
//public class CaptureEmptyRegionsTest {
//    private static final IGenerator generator = new Generator();
//    private static final Board board = new Board(4, 3);
//    private final PlayerService playerService = PlayerService.GetInstance();
//    private final Player player = new Player();
//
//    private static void createBoard() {
//        board.generate();
//
//    }
//
//    // Захват воды разными расами
//    @Test
//    public void capturingWaterForAmphibia() {
//        createBoard();
//        player.changeRace(new Amphibia());
//        playerService.regionCapture(board.getCell(0, 0), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 5
//                && board.getCell(0, 0).getBelongs() == player
//                && board.getCell(0, 0).getCountTokens() == 1);
//    }
//
//    @Test
//    public void capturingWaterForDwarfs() {
//        createBoard();
//        player.changeRace(new Dwarfs());
//        playerService.regionCapture(board.getCell(0, 0), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(0, 0).getBelongs() == player
//                && board.getCell(0, 0).getCountTokens() == 0);
//    }
//
//    @Test
//    public void capturingWaterForElfs() {
//        createBoard();
//        player.changeRace(new Elfs());
//        playerService.regionCapture(board.getCell(0, 0), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 5
//                && board.getCell(0, 0).getBelongs() == player
//                && board.getCell(0, 0).getCountTokens() == 0);
//    }
//
//    @Test
//    public void capturingWaterForMushrooms() {
//        createBoard();
//        player.changeRace(new Mushrooms());
//        playerService.regionCapture(board.getCell(0, 0), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 5
//                && board.getCell(0, 0).getBelongs() == player
//                && board.getCell(0, 0).getCountTokens() == 0);
//    }
//
//    @Test
//    public void capturingWaterForOrcs() {
//        createBoard();
//        player.changeRace(new Orcs());
//        playerService.regionCapture(board.getCell(0, 0), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(0, 0).getBelongs() == player
//                && board.getCell(0, 0).getCountTokens() == 0);
//    }
//
//    @Test
//    public void capturingWaterForUndead() {
//        createBoard();
//        player.changeRace(new Undead());
//        playerService.regionCapture(board.getCell(0, 0), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 10
//                && board.getCell(0, 0).getBelongs() == player
//                && board.getCell(0, 0).getCountTokens() == 0);
//    }
//
//
//    // Захват гор разными расами
//    @Test
//    public void capturingMountedForAmphibia() {
//        createBoard();
//        player.changeRace(new Amphibia());
//        playerService.regionCapture(board.getCell(0, 1), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 3
//                && board.getCell(0, 1).getBelongs() == player
//                && board.getCell(0, 1).getCountTokens() == 3);
//    }
//
//    @Test
//    public void capturingMountedForDwarfs() {
//        createBoard();
//        player.changeRace(new Dwarfs());
//        playerService.regionCapture(board.getCell(0, 1), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 2
//                && board.getCell(0, 1).getBelongs() == player
//                && board.getCell(0, 1).getCountTokens() == 3);
//    }
//
//    @Test
//    public void capturingMountedForElfs() {
//        createBoard();
//        player.changeRace(new Elfs());
//        playerService.regionCapture(board.getCell(0, 1), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 3
//                && board.getCell(0, 1).getBelongs() == player
//                && board.getCell(0, 1).getCountTokens() == 3);
//    }
//
//    @Test
//    public void capturingMountedForMushrooms() {
//        createBoard();
//        player.changeRace(new Mushrooms());
//        playerService.regionCapture(board.getCell(0, 1), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 3
//                && board.getCell(0, 1).getBelongs() == player
//                && board.getCell(0, 1).getCountTokens() == 3);
//    }
//
//    @Test
//    public void capturingMountedForOrcs() {
//        createBoard();
//        player.changeRace(new Orcs());
//        playerService.regionCapture(board.getCell(0, 1), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 3
//                && board.getCell(0, 1).getBelongs() == player
//                && board.getCell(0, 1).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingMountedForUndead() {
//        createBoard();
//        player.changeRace(new Undead());
//        playerService.regionCapture(board.getCell(0, 1), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 8
//                && board.getCell(0, 1).getBelongs() == player
//                && board.getCell(0, 1).getCountTokens() == 3);
//    }
//
//    // Захват грибов разными расами
//    @Test
//    public void capturingMushroomsForAmphibia() {
//        createBoard();
//        player.changeRace(new Amphibia());
//        playerService.regionCapture(board.getCell(0, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(0, 2).getBelongs() == player
//                && board.getCell(0, 2).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingMushroomsForDwarfs() {
//        createBoard();
//        player.changeRace(new Dwarfs());
//        playerService.regionCapture(board.getCell(0, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 3
//                && board.getCell(0, 2).getBelongs() == player
//                && board.getCell(0, 2).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingMushroomsForElfs() {
//        createBoard();
//        player.changeRace(new Elfs());
//        playerService.regionCapture(board.getCell(0, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(0, 2).getBelongs() == player
//                && board.getCell(0, 2).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingMushroomsForMushrooms() {
//        createBoard();
//        player.changeRace(new Mushrooms());
//        playerService.regionCapture(board.getCell(0, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(0, 2).getBelongs() == player
//                && board.getCell(0, 2).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingMushroomsForOrcs() {
//        createBoard();
//        player.changeRace(new Orcs());
//        playerService.regionCapture(board.getCell(0, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(0, 2).getBelongs() == player
//                && board.getCell(0, 2).getCountTokens() == 1);
//    }
//
//    @Test
//    public void capturingMushroomsForUndead() {
//        createBoard();
//        player.changeRace(new Undead());
//        playerService.regionCapture(board.getCell(0, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 9
//                && board.getCell(0, 2).getBelongs() == player
//                && board.getCell(0, 2).getCountTokens() == 2);
//    }
//
//    // Захват земли разными расами
//    @Test
//    public void capturingEarthForAmphibia() {
//        createBoard();
//        player.changeRace(new Amphibia());
//        playerService.regionCapture(board.getCell(2, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(2, 2).getBelongs() == player
//                && board.getCell(2, 2).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingEarthForDwarfs() {
//        createBoard();
//        player.changeRace(new Dwarfs());
//        playerService.regionCapture(board.getCell(2, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 3
//                && board.getCell(2, 2).getBelongs() == player
//                && board.getCell(2, 2).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingEarthForElfs() {
//        createBoard();
//        player.changeRace(new Elfs());
//        playerService.regionCapture(board.getCell(2, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(2, 2).getBelongs() == player
//                && board.getCell(2, 2).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingEarthForMushrooms() {
//        createBoard();
//        player.changeRace(new Mushrooms());
//        playerService.regionCapture(board.getCell(2, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(2, 2).getBelongs() == player
//                && board.getCell(2, 2).getCountTokens() == 2);
//    }
//
//    @Test
//    public void capturingEarthForOrcs() {
//        createBoard();
//        player.changeRace(new Orcs());
//        playerService.regionCapture(board.getCell(2, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 4
//                && board.getCell(2, 2).getBelongs() == player
//                && board.getCell(2, 2).getCountTokens() == 1);
//    }
//
//    @Test
//    public void capturingEarthForUndead() {
//        createBoard();
//        player.changeRace(new Undead());
//        playerService.regionCapture(board.getCell(2, 2), player);
//        Assert.assertTrue(player.getLocationCell().size() != 0 && player.getCountTokens() == 9
//                && board.getCell(2, 2).getBelongs() == player
//                && board.getCell(2, 2).getCountTokens() == 2);
//    }
//}
