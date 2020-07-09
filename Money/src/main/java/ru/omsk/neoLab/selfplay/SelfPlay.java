package ru.omsk.neoLab.selfplay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsk.neoLab.Player;
import ru.omsk.neoLab.board.IBoard;

import java.util.Queue;

public class SelfPlay {

    private static final Logger logger = LoggerFactory.getLogger(SelfPlay.class);

    private IBoard board;
    private Queue<Player> players;

    private String nickName = null;
    private String phase;
    private int round;

    enum Phases {
        RACE_CHOICE("race choice"), // Выбор расы
        CAPTURE_OF_REGIONS("capture of regions"), // захват регионов
        GETTING_COINS("getting coins"), // Получение монет
        ;

        private final String phasesName;

        Phases(final String phasesName) {
            this.phasesName = phasesName;
        }

        boolean equalPhase(final String phase) {
            return phasesName.equals(phase);
        }
    }

    public void Game(IBoard board) {
        board.generateBoard();
        while (true) {
            if (round == 1) {
                phase = "race choice";
                while (Phases.RACE_CHOICE.equalPhase(phase)) {
                    if (Phases.RACE_CHOICE.equalPhase("race choice")) {
                        logger.info("Началась фаза выбора расы");
                        if (players.element().getRace() != null) {
                            phase = "capture of regions";
                        }
                    }
                }
            }
            while (Phases.CAPTURE_OF_REGIONS.equalPhase(phase)) {
                logger.info("Началась фаза захвата территории");
                if (true) {
                    phase = "getting coins";
                }
            }
            while (Phases.GETTING_COINS.equalPhase(phase)) {
                logger.info("Началась фаза c Сбор Монет");
                changeCourse(players.element());
                phase = "";
            }
            if (round == 10) {
                toEndGame();
            }
        }
    }


    private void changeCourse(Player player) {
        players.poll();
        players.add(player);
    }

    public void toAppointBoard() {

    }

    public void toEndGame() {

    }

   /* public void StartGame() throws IOException{
       System.out.println(String.format("Server started, port: %d", PORT));
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
            // serverSocket.setSoTimeout(1000);
            while (true) { // приложение с помощью System.exit() закрывается по команде от клиента
                // Блокируется до возникновения нового соединения
                final Socket socket = serverSocket.accept();
                try {
                    new Game(this, socket, null,null).start();
                } catch (final IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
            }
        } catch (final BindException e) {
            e.printStackTrace();
        }
    }*/
}
