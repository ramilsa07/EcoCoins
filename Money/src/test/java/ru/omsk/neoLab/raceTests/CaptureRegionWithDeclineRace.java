package ru.omsk.neoLab.raceTests;

import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.Generators.Generator;
import ru.omsk.neoLab.board.Generators.IGenerator;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;

public class CaptureRegionWithDeclineRace {
    private static final IGenerator generator = new Generator();
    private static final Board board = new Board(4, 3);
    private final PlayerService playerService = PlayerService.GetInstance();
    private final Player player = new Player();
    private final Player current = new Player();

    private static void createBoard() {
        board.generate();
    }


}
