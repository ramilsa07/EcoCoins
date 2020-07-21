package ru.omsk.neoLab.simpleBot;

import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;

import java.util.Random;

public class SimpleBot {
    private final Random random = new Random();

    public SimpleBot() {
    }

    public void getRandomRace(Player currentPlayer){
        currentPlayer.changeRace(PlayerService.getRacesPool().get(
                (random.nextInt(PlayerService.getRacesPool().size()))));
    }

    public void getRandomRegionToCapture(PlayerService playerService, Object[] cells, Player currentPlayer){
        playerService.regionCapture((Cell) cells[random.nextInt(cells.length)], currentPlayer);
    }
}
