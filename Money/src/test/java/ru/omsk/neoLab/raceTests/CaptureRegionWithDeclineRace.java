package ru.omsk.neoLab.raceTests;

import org.junit.Assert;
import org.junit.Test;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;
import ru.omsk.neoLab.race.*;

public class CaptureRegionWithDeclineRace {
    private static final IGenerator generator = new Generator();
    private static final Board board = new Board(4, 3);
    private final PlayerService playerService = PlayerService.GetInstance();
    private final Player player = new Player();
    private final Player current = new Player();

    private static void createBoard() {
        board.generate();
    }

    // Захват земли разными расами // Для гор и грибов работает аналогично
    @Test
    public void capturingEarthWithAmphibiaForDwarfs() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(2, 2), player);
        player.goIntoDecline();
        current.changeRace(new Dwarfs());
        playerService.regionCapture(board.getBoardElements(2, 2), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 4 && current.getCountTokens() == 0 &&
                board.getBoardElements(2, 2).getBelongs() == current &&
                board.getBoardElements(2, 2).getCountTokens() == 5);
    }

    @Test
    public void capturingEarthWithAmphibiaForElfs() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(2, 2), player);
        player.goIntoDecline();
        current.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(2, 2), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 4 && current.getCountTokens() == 1 &&
                board.getBoardElements(2, 2).getBelongs() == current &&
                board.getBoardElements(2, 2).getCountTokens() == 5);
    }

    @Test
    public void capturingEarthWithAmphibiaForMushrooms() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(2, 2), player);
        player.goIntoDecline();
        current.changeRace(new Mushrooms());
        playerService.regionCapture(board.getBoardElements(2, 2), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 4 && current.getCountTokens() == 1 &&
                board.getBoardElements(2, 2).getBelongs() == current &&
                board.getBoardElements(2, 2).getCountTokens() == 5);
    }

    @Test
    public void capturingEarthWithAmphibiaForOrcs() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(2, 2), player);
        player.goIntoDecline();
        current.changeRace(new Orcs());
        playerService.regionCapture(board.getBoardElements(2, 2), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 4 && current.getCountTokens() == 1 &&
                board.getBoardElements(2, 2).getBelongs() == current &&
                board.getBoardElements(2, 2).getCountTokens() == 4);
    }

    @Test
    public void capturingEarthWithAmphibiaForUndead() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(2, 2), player);
        player.goIntoDecline();
        current.changeRace(new Undead());
        playerService.regionCapture(board.getBoardElements(2, 2), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 4 && current.getCountTokens() == 6 &&
                board.getBoardElements(2, 2).getBelongs() == current &&
                board.getBoardElements(2, 2).getCountTokens() == 5);
    }

    @Test
    public void capturingEarthWithElfsForAmphibia() {
        createBoard();
        player.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(2, 2), player);
        player.goIntoDecline();
        current.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(2, 2), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 4 && current.getCountTokens() == 1 &&
                board.getBoardElements(2, 2).getBelongs() == current &&
                board.getBoardElements(2, 2).getCountTokens() == 5);
    }

    // захват воды разными расами, когда там стоят эльфы
    @Test
    public void capturingWaterWithElfsForAmphibia() {
        createBoard();
        player.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 5 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 1);
    }

    @Test
    public void capturingWaterWithElfsForDwarfs() {
        createBoard();
        player.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Dwarfs());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 4 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    @Test
    public void capturingWaterWithMushroomsForElfs() {
        createBoard();
        player.changeRace(new Mushrooms());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 5 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    @Test
    public void capturingWaterWithElfsForMushrooms() {
        createBoard();
        player.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Mushrooms());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 5 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    @Test
    public void capturingWaterWithElfsForOrcs() {
        createBoard();
        player.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Orcs());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 4 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    @Test
    public void capturingWaterWithElfsForUndead() {
        createBoard();
        player.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Undead());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 10 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    // захват воды разными расами, когда там стоят амфибии
    @Test
    public void capturingWaterWithAmphibianForDwarfs() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Dwarfs());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 2 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    @Test
    public void capturingWaterWithAmphibianForElfs() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Elfs());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 3 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    @Test
    public void capturingWaterWithAmphibianForMushrooms() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Mushrooms());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 3 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    @Test
    public void capturingWaterWithAmphibianForOrcs() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Orcs());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 2 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }

    @Test
    public void capturingWaterWithAmphibianForUndead() {
        createBoard();
        player.changeRace(new Amphibia());
        playerService.regionCapture(board.getBoardElements(0, 0), player);
        player.goIntoDecline();
        current.changeRace(new Undead());
        playerService.regionCapture(board.getBoardElements(0, 0), current);
        Assert.assertTrue(player.getLocationCell().size() == 0 && current.getLocationCell().size() == 1 &&
                player.getCountTokens() == 5 && current.getCountTokens() == 8 &&
                board.getBoardElements(0, 0).getBelongs() == current &&
                board.getBoardElements(0, 0).getCountTokens() == 0);
    }
}
