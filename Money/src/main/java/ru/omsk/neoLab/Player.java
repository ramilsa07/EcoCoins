package ru.omsk.neoLab;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;

@Slf4j
@Data
public class Player {

    private final String nickName;
    private int countCoin;

    private int countTokens = 0;

    private ARace race;
    private ArrayList<ACell> locationCell = new ArrayList<ACell>();

    private ARace raceDecline = null;
    private ArrayList<ACell> locationDeclineCell = new ArrayList<ACell>();

    private PlayerService service = PlayerService.GetInstance();

    private boolean decline = false;

    public Player(String nickName) {
        this.nickName = nickName;
    }

    public void changeRace(final ARace race) {
        if (decline) {
            if (raceDecline != null) {
                PlayerService.getRacesPool().add(raceDecline);
            }
            this.raceDecline = this.race;
            this.locationDeclineCell = locationCell;
            this.decline = false;
            locationCell.clear();
        }
        this.race = race;
        PlayerService.getRacesPool().remove(race);
    }

    public void goIntoDecline() {
        this.decline = true;
    }

    public void regionCapture(ACell cell) {
        cell.regionCapture(this);
        log.info("Осталось жетонов у игрока {} - {} от территории {}  и потратили жетонов {}", this.nickName, this.countTokens, cell.getType(), this.race.);
    }

    public void shufflingTokens() {
        for (ACell cell : locationCell) {
            service.getToken(cell, this.countTokens);
        }
        log.info("После перетасовки жетонов, у игрока {} - жетонов {}", this.nickName, this.countCoin);
    }

    public void collectCountTokens(final int countTokens) {
        this.countTokens += countTokens;
    }

    public void collectAllCoins() {
        for (ACell cell : locationCell) {
            this.countCoin += this.race.getAdvantageCoin(cell);
        }
        for (ACell cell : locationDeclineCell) {
            this.countCoin += this.raceDecline.getAdvantageCoin(cell);
        }
    }


}

