package ru.omsk.neoLab;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.board.Сell.Cell;
import ru.omsk.neoLab.player.Player;

public final class Validator {

    static Logger log = LoggerFactory.getLogger(Validator.class);
    public static boolean isCheckingOutputOverBoard(int x, int y, int height, int width) {
        return x >= 0 && y >= 0 && x < height && y < width;
    }

    public static boolean isCheckingCapture(Cell cell, Player player) {
        if (cell.getCountTokens() == 0) {
            log.info("{} потратит на захват клетки {} - {} и останется {}", player.getNickName(), cell, player.getRace().getAdvantageCaptureCell(cell), player.getCountTokens() - player.getRace().getAdvantageCaptureCell(cell));
            return player.getCountTokens() - player.getRace().getAdvantageCaptureCell(cell) >= 0;
        } else {
            log.info("{} потратит на захват клетки {} - {} и останется {}", player.getNickName(), cell, (player.getRace().getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1), player.getCountTokens() - (player.getRace().getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1));
            return player.getCountTokens() - (player.getRace().getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1) >= 0;
        }
    }

    public static boolean isCheckingBelongsCell(Player player, Cell cell) {
        return cell.getBelongs().equals(player);
    }
}
