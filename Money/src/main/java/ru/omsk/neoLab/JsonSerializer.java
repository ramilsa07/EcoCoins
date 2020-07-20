package ru.omsk.neoLab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Board;

public class JsonSerializer {

    public static String jsonString;
    private static ObjectMapper mapper = new ObjectMapper();

    public static void boardSerialize(final Board board) throws JsonProcessingException {
        jsonString = mapper.writeValueAsString(board);
    }

    public static Board boardDeserialize() throws JsonProcessingException {
        return mapper.readValue(jsonString, Board.class);
    }
}
