package ru.omsk.neoLab.selfplay;

import ru.omsk.neoLab.LoggerGame;
import ru.omsk.neoLab.ServerClient.aibot.ABot;
import ru.omsk.neoLab.ServerClient.aibot.IBot;
import ru.omsk.neoLab.ServerClient.aibot.RandomBot;
import ru.omsk.neoLab.answer.Answer;
import ru.omsk.neoLab.board.Board;
import ru.omsk.neoLab.board.phases.Phases;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;
import ru.omsk.neoLab.player.PlayerService;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SelfPlayForBots {
    private final Board board;
    private final PlayerService playerService = PlayerService.GetInstance();

    private static final int NUM_PLAYERS = 2;
    private final Queue<ABot> bots = new LinkedList<>();

    private int round = 1;

    private HashSet<Cell> possibleCellsCapture = new HashSet<>();

    public SelfPlayForBots(final IBot.IBotFactory[] factories) {
        if (factories.length != NUM_PLAYERS) {
            throw new IllegalStateException("Factories length " + factories.length + " != " + NUM_PLAYERS);
        }
        bots.add((ABot) factories[0].createBot(new Player("SimpleBot1")));
        bots.add((ABot) factories[0].createBot(new Player("SimpleBot2")));
        board = new Board(4, 3);
    }

    public void Game() {
        generateBoard();
        LoggerGame.logOutputBoard(board);
        ABot firstPlayer = bots.element();
        ABot currentPlayer = bots.element();
        while (round < 11) {
            LoggerGame.logPlayerRoundStart(currentPlayer.getBotPlayer(), round);
            if (currentPlayer.getBotPlayer().isDecline() || round == 1) {
                board.changePhase(Phases.RACE_CHOICE);
                raceChoicePhase(currentPlayer);
            }
            if (Phases.PICK_UP_TOKENS.equals(board.getPhase())) {
                pickUpTokensPhase(currentPlayer);
            }
            if (Phases.GO_INTO_DECLINE.equals(board.getPhase()) && currentPlayer.getAnswer(board).isDecline()) { // Phases.GO_INTO_DECLINE.equals(board.getPhase())
                goIntoDeclinePhase(currentPlayer, firstPlayer);
            } else {
                board.changePhase(Phases.CAPTURE_OF_REGIONS);
            }
            captureOfRegionsPhase(currentPlayer, firstPlayer);
//
//            changeCourse(currentPlayer);
//            currentPlayer = bots.element();
//            if (currentPlayer.equals(firstPlayer)) {
//                board.changePhase(Phases.GETTING_COINS);
//            } else if (currentPlayer.getBotPlayer().isDecline()) {
//                board.changePhase(Phases.RACE_CHOICE);
//            } else {
//                board.changePhase(Phases.PICK_UP_TOKENS);
//            }
            while (Phases.GETTING_COINS.equals(board.getPhase())) {
                getCoinsPhase(currentPlayer);
            }
            if (round == 11) {
                toEndGame();
            }
        }
    }

    private void raceChoicePhase(ABot currentPlayer) {
        LoggerGame.logStartPhaseRaceChoice();
        while (Phases.RACE_CHOICE.equals(board.getPhase())) {
            LoggerGame.logWhatRacesCanIChoose(PlayerService.getRacesPool());
            currentPlayer.getBotPlayer().changeRace(currentPlayer.getAnswer(board).getRace());
            LoggerGame.logChooseRaceTrue(currentPlayer.getBotPlayer());
            board.changePhase(Phases.CAPTURE_OF_REGIONS);
        }
    }

    private void pickUpTokensPhase(ABot currentPlayer) {
        LoggerGame.logStartPhasePickUpTokens();
        while (Phases.PICK_UP_TOKENS.equals(board.getPhase())) {
            currentPlayer.getBotPlayer().collectTokens();
            LoggerGame.logGetTokens(currentPlayer.getBotPlayer());
            board.changePhase(Phases.GO_INTO_DECLINE);
        }
    }

    private void goIntoDeclinePhase(ABot currentPlayer, ABot firstPlayer) {
        currentPlayer.getBotPlayer().goIntoDecline();
        LoggerGame.logRaceInDecline(currentPlayer.getBotPlayer());
        changeCourse(currentPlayer);
        currentPlayer = bots.element();
        if (currentPlayer.equals(firstPlayer)) {
            board.changePhase(Phases.GETTING_COINS);
        } else if (currentPlayer.getBotPlayer().isDecline()) {
            board.changePhase(Phases.RACE_CHOICE);
        } else {
            board.changePhase(Phases.PICK_UP_TOKENS);
        }
    }

    private void captureOfRegionsPhase(ABot currentPlayer, ABot firstPlayer) {
        LoggerGame.logStartPhaseCaptureOfRegions();
        while (Phases.CAPTURE_OF_REGIONS.equals(board.getPhase())) {
            Answer answer = currentPlayer.getAnswer(board);
            if (answer != null) {
                playerService.regionCapture(answer.getCell(), currentPlayer.getBotPlayer());
            } else {
                if (currentPlayer.getBotPlayer().getCountTokens() > 0) {
                    shufflingTokensPhase(currentPlayer);
                }
                changeCourse(currentPlayer);
                currentPlayer = bots.element();
                if (currentPlayer.equals(firstPlayer)) {
                    board.changePhase(Phases.GETTING_COINS);
                } else if (currentPlayer.getBotPlayer().isDecline()) {
                    board.changePhase(Phases.RACE_CHOICE);
                } else {
                    board.changePhase(Phases.PICK_UP_TOKENS);
                }
            }
        }
    }

    private void shufflingTokensPhase(ABot currentPlayer) {
        board.changePhase(Phases.SHUFFLING_TOKENS);
        LoggerGame.logRedistributionOfTokens(currentPlayer.getBotPlayer());
        currentPlayer.getBotPlayer().shufflingTokens(currentPlayer.getAnswer(board).getCell());
    }

    private void getCoinsPhase(ABot currentPlayer){
        LoggerGame.logStartPhaseGetCoins();
        for (ABot bot : bots) {
            bot.getBotPlayer().collectAllCoins();
            LoggerGame.logGetCoins(bot.getBotPlayer());
        }
        round++;
        if (currentPlayer.getBotPlayer().isDecline()) {
            board.changePhase(Phases.RACE_CHOICE);
        } else {
            board.changePhase(Phases.PICK_UP_TOKENS);
        }
    }

    private void generateBoard() {
        board.generate();
    }

    private void changeCourse(ABot bot) {
        bots.poll();
        bots.add(bot);
    }

    public Queue<ABot> getBots() {
        return bots;
    }

    private void toEndGame() {
        LoggerGame.logEndGame();
        System.exit(0);
    }

    public static void main(final String[] args) {
        final IBot.IBotFactory[] factories = {
                RandomBot.factory,
                RandomBot.factory,
        };
        final SelfPlayForBots selfPlayForBots = new SelfPlayForBots(factories);
        selfPlayForBots.Game();
    }
}
