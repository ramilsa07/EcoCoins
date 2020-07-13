import ru.omsk.neoLab.GameService;
import ru.omsk.neoLab.Player;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;

public class Game {

    public static void main(String[] args) {
        Board board = Board.GetInstance();
        ACell[][] aBoard = board.getBoard();
        for (int i = 0; i < aBoard.length; i++) {
            System.out.println();
            for (int j = 0; j < aBoard[0].length; j++) {
                System.out.print(aBoard[i][j].getType() + " ");
            }
        }
        Player player = new Player("Nick");
        ACell cell = aBoard[0][0];
        System.out.println();
        cell.setBelongs(player);
        player.getLocation().add(cell);
        cell = aBoard[0][1];
        cell.setBelongs(player);
        player.getLocation().add(cell);
        cell = aBoard[1][1];
        cell.setBelongs(player);
        player.getLocation().add(cell);
        GameService gameService = GameService.GetInstance();
        for (ACell cells : gameService.findOutWherePlayerCanGo(board, player)) {
            System.out.print(cells.getType() + " --> " + cells.getX() + "-" + cells.getY() + " ");
        }
    }
}
