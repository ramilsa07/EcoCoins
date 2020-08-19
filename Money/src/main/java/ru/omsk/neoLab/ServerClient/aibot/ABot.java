package ru.omsk.neoLab.ServerClient.aibot;

import ru.omsk.neoLab.player.Player;


public abstract class ABot implements IBot {
    public abstract static class BaseBotFactory implements IBotFactory {
        protected int botIdx = 0; // в случае зарубы между 2 одинаковыми ботами
        public abstract ABot createBot(final Player player);
    }

    protected static final double EPS = 1.0e-9;

    protected final Player player;
    protected final String name;

    protected int id = -1;
    protected int totalNodes = 0;
    protected int terminalNodes = 0;

    protected ABot(final Player player, final String name) {
        this.player = player;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Player getBotPlayer() {
        return player;
    }

}
