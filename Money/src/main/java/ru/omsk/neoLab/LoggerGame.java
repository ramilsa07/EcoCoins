package ru.omsk.neoLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;
import java.util.HashSet;

public final class LoggerGame {

    private static final Logger log = LoggerFactory.getLogger(LoggerGame.class);

    public static void logHowManyPeople(int num) {
        log.info("Game for" + num + "players");
    }

    public static void logNickSelection(Player player) {
        log.info(player.getNickName() + "joined the game");
    }

    public static void logNickSelectionError(String s) {
        log.error("Nick " + s + " is already in use. Choose another nickname");
    }

    public static void logStartGame(Player player1, Player player2) {
        log.info(player1.getNickName() + "vs" + player2.getNickName() + "game begins");
    }

    public static void logPlayerRoundStart(Player player){
        log.info("Player " + player.getNickName() + " starts his turn");
    }

    public static void logRoundNumber(int num){
        log.info("Round " + num + " starts");
    }

    public static void logWhatRacesCanIChoose(HashSet<ARace> racesPool) {
        log.info("Can choose: ");
        for(ARace race: racesPool){
            log.info(race.getNameRace());
        }
    }

    public static void logChooseRaceTrue(Player player) {
        log.info(player.getNickName() + " - chose a race of - " + player.getRace());
    }

    public static void logChooseRaceFalse() {
        log.error("This race is already in use");
    }

    public static void logNumberOfFreeTokens(Player player) {
        log.info("logNumberOfFreeTokens" + player.getCountTokens());
    }

    public static void logWhereToGo(ArrayList<ACell> possibleCellsCapture) {
        log.info("Can go: ");
        for (ACell cell: possibleCellsCapture){
            log.info(cell.getType() + "[" + cell.getX() + ", " + cell.getY() + "]");
        }
    }

    public static void logRegionCaptureTrue(Player player, ACell cell) {
        log.info("The cell " + cell.getType() + "[" + cell.getX() + ", "
                + cell.getY() + "] " + "is captured by the player " + player.getNickName());
    }

    public static void logRegionCaptureFalse() {
        log.error("This territory cannot be captured");
    }

    public static void logFewTokens(){
        log.error("FewTokens");
    }

    public static void logRedistributionOfTokens(Player player){ // Перераспределение жетонов
        log.info(player.getNickName() + " begins the redistribution of tokens");
    }

    public static void logNumberOfTokensOnCell(ACell cell, Player player){
        log.info("On cell" + cell.getType() + "[" + cell.getX() + ", "
                + cell.getY() + "] " + cell.getCountTokens() + " tokens");
    }

    public static void logGetCoins(Player player){
        log.info(player.getNickName() + " has " + player.getCountCoin() + " coins");
    }

    public static void logRaceInDecline(Player player){
        log.info(player.getNickName() + " turned the race " + player.getRaceDecline().getNameRace() + " into decline");
    }

    public static void logPickUpTokens(Player player){
        log.info(player.getNickName() + " pick up tokens");
    }

    public static void logEndGame(){
        log.info("Game over");
    }

    public static void logWinner(Player player){
        log.info(player.getNickName() + "win!");
    }
}
