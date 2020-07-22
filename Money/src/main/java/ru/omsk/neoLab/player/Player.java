package ru.omsk.neoLab.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.board.Сell.TypeCell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;


public final class Player {

    public static Logger log = LoggerFactory.getLogger(Player.class);

    private final String nickName;
    private int countCoin;

    private int countTokens = 0;

    private ARace race;
    private final ArrayList<Cell> locationCell = new ArrayList<Cell>();

    private ARace raceDecline = null;
    private ArrayList<Cell> locationDeclineCell = new ArrayList<Cell>();

    private boolean decline = false;

    public Player(final String nickName) {
        this.nickName = nickName;
    }

    public final void changeRace(final ARace race) {
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

    public final void goIntoDecline() {
        decline = true;
        raceDecline = race;
        locationDeclineCell.addAll(locationCell);
        locationCell.clear();
    }

    public final void regionCapture(final Cell cell) {
        if (cell.getCountTokens() == 0) {
            this.countTokens -= this.race.getAdvantageCaptureCell(cell);
            cell.setCountTokens(this.race.getAdvantageCaptureCell(cell));
        } else {
            cell.getBelongs().locationCell.remove(cell);
            cell.getBelongs().locationDeclineCell.remove(cell);
            this.countTokens -= this.race.getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1;
            cell.setCountTokens(this.race.getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1);
        }
        this.locationCell.add(cell);
        cell.regionCapture(this);
        log.info("Осталось жетонов у игрока {}  {} от территории {}  и потратили жетонов {}", this.nickName,
                this.countTokens, cell.getType(), cell.getCountTokens());
        if (cell.getType() == TypeCell.Water && !race.getNameRace().equals("Amphibia")) {
            cell.setCountTokens(0);
        }
    }

    public final void shufflingTokens() {
        if (this.countTokens > 0) {
            locationCell.get(0).setCountTokens(locationCell.get(0).getCountTokens() + this.countTokens);
            countTokens = 0;
        }
        log.info("После перетасовки жетонов, у игрока {} осталось {} жетонов", this.nickName, this.countTokens);
    }

    public final void collectTokens(Cell cell) {
        countTokens += cell.getToken(cell.getCountTokens() - 1);
        cell.setCountTokens(1);
    }

    public final void collectAllCoins() {
        for (Cell cell : locationCell) {
            if (race.isAdvantageOpportunityCaptureCell(cell)) {
                countCoin += race.getAdvantageCoin(cell);
            }
        }
        for (Cell cell : locationDeclineCell) {
            if (this.raceDecline.isAdvantageOpportunityCaptureCell(cell)) {
                this.countCoin += this.raceDecline.getAdvantageCoin(cell);
            }
        }
    }

    public String getNickName() {
        return nickName;
    }

    public int getCountCoin() {
        return countCoin;
    }

    public int getCountTokens() {
        return countTokens;
    }

    public ARace getRace() {
        return race;
    }

    public void setRace(ARace race) {
        this.race = race;
    }

    public ArrayList<Cell> getLocationCell() {
        return locationCell;
    }

    public ARace getRaceDecline() {
        return raceDecline;
    }

    public boolean isDecline() {
        return decline;
    }
}

