package ru.omsk.neoLab.selfplay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.race.ARace;
import ru.omsk.neoLab.race.Amphibia;

;

@RunWith(MockitoJUnitRunner.class)
public class SelfPlayRaceAmphibiaTest {

    @Mock
    Cell cell;
    @Mock
    ARace race = new Amphibia();
    Player player = new Player("Garen");

    @Test
    public void TestSelfPlayChoiceRace() {
        SelfPlay selfPlay = new SelfPlay();

        selfPlay.createNewPlayer(player);


    }

}