package ru.omsk.neoLab;

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
        if (cell.getCountTokens() == 0) {
            this.countTokens -= this.race.getAdvantageCaptureCell(cell);
            cell.setCountTokens(this.race.getAdvantageCaptureCell(cell));
        } else {
            cell.getBelongs().locationCell.remove(cell);
            this.countTokens -= this.race.getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1;
            cell.setCountTokens(this.race.getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1);
        }
            this.locationCell.add(cell);
            cell.regionCapture(this);
        log.info("Осталось жетонов у игрока {} - {} от территории {}  и потратили жетонов {}", this.nickName,
                this.countTokens, cell.getType(), cell.getCountTokens());
        if(cell.getType() == TypeCell.Water && !race.getNameRace().equals("Amphibia")){
            cell.setCountTokens(0);
        }
    }

    public void shufflingTokens() {
//        for (Cell cell : locationCell) {
//            service.getToken(cell, this.countTokens);
//        }
        if(this.countTokens > 0){
            locationCell.get(0).setCountTokens(locationCell.get(0).getCountTokens() + this.countTokens);
            countTokens = 0;
        }
        log.info("После перетасовки жетонов, у игрока {} - жетонов {}", this.nickName, this.countTokens);
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

    public String getNickName() {
        return nickName;
    }

    public int getCountCoin() {
        return countCoin;
    }

    public void setCountCoin(int countCoin) {
        this.countCoin = countCoin;
    }

    public int getCountTokens() {
        return countTokens;
    }

    public void setCountTokens(int countTokens) {
        this.countTokens = countTokens;
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

    public void setLocationCell(ArrayList<Cell> locationCell) {
        this.locationCell = locationCell;
    }

    public ARace getRaceDecline() {
        return raceDecline;
    }

    public void setRaceDecline(ARace raceDecline) {
        this.raceDecline = raceDecline;
    }

    public ArrayList<Cell> getLocationDeclineCell() {
        return locationDeclineCell;
    }

    public void setLocationDeclineCell(ArrayList<Cell> locationDeclineCell) {
        this.locationDeclineCell = locationDeclineCell;
    }

    public PlayerService getService() {
        return service;
    }

    public void setService(PlayerService service) {
        this.service = service;
    }

    public boolean isDecline() {
        return decline;
    }

    public void setDecline(boolean decline) {
        this.decline = decline;
    }
}

