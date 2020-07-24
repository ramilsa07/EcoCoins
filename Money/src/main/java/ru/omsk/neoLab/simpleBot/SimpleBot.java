package ru.omsk.neoLab.simpleBot;

import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.PlayerService;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;
import java.util.Random;

public final class SimpleBot {

    private final Random random = new Random();

    public SimpleBot() {
    }

    public ARace getRandomRace() {
        return PlayerService.getRacesPool().get(random.nextInt(PlayerService.getRacesPool().size()));
    }

    public Cell getRandomRegionToCapture(Object[] cells) {
        return (Cell) cells[random.nextInt(cells.length)];
    }

    public Cell getRandomCellForDistribution(ArrayList<Cell> locationCell){
        return locationCell.get(random.nextInt(locationCell.size()));
    }
}
