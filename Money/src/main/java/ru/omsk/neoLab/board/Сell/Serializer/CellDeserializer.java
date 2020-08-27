package ru.omsk.neoLab.board.Сell.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Сell.Cell;

public class CellDeserializer {

    public static Cell deserialize(String jsonString) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Cell.class);
    }
}
