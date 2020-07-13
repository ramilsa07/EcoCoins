package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;
import ru.omsk.neoLab.race.ARace;
import ru.omsk.neoLab.race.RaceContainer;

public class PlayerService {

    public boolean raceChoice(RaceContainer container, String raceName, Player player) {
        for (ARace race : container.getSetRace()) {
            if (race.getNameRace().equals(raceName)) {
                player.takeRaceFromPool(race);
                container.removeElement(race);
                return true;
            }
        }
        return false;
    }

    public boolean regionCapture(Board board, Player player, int x, int y) {
        if (player.getLocation() == null && (x == 0 || x == 3 || y == 0 || y == 2)) {
            // Выбираем по краям карты и не воду
            if (board.getCall(x, y).equals("Water") && !player.getRace().equals("Amphibia")) {
                return false; // тут можно в логе выдать ошибку выбора
            }
            player.addTerritory(board.getCall(x, y));
            return true;
        }
        if (player.getLocation() != null) {
            for (ACall call : player.getLocation().getCalls()) {
                if (((call.getX() == x - 1) || (call.getX() == x) || (call.getX() == x + 1)) &&
                        ((call.getY() == y - 1) || (call.getY() == y) || (call.getY() == y + 1))) {
                    player.addTerritory(board.getCall(x, y));
                    return true;
                }
            }
        }
        return false;
    }

    public void unitDistribution(Player player) {
        player.pickUpUnits();
    }

    public void countMoney(Player player) {
        player.makeCoinCount();
    }

    public void raceInDecline(Player player) {
        player.setRaceDecline();
    }
}
