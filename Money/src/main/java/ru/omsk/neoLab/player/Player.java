package ru.omsk.neoLab.player;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.board.Сell.TypeCell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;

@JsonAutoDetect
public final class Player {

    public static Logger log = LoggerFactory.getLogger(Player.class);

    private final String nickName;
    @JsonIgnore
    private int countCoin;
    @JsonIgnore
    private int countTokens = 0;

    private ARace race;
    @JsonIgnore
    private ArrayList<Cell> locationCell = new ArrayList<Cell>();
    @JsonIgnore
    private ARace raceDecline = null;
    @JsonIgnore
    private ArrayList<Cell> locationDeclineCell = new ArrayList<Cell>();
    @JsonIgnore
    private PlayerService service = PlayerService.GetInstance();

    private boolean decline = false;

    public Player(String nickName) {
        this.nickName = Player.class.toString();
    }

    public Player() {
        this.nickName = Player.class.toString();
    }

    public Player(@JsonProperty("nickName") String nickName, @JsonProperty("countCoin") int countCoin,
                  @JsonProperty("countTokens") int countTokens, @JsonProperty("race") ARace race,
                  @JsonProperty("locationCell") ArrayList<Cell> locationCell,
                  @JsonProperty("raceDecline") ARace raceDecline,
                  @JsonProperty("locationDeclineCell") ArrayList<Cell> locationDeclineCell,
                  @JsonProperty("service") PlayerService service, @JsonProperty("decline") boolean decline) {
        this.nickName = nickName;
        this.countCoin = countCoin;
        this.countTokens = countTokens;
        this.race = race;
        this.locationCell = locationCell;
        this.raceDecline = raceDecline;
        this.locationDeclineCell = locationDeclineCell;
        this.service = service;
        this.decline = decline;
    }

    public Player(Player player){
        this(player.getNickName(), player.getCountCoin(), player.getCountTokens(), player.getRace(),
                player.getLocationCell(), player.getRaceDecline(), player.getLocationDeclineCell(),
                player.getService(), player.isDecline());
    }


    public void changeRace(final ARace race) {
        if (decline) {
            if (raceDecline != null) {
                PlayerService.getRacesPool().add(raceDecline);
            }
            this.decline = false;
        }
        this.race = race;
        this.countTokens = race.getCountTokens();
        PlayerService.getRacesPool().remove(race);
    }

    public void goIntoDecline() {
        this.decline = true;
        raceDecline = race;
        if (locationDeclineCell.size() != 0) {
            for (Cell cell : locationDeclineCell) {
                cell.setBelongs(null);
                cell.setCountTokens(0);
            }
            locationDeclineCell.clear();
        }
        locationDeclineCell.addAll(locationCell);
        locationCell.clear();
    }

    public void regionCapture(Cell cell) {
        if (cell.getCountTokens() == 0) {
            if (cell.getBelongs() != null) { // Для случая с водой
                cell.getBelongs().locationCell.remove(cell);
                cell.getBelongs().locationDeclineCell.remove(cell);
            }
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
        if (cell.getType() == TypeCell.WATER && !race.getNameRace().equals("Amphibia")) {
            cell.setCountTokens(0);
        }
    }

    public void shufflingTokens(Cell cell) {
        cell.setCountTokens(cell.getCountTokens() + this.countTokens);
        this.countTokens = 0;
        log.info("После перетасовки жетонов, у игрока {} осталось {} жетонов", this.nickName, this.countTokens);
    }

    public void collectTokens() {
        for (Cell cell : locationCell) {
            if (cell.getCountTokens() > 1) {
                this.countTokens += cell.getToken(cell.getCountTokens() - 1);
            }
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
        if (race.getNameRace().equals("Elfs")) {
            race.clearCells();
        }
        if (raceDecline != null && raceDecline.getNameRace().equals("Elfs")) {
            raceDecline.clearCells();
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

    public ArrayList<Cell> getLocationDeclineCell() {
        return locationDeclineCell;
    }

    public boolean isDecline() {
        return decline;
    }

    public PlayerService getService() {
        return service;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickName='" + nickName + '\'' +
                ", countCoin=" + countCoin +
                ", countTokens=" + countTokens +
                ", race=" + race +
                ", locationCell=" + locationCell +
                ", raceDecline=" + raceDecline +
                ", locationDeclineCell=" + locationDeclineCell +
                ", service=" + service +
                ", decline=" + decline +
                '}';
    }
}

