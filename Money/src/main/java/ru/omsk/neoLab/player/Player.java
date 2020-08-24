package ru.omsk.neoLab.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Player {

    public static Logger log = LoggerFactory.getLogger(Player.class);

    private @JsonProperty("nickName")
    String nickName;
    @JsonIgnore
    private int countCoin;
    @JsonProperty("countTokens")
    private int countTokens = 0;

    @JsonProperty("race")
    private ARace race;
    @JsonProperty("locationCell")
    private ArrayList<Cell> locationCell = new ArrayList<>();
    @JsonIgnore
    private ARace raceDecline = null;
    @JsonIgnore
    private ArrayList<Cell> locationDeclineCell = new ArrayList<>();
    @JsonIgnore
    private boolean decline = false;

    @JsonIgnore
    public Player() {

    }

    @JsonIgnore
    public Player(String nickName) {
        this.nickName = nickName;
    }

    @JsonCreator
    public Player(@JsonProperty("nickName") String nickName, @JsonProperty("race") ARace race) {
        this.nickName = nickName;
        this.race = race;
    }


    public Player(Player player) {
        this(player.getNickName(), player.getCountCoin(), player.getCountTokens(), player.getRace(),
                player.getLocationCell(), player.getRaceDecline(), player.getLocationDeclineCell(), player.isDecline());
    }

    public Player(String nickName, int countCoin, int countTokens, ARace race, ArrayList<Cell> locationCell, ARace raceDecline, ArrayList<Cell> locationDeclineCell, boolean decline) {
        this.nickName = nickName;
        this.countCoin = countCoin;
        this.countTokens = countTokens;
        this.race = race;
        this.locationCell = locationCell;
        this.raceDecline = raceDecline;
        this.locationDeclineCell = locationDeclineCell;
        this.decline = decline;
    }

    public void changeRace(final ARace race, final Board board) {
        if (decline) {
            locationDeclineCell.clear();
            if (raceDecline != null) {
                board.getRacesPool().add(raceDecline);
            }
            this.raceDecline = this.race;
            this.locationDeclineCell.addAll(this.locationCell);
            this.decline = false;
            locationCell.clear();
        }
        this.race = race;
        this.countTokens = race.getCountTokens();
        board.getRacesPool().remove(race);
        log.info("{} chose a race of {}", this.getNickName(), this.getRace().getNameRace());
    }

    public void goIntoDecline() {
        this.decline = true;
        if (locationDeclineCell.size() != 0) {
            for (Cell cell : locationDeclineCell) {
                cell.setBelongs(new PlayerNeutral("Neutral", race));
                cell.setCountTokens(0);
            }
        }
    }

    public void regionCapture(Cell cell) {
        cell.getBelongs().locationCell.remove(cell);
        cell.getBelongs().locationDeclineCell.remove(cell);
        if (cell.getCountTokens() == 0) {
            this.countTokens -= this.race.getAdvantageCaptureCell(cell);
            cell.setCountTokens(this.race.getAdvantageCaptureCell(cell));
        } else {
            this.countTokens -= this.race.getAdvantageCaptureCell(cell) +
                    cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1;
            cell.setCountTokens(this.race.getAdvantageCaptureCell(cell));
        }
        this.locationCell.add(cell);
        cell.regionCapture(this);
        log.info("Осталось жетонов у игрока {}  {} от территории {}[{},{}]", this.nickName,
                this.countTokens, cell.getType(), cell.getX(), cell.getY());
    }

    public void shufflingTokens(Cell cell) {
        cell.setCountTokens(cell.getCountTokens() + 1);
        this.countTokens--;
        log.info("On cell {} [{}, {}] {} tokens", cell.getType(), cell.getX(),
                cell.getY(), cell.getCountTokens());
        log.info("After shuffling tokens, player {} has {} tokens", this.nickName, this.countTokens);
    }

    public void collectTokens(Cell cell) {
        while (cell.getCountTokens() > 1) {
            this.countTokens += cell.getToken(1);
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

    /*public Player getClone(){
        return new Player(this);
    }*/

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
                ", decline=" + decline +
                '}';
    }
}