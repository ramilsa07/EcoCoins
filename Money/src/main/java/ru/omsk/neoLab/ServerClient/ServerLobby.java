package ru.omsk.neoLab.ServerClient;


import ru.omsk.neoLab.Player.Player;

import java.net.Socket;

public class ServerLobby {

    private final Socket socketClient;
    private Player player;

    public ServerLobby(Socket socketClient, Player player) {
        this.socketClient = socketClient;
        this.player = player;
    }

    public Socket getSocketClient() {
        return socketClient;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
