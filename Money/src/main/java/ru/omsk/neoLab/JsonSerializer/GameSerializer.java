package ru.omsk.neoLab.JsonSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Board;

public class GameSerializer {

    public static String jsonString;

    public static void serialize(final Board board) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(board);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
