package ru.omsk.neoLab;


import ru.omsk.neoLab.board.Сell.Cell;

public final class Validator {

    public static boolean isCheckingOutputOverBoard(int x, int y, int height, int width) {
        return x >= 0 && y >= 0 && x < height && y < width;
    }

    public static boolean isCheckingCapture(Cell cell, Player player) {
        if (cell.getCountTokens() == 0)
            return player.getCountTokens() - player.getRace().getAdvantageCaptureCell(cell) >= 0;
        else
            return player.getCountTokens() - (player.getRace().getAdvantageCaptureCell(cell) + cell.getBelongs().getRace().getAdvantageDefendCell(cell) + 1) >= 0;
    }

    public static boolean isCheckingBelongsCell(Player player, Cell cell) {
        return cell.getBelongs().equals(player);
    }


}
