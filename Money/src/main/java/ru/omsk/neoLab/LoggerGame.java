package ru.omsk.neoLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Generators.Cells.Сell.ACell;
import ru.omsk.neoLab.race.ARace;

import java.util.ArrayList;
import java.util.HashSet;

/*
 * Класс хранит все предупреждения во время игры.
 * */

public final class LoggerGame {

    private static final Logger log = LoggerFactory.getLogger(LoggerGame.class);

    public static void logHowManyPeople(int num) { // Сколько человек играет
        log.info("Game for {} players", num);
    }

    public static void logNickSelection(Player player) { // Игрок1 выбрал ник и присоеденился к игре
        log.info("{} joined the game", player.getNickName());
    }

    public static void logNickSelectionError(String nick) { // Этот ник уже используется. Выберете другой
        log.error("Nick {} is already in use. Choose another nickname", nick);
    }

    public static void logStartGame(Player player1, Player player2) { // Старт игры, представление противников
        log.info("{} vs {} game begins", player1.getNickName(), player2.getNickName());
    }

    public static void logPlayerRoundStart(Player player){ // Начало раунда определенного игрока
        log.info("Player {} starts his turn", player.getNickName());
    }

    public static void logRoundNumber(int round){ // Выводим номер раунда в лог
        log.info("Round {} starts", round);
    }

    public static void logWhatRacesCanIChoose(HashSet<ARace> freeRacesPool) { // Выводим на экран свободные расы
        log.info("Can choose: ");
        for(ARace race: freeRacesPool){
            log.info(race.getNameRace());
        }
    }

    public static void logChooseRaceTrue(Player player) { // Какую расу выбрал игрок
        log.info("{} chose a race of {}", player.getNickName(), player.getRace());
    }

    public static void logChooseRaceFalse() { // Эта раса уже используется. Нужно выбрать другую
        log.error("This race is already in use");
    }

    public static void logNumberOfFreeTokens(Player player) { // Выводим число свободных жетонов на экран
        log.info("logNumberOfFreeTokens {}", player.getCountTokens());
    }

    public static void logWhereToGo(ArrayList<ACell> possibleCellsCapture) { // Куда можно пойти
        log.info("Can go: ");
        for (ACell cell: possibleCellsCapture){
            log.info("{} [{}, {}]", cell.getType(), cell.getX(), cell.getY());
        }
    }

    public static void logRegionCaptureTrue(Player player, ACell cell) { // Такая-то ячейка захвачена игроком
        log.info("The cell {} [{}, {}] is captured by the player {}", cell.getType(), cell.getX(),
                cell.getY(), player.getNickName());
    }

    public static void logRegionCaptureFalse() { // Эту территорию нельзя захватить
        log.error("This territory cannot be captured");
    }

    public static void logFewTokens(){ // Мало жетонов для захвата
        log.error("FewTokens");
    }

    public static void logRedistributionOfTokens(Player player){ // Перераспределение жетонов
        log.info("{} begins the redistribution of tokens", player.getNickName());
    }

    public static void logNumberOfTokensOnCell(ACell cell){ // Выводит число жетонов на ячейке
        log.info("On cell {} [{}, {}] {} tokens", cell.getType(), cell.getX(),
                cell.getY(), cell.getCountTokens());
    }

    public static void logGetCoins(Player player){ // Показывает сколько монеток у игрока
        log.info("{} has {} coins", player.getNickName(), player.getCountCoin());
    }

    public static void logRaceInDecline(Player player){ // Расу отправляем в упадок
        log.info("{} turned the race {} into decline", player.getNickName(), player.getRaceDecline().getNameRace());
    }

    public static void logPickUpTokens(Player player){ // Берем жетоны с ячеек на руку
        log.info("{} pick up tokens", player.getNickName());
    }

    public static void logEndGame(){ // Конец игры
        log.info("Game over");
    }

    public static void logWinner(Player player){ // Победа игрока
        log.info("{} win!", player.getNickName());
    }
}
