package ru.omsk.neoLab;

import java.io.*;
import java.net.Socket;

public final class Client {

    private static final String IP = "127.0.0.1";//"localhost";
    private static final int PORT = Server.PORT;

    private final String ip; // ip адрес клиента
    private final int port; // порт соединения

    private Socket socket = null;
    private BufferedReader in = null; // поток чтения из сокета
    private BufferedWriter out = null; // поток записи в сокет
    private BufferedReader inputUser = null; // поток чтения с консоли
    private String nickname = null; // имя клиента

    private Client(final String ip, final int port) {
        this.ip = ip;
        this.port = port;
    }

    private void startClient() {
        try {
            socket = new Socket(this.ip, this.port);
        } catch (final IOException e) {
            System.err.println("Socket failed");
            return;
        }

        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (final IOException e) {
            downService();
            return;
        }

        System.out.println(String.format("Client started, ip: %s, port: %d", ip, port));
        pressNickname(); // перед началом необходимо спросить имя
    }

    private void send(final String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    private void pressNickname() {
        System.out.print("Press your nick: ");
        try {
            nickname = inputUser.readLine();
            send(nickname);
        } catch (final IOException ignored) {
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        } catch (final IOException ignored) {
        }
    }

    public static void main(final String[] args) {
        final Client client = new Client(IP, PORT);
        client.startClient();
    }
}
