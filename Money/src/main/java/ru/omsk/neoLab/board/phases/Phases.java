package ru.omsk.neoLab.board.phases;

public enum Phases {
    RACE_CHOICE("race_choice"),
    CAPTURE_OF_REGIONS("capture_of_regions"),
    PICK_UP_TOKENS("pick_up_tokens"),
    GO_INTO_DECLINE("go_into_decline"),
    GETTING_COINS("getting_coins"),
    END_GAME("end_game");

    private String phasesName;

    Phases(final String phasesName) {
        this.phasesName = phasesName;
    }

    boolean equalPhase(final String phase) {
        return phasesName.equals(phase);
    }
}
