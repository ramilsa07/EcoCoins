package ru.omsk.neoLab.ServerClient.aibot;

import ru.omsk.neoLab.player.Player;


public abstract class ABot implements IBot {
    public abstract static class BaseBotFactory implements IBotFactory {
        protected int botIdx = 0; // в случае зарубы между 2 одинаковыми ботами
        public abstract ABot createBot(final Player player);
    }

    protected static final double EPS = 1.0e-9;

    protected int id = -1;
    protected int totalNodes = 0;
    protected int terminalNodes = 0;

    protected ABot() {

    }

}
