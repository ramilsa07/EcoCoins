package ru.omsk.neoLab.race;

import ru.omsk.neoLab.board.Сell.Cell;


public abstract class ARace {

    public String getNameRace() {
        return nameRace;
    }

    public void setNameRace(String nameRace) {
        this.nameRace = nameRace;
    }

    public int getCountTokens() {
        return countTokens;
    }

    public void setCountTokens(int countTokens) {
        this.countTokens = countTokens;
    }

    protected String nameRace;
    protected int countTokens = 6;

    public int getAdvantageCoin(final Cell cell) {
        return cell.getCoin();
    }

    public int getAdvantageCaptureCell(final Cell cell) {
        return cell.getTokensForCapture();
    }

    public int getAdvantageDefendCell(final Cell cell) {
        return cell.getCountTokens();
    }

    public boolean isAdvantageOpportunityCaptureCell(final Cell cell) {
        return cell.isAbilityCapture();
    }

}
