import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.board.Generators.Cells.Сell.Mushrooms;
import ru.omsk.neoLab.race.ARace;
import ru.omsk.neoLab.race.Dwarfs;
import ru.omsk.neoLab.race.Orcs;

public class Game {

    public static void main(String[] args) {
        /*SelfPlay selfPlay = new SelfPlay();
        selfPlay.createNewPlayer(new Player("SimpleBot1"));
        selfPlay.createNewPlayer(new Player("SimpleBot2"));
        selfPlay.Game();
        ARace race1 = new Amphibia();
        ARace race2 = new Orcs();
        ACell cell = new Water();*/
        ARace race1 = new Dwarfs();
        ARace race2 = new Orcs();
        ACell cell = new Mushrooms();
        System.out.print();
    }
}
