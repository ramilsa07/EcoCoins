import ru.omsk.neoLab.JsonSerializer.GameDeserializer;
import ru.omsk.neoLab.JsonSerializer.GameSerializer;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.selfplay.SelfPlay;

public class Game {

    public static void main(String[] args) {
        SelfPlay selfPlay = new SelfPlay();
        selfPlay.generateBoard();
        Board board = Board.GetInstance();
        System.out.println(board.getBoard()[0][0]);

        GameSerializer.serialize(board);
        System.out.println(GameSerializer.jsonString);

        board = GameDeserializer.deserialize();
        System.out.println(board.getBoard()[0][0]);
    }
}
