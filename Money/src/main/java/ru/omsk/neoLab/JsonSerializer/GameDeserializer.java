package ru.omsk.neoLab.JsonSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Board;

import static ru.omsk.neoLab.JsonSerializer.GameSerializer.jsonString;

public class GameDeserializer {

    private static Board board;


    public static Board deserialize() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            board = (Board) mapper.readValue(jsonString, Board.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return board;
    }
}
