package ru.omsk.neoLab.ServerClient.server;


import ru.omsk.neoLab.player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerLobby {

    private Socket socketClient;
    private Player player;

    private DataInputStream in;
    private DataOutputStream out;

    public ServerLobby(Socket socketClient, Player player) throws IOException {
        this.socketClient = socketClient;
        this.player = player;

        in = new DataInputStream(this.socketClient.getInputStream());
        out = new DataOutputStream(this.socketClient.getOutputStream());
    }

    public ServerLobby(ServerLobby serverLobby) {
        this(serverLobby.getSocketClient(), serverLobby.getPlayer(), serverLobby.getIn(), serverLobby.getOut());
    }

    public ServerLobby(Socket socketClient, Player player, DataInputStream in, DataOutputStream out) {
        this.socketClient = socketClient;
        this.player = player;
        this.in = in;
        this.out = out;
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

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }
}
