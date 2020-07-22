package ru.omsk.neoLab.JsonSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Board;

public class ObjectDeserializer {

    public static Object deserialize() {
        final ObjectMapper mapper = new ObjectMapper();
        Object object = null;
        try {
            object = mapper.readValue(GameSerializer.jsonString, Board.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }
}
