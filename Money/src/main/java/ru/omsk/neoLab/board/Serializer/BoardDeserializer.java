package ru.omsk.neoLab.board.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Board;

public class BoardDeserializer {

    final static ObjectMapper mapper = new ObjectMapper();

    public static Board deserialize(String jsonString) throws JsonProcessingException {
        return mapper.readValue(jsonString, Board.class);
    }
}
