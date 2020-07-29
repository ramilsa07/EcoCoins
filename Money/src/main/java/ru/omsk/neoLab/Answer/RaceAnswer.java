package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.player.PlayerService;
import ru.omsk.neoLab.race.ARace;

public class RaceAnswer extends Answer {

    @JsonProperty("race")
    private ARace race;

    @JsonCreator
    public RaceAnswer(@JsonProperty("board") final Board board) {
        super(board);
        this.race = PlayerService.getRacesPool().get(random.nextInt(PlayerService.getRacesPool().size()));
    }

    public ARace getRace() {
        return race;
    }
}
