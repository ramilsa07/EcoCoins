package ru.omsk.neoLab.JsonSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Board;

public class BoardDeserializer {

    public static Board deserialize(String jsonString) {
        final ObjectMapper mapper = new ObjectMapper();
        Board board = null;
        try {
            board = (Board) mapper.readValue(jsonString, Board.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return board;
    }
}
