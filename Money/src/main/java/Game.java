import ru.omsk.neoLab.Player;
import ru.omsk.neoLab.selfplay.SelfPlay;

public class Game {

    public static void main(String[] args) {
        SelfPlay selfPlay = new SelfPlay();
        selfPlay.createNewPlayer(new Player("SimpleBot1"));
        selfPlay.createNewPlayer(new Player("SimpleBot2"));
        selfPlay.Game();
    }
}
