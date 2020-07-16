package ru.omsk.neoLab;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.omsk.neoLab.board.Generators.Cells.Сell.Cell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;

@Slf4j
@Data
public class Player {

    private final String nickName;
    private int countCoin;

    private int countTokens = 0;

    private ARace race;
    private ArrayList<Cell> locationCell = new ArrayList<Cell>();

    private ARace raceDecline = null;
    private ArrayList<Cell> locationDeclineCell = new ArrayList<Cell>();

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
            this.locationDeclineCell = this.locationCell;
            this.decline = false;
            locationCell.clear();
        }
        this.race = race;
        this.countTokens = race.getCountTokens();
        PlayerService.getRacesPool().remove(race);
    }

    public void goIntoDecline() {
        this.decline = true;
    }

    public void regionCapture(Cell cell) {
        if (cell.getCountTokens() == 0)
            this.countTokens -= this.race.getAdvantageCaptureCell(cell);
        else
            this.countTokens -= this.race.getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1;
        this.locationCell.add(cell);
        cell.regionCapture(this);
        log.info("Осталось жетонов у игрока {} - {} от территории {}  и потратили жетонов {}", this.nickName, this.countTokens, cell.getType(), 1);
    }

    public void shufflingTokens() {
        for (Cell cell : locationCell) {
            service.getToken(cell, this.countTokens);
        }
        log.info("После перетасовки жетонов, у игрока {} - жетонов {}", this.nickName, this.countCoin);
    }

    public void collectTokens() {
        for (Cell cell : locationCell) {
            this.countTokens += cell.getToken(cell.getCountTokens() - 1);
        }
    }

    public void collectAllCoins() {
        for (Cell cell : locationCell) {
            if (this.race.isAdvantageOpportunityCaptureCell(cell)) {
                this.countCoin += this.race.getAdvantageCoin(cell);
            }
        }
        for (Cell cell : locationDeclineCell) {
            if (this.raceDecline.isAdvantageOpportunityCaptureCell(cell)) {
                this.countCoin += this.raceDecline.getAdvantageCoin(cell);
            }
        }
    }


}

