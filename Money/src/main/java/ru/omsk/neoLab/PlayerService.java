package ru.omsk.neoLab;

import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.race.ARace;
import ru.omsk.neoLab.race.RaceContainer;

public class PlayerService {

    public boolean raceChoice(RaceContainer container, String raceName, Player player){
        for(ARace race: container.getSetRace()){
            if(race.getNameRace().equals(raceName)){
                if(player.getRace() != null){
                    player.setRaceDecline(player.getRace());
                }
                player.takeRaceFromPool(race);
                container.removeElement(race);
                return true;
            }
        }
        return false;
    }

    public void regionCapture(Board board, Player player){
        //TODO
    }

    public void unitDistribution(Player player){

    }

    public void countMoney(Player player){
        player.makeCoinCount();
    }
}
