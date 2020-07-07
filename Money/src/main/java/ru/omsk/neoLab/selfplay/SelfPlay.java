package ru.omsk.neoLab.selfplay;

import ru.omsk.neoLab.Player;
import ru.omsk.neoLab.board.IBoard;

import java.io.*;
import java.net.*;

public class SelfPlay {

    static final int PORT = 8081;
    private final int MAX_NUMBER = 4;
    private IBoard board;
    private Player[] players;

    enum Command {
        WARNING("warning"),
        STOP_CLIENT_FROM_SERVER("stop client from server"),
        STOP_CLIENT("stop client"),
        STOP_ALL_CLIENTS("stop all clients"),
        STOP_SERVER("stop server"),
        ;

        private final String commandName;

        Command(final String commandName) {
            this.commandName = commandName;
        }

        boolean equalCommand(final String message) {
            return commandName.equals(message);
        }

        static boolean isCommandMessage(final String message) {
            for (final Command command : values()) {
                if (command.equalCommand(message)) {
                    return true;
                }
            }
            return false;
        }
    }
    private class ServerSomething extends Thread {

        private final SelfPlay server;
        private final Socket socket;
        private final BufferedReader in; // поток чтения из сокета
        private final BufferedWriter out; // поток записи в сокет
        private String nickName = null;

        /**
         * Для общения с клиентом необходим сокет (адресные данные)
         *
         * @param server сервер
         * @param socket сокет
         */
        private ServerSomething(final SelfPlay server, final Socket socket) throws IOException {
            this.server = server;
            this.socket = socket;
            // если потоку ввода/вывода приведут к генерированию искдючения, оно проброситься дальше
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }


        @Override
        public void run() {
            /*try {
                // первое сообщение отправленное сюда - это nickName
                nickName = in.readLine();
                for (final ServerSomething ss : server.serverList) {
                    if (!ss.nickName.equals(nickName)) {
                        continue;
                    }
                    //processDuplicatedNickName();
                    return;
                }
                //processUniqueNickName();
                while (true) {
                    if (!processMessage()) {
                        break;
                    }
                }
            } catch (final IOException e) {
                this.downService();
            }*/
        }

        public void toAppointBoard() {

        }

        public void StartGame() {

        }

        public void toGame() {

        }

        public void Validation() {

        }

        public void toEndGame() {

        }
    }
}
