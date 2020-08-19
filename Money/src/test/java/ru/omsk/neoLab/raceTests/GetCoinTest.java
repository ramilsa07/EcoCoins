//package ru.omsk.neoLab.raceTests;
//
//import org.junit.Assert;
//import org.junit.Test;
//import ru.omsk.neoLab.board.Сell.Cell;
//import ru.omsk.neoLab.board.Сell.TypeCell;
//import ru.omsk.neoLab.player.Player;
//import ru.omsk.neoLab.player.PlayerService;
//
//public class GetCoinTest {
//    private final PlayerService playerService = PlayerService.GetInstance();
//    // Счетчик монет для эльфов
//    @Test
//    public void getCoinFrom3EarthForElf() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(2));
//        for (int i = 0; i < 3; i++) {
//            Cell cell = new Cell();
//            cell.setType(TypeCell.EARTH);
//            playerService.regionCapture(cell, player);
//        }
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 4);
//    }
//
//    @Test
//    public void getCoinFrom3EarthForDeclineElf() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(2));
//        for (int i = 0; i < 3; i++) {
//            Cell cell = new Cell();
//            cell.setType(TypeCell.EARTH);
//            playerService.regionCapture(cell, player);
//        }
//        player.goIntoDecline();
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 4);
//    }
//
//    @Test
//    public void getCoinFrom2DifTerrForElf2() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(2));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.WATER);
//        cell.setAbilityCapture(false);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell1, player);
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 2);
//    }
//
//    @Test
//    public void getCoinFrom2DifTerrForDeclineElf() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(2));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.WATER);
//        cell.setAbilityCapture(false);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell1, player);
//        player.goIntoDecline();
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 2);
//    }
//
//    @Test
//    public void getCoinFrom3DifTerrForElf() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(2));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MOUNTED);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell1, player);
//        Cell cell2 = new Cell();
//        cell2.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell2, player);
//        Cell cell3 = new Cell();
//        cell3.setType(TypeCell.EARTH);
//        playerService.regionCapture(cell3, player);
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 7);
//    }
//
//    @Test
//    public void getCoinFrom3DifTerrForDeclineElf() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(2));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MOUNTED);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell1, player);
//        Cell cell2 = new Cell();
//        cell2.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell2, player);
//        Cell cell3 = new Cell();
//        cell3.setType(TypeCell.EARTH);
//        playerService.regionCapture(cell3, player);
//        player.goIntoDecline();
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 7);
//    }
//
//    @Test
//    public void getCoinFrom2IterationsForElf() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(2));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MOUNTED);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell1, player);
//        player.collectAllCoins();
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 8);
//    }
//
//    @Test
//    public void getCoinFrom2IterationsForDeclineElf() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(2));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MOUNTED);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell1, player);
//        player.goIntoDecline();
//        player.collectAllCoins();
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 8);
//    }
//
//    // Счетчик монет для амфибий
//    @Test
//    public void getCoinFrom2DifTerrForAmfibia() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.WATER);
//        cell.setAbilityCapture(false);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell1, player);
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 2);
//    }
//
//    @Test
//    public void getCoinFrom2DifTerrForDeclineAmfibia() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.WATER);
//        cell.setAbilityCapture(false);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell1, player);
//        player.goIntoDecline();
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 2);
//    }
//
//    @Test
//    public void getCoinFrom3DifTerrForAmfibia() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(0));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MOUNTED);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.WATER);
//        cell1.setAbilityCapture(false);
//        playerService.regionCapture(cell1, player);
//        Cell cell2 = new Cell();
//        cell2.setType(TypeCell.WATER);
//        cell2.setAbilityCapture(false);
//        playerService.regionCapture(cell2, player);
//        Cell cell3 = new Cell();
//        cell3.setType(TypeCell.EARTH);
//        playerService.regionCapture(cell3, player);
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 4);
//    }
//
//    // Счетчик монет для гномов
//    @Test
//    public void getCoinFrom3DifTerrForDwarfs() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(1));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MOUNTED);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.WATER);
//        cell1.setAbilityCapture(false);
//        playerService.regionCapture(cell1, player);
//        Cell cell2 = new Cell();
//        cell2.setType(TypeCell.WATER);
//        cell2.setAbilityCapture(false);
//        playerService.regionCapture(cell2, player);
//        Cell cell3 = new Cell();
//        cell3.setType(TypeCell.EARTH);
//        playerService.regionCapture(cell3, player);
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 2);
//    }
//
//    // Счетчик монет для орков
//    @Test
//    public void getCoinFrom3DifTerrForOrcs() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(4));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MOUNTED);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.WATER);
//        cell1.setAbilityCapture(false);
//        playerService.regionCapture(cell1, player);
//        Cell cell2 = new Cell();
//        cell2.setType(TypeCell.WATER);
//        cell2.setAbilityCapture(false);
//        playerService.regionCapture(cell2, player);
//        Cell cell3 = new Cell();
//        cell3.setType(TypeCell.EARTH);
//        playerService.regionCapture(cell3, player);
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 2);
//    }
//
//    // Счетчик монет для нежити
//    @Test
//    public void getCoinFrom3DifTerrForUndead() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(5));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MOUNTED);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.WATER);
//        cell1.setAbilityCapture(false);
//        playerService.regionCapture(cell1, player);
//        Cell cell2 = new Cell();
//        cell2.setType(TypeCell.WATER);
//        cell2.setAbilityCapture(false);
//        playerService.regionCapture(cell2, player);
//        Cell cell3 = new Cell();
//        cell3.setType(TypeCell.EARTH);
//        playerService.regionCapture(cell3, player);
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 2);
//    }
//
//    // Счетчик монет для грибов
//    @Test
//    public void getCoinFrom3DifTerrForMushrooms() {
//        Player player = new Player();
//        player.changeRace(PlayerService.getRacesPool().get(3));
//        Cell cell = new Cell();
//        cell.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell, player);
//        Cell cell1 = new Cell();
//        cell1.setType(TypeCell.WATER);
//        cell1.setAbilityCapture(false);
//        playerService.regionCapture(cell1, player);
//        Cell cell2 = new Cell();
//        cell2.setType(TypeCell.MUSHROOMS);
//        playerService.regionCapture(cell2, player);
//        Cell cell3 = new Cell();
//        cell3.setType(TypeCell.EARTH);
//        playerService.regionCapture(cell3, player);
//        player.collectAllCoins();
//        Assert.assertEquals(player.getCountCoin(), 5);
//    }
//}
